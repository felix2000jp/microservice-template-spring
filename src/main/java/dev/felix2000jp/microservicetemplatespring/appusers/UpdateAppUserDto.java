package dev.felix2000jp.microservicetemplatespring.appusers;

import jakarta.validation.constraints.NotEmpty;

record UpdateAppUserDto(
        @NotEmpty(message = "Username must not be empty")
        String username,
        @NotEmpty(message = "Password must not be empty")
        String password
) {
}
