package dev.felix2000jp.microservicetemplatespring.config.auth;

import java.util.UUID;

public record AppuserPrincipal(UUID id, String username, Scope scope) {
}
