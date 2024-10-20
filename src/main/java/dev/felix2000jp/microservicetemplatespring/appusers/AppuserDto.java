package dev.felix2000jp.microservicetemplatespring.appusers;

import dev.felix2000jp.microservicetemplatespring.config.auth.Scope;

import java.util.UUID;

record AppuserDto(UUID id, String username, Scope scope) {
}
