package dn.onlinelibrary.security.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.keycloak.client")
@Component
public class KeycloakCredentials {

    private String id;

    private String secret;
}
