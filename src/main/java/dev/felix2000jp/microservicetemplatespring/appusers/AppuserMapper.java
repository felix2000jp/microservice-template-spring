package dev.felix2000jp.microservicetemplatespring.appusers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AppuserMapper {

    AppuserDto toDto(Appuser appuser);

}
