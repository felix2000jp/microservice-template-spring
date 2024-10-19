package dev.felix2000jp.microservicetemplatespring.appusers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record CreateAppuserDto(
        @Size(min = 3, max = 20)
        @NotBlank
        String username,
        @Size(min = 5, max = 150)
        @NotBlank
        String password
) {
}
