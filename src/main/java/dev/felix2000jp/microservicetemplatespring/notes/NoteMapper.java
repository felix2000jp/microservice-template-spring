package dev.felix2000jp.microservicetemplatespring.notes;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface NoteMapper {

    NoteDto toDto(Note note);

}
