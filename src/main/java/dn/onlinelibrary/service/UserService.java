package dn.onlinelibrary.service;

import dn.onlinelibrary.dto.UserDto;
import dn.onlinelibrary.entity.UserEntity;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {

    UserEntity createUser(UserDto userDto);

    void addRole(RealmResource realmResource,String id);

    UserRepresentation createUserRepresentation(UserDto userDto);

    void setCredentials(UserRepresentation userRepresentation,String password);

    void deleteById(String id);

    UserEntity getUserById(String id);


}
