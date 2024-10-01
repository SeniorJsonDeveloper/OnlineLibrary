package dn.onlinelibrary.service.impl;

import dn.onlinelibrary.dto.UserDto;
import dn.onlinelibrary.entity.UserEntity;
import dn.onlinelibrary.repository.UserRepository;
import dn.onlinelibrary.security.keycloak.KeycloakCredentials;
import dn.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;

    private final Keycloak keycloak;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.keycloak.realm}")
    private String realm;


    @Override
    public void addRole(RealmResource realmResource, String id) {
        UserResource userResource = realmResource.users().get(id);
        var role = keycloak.realm(realm).roles().get(ROLE_USER).toRepresentation();
        userResource.roles().realmLevel().add(List.of(role));

    }

    @Override
    public UserRepresentation createUserRepresentation(UserDto userDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getUsername());
        if(userRepository.existsByUsername(userDto.getUsername())) {
            userRepresentation.setEnabled(false);
            throw new RuntimeException("This name already exists, change any username");
        }
        userRepresentation.setEmail(userDto.getEmail());
        if(userRepository.existsByEmail(userDto.getEmail())) {
            userRepresentation.setEmailVerified(false);
            userRepresentation.setEnabled(false);
            throw new RuntimeException("This email already exists, change any email");

        }
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    @Override
    public void setCredentials(UserRepresentation userRepresentation, String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setRealmRoles(List.of(ROLE_USER));



    }

    @Override
    public void deleteById(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(RuntimeException::new);
        userRepository.delete(userEntity);
    }

    @Override
    public UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    @Override
    public UserEntity createUser(UserDto userDto) {
        RealmResource realmResource = keycloak.realm(realm);
        UserRepresentation userRepresentation = createUserRepresentation(userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        setCredentials(userRepresentation, encodePassword);
        var response = realmResource.users().create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);
        addRole(realmResource, userId);
        UserEntity user = new UserEntity(userId,
                userDto.getUsername(),
                encodePassword,
                userDto.getEmail(),
                userDto.getCreatedAt());
        return userRepository.save(user);
    }





}
