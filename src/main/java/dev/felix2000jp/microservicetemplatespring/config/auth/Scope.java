package dev.felix2000jp.microservicetemplatespring.config.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Scope implements GrantedAuthority {
    ALL,
    APPLICATION;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
