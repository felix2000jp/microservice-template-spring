package dev.felix2000jp.microservicetemplatespring.appusers;

import dev.felix2000jp.microservicetemplatespring.appusers.dtos.AppUserDto;
import dev.felix2000jp.microservicetemplatespring.appusers.dtos.CreateAppUserDto;
import dev.felix2000jp.microservicetemplatespring.appusers.dtos.UpdateAppUserDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDto toDto(AppUser appUser);

    AppUser toEntity(CreateAppUserDto createAppUserDto);

    AppUser toEntity(UUID id, UpdateAppUserDto updateAppUserDto);

}
