package dev.felix2000jp.microservicetemplatespring.appusers.dtos;

import java.util.UUID;

public record AppUserDto(UUID id, String username) {
}
