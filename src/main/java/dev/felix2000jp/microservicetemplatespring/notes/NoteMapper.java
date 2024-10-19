package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.notes.dtos.CreateNoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.NoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.UpdateNoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDto toDto(Note note);

    @Mapping(target = "id", ignore = true)
    Note toEntity(CreateNoteDto createNoteDto);

    Note toEntity(UUID id, UpdateNoteDto updateNoteDto);

}
