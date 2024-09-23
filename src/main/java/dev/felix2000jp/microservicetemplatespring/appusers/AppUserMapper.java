package dev.felix2000jp.microservicetemplatespring.appusers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
interface AppUserMapper {

    AppUserDto toDto(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    AppUser toEntity(CreateAppUserDto createAppUserDto);

    AppUser toEntity(UUID id, UpdateAppUserDto updateAppUserDto);

}
