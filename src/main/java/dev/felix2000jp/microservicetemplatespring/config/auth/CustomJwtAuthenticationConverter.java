package dev.felix2000jp.microservicetemplatespring.config.auth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String USERNAME_CLAIM_NAME = "appuser_username";
    private static final String SCOPE_CLAIM_NAME = "appuser_scope";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var username = source.getClaimAsString(USERNAME_CLAIM_NAME);
        var scope = source.getClaimAsString(SCOPE_CLAIM_NAME);
        var authorities = List.of(new SimpleGrantedAuthority(scope));

        return new CustomJwtAuthenticationToken(source, authorities, username);
    }

}
