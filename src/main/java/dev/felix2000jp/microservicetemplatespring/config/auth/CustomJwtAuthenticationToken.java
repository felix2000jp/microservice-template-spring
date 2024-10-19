package dev.felix2000jp.microservicetemplatespring.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.UUID;

class CustomJwtAuthenticationToken extends JwtAuthenticationToken {

    CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String name) {
        super(jwt, authorities, name);
    }

    @Override
    public AppuserPrincipal getPrincipal() {
        var token = getToken();

        return new AppuserPrincipal(
                UUID.fromString(token.getSubject()),
                token.getClaimAsString("appuser_username"),
                Scope.valueOf(token.getClaimAsString("appuser_scope"))
        );
    }

}