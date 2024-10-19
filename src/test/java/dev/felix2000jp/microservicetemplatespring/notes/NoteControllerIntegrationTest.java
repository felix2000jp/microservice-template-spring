package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.notes.dtos.CreateNoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.NoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.UpdateNoteDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    TestRestTemplate restTemplate;

    private Note note;
    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
        note = new Note("title", "content");
        note = noteRepository.save(note);

        noteDto = new NoteDto(note.getId(), note.getTitle(), note.getContent());
    }

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
    }

    @Test
    void getById_should_return_note_when_note_exists() {
        var actual = restTemplate.getForObject("/api/notes/{id}", NoteDto.class, note.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(noteDto);
    }

    @Test
    void getByTitle_should_return_note_when_note_exists() {
        var actual = restTemplate.getForObject("/api/notes/title/{title}", NoteDto.class, note.getTitle());
        assertThat(actual).usingRecursiveComparison().isEqualTo(noteDto);
    }

    @Test
    void create_should_save_note() {
        var createnoteDto = new CreateNoteDto("new title", "new content");

        var location = restTemplate.postForLocation("/api/notes", createnoteDto, NoteDto.class);

        var actual = restTemplate.getForObject(location, NoteDto.class);
        var actualInDatabase = noteRepository.findById(actual.id()).orElse(null);
        assertThat(actualInDatabase).isNotNull();
        assertThat(actualInDatabase.getTitle()).isEqualTo(createnoteDto.title());
        assertThat(actualInDatabase.getContent()).isEqualTo(createnoteDto.content());
    }

    @Test
    void update_should_update_note_when_note_exists() {
        var updatenoteDto = new UpdateNoteDto("new title", "new content");

        restTemplate.put("/api/notes/" + note.getId(), updatenoteDto);

        var actualInDatabase = noteRepository.findById(note.getId()).orElse(null);
        assertThat(actualInDatabase).isNotNull();
        assertThat(actualInDatabase.getTitle()).isEqualTo(updatenoteDto.title());
        assertThat(actualInDatabase.getContent()).isEqualTo(updatenoteDto.content());
    }

    @Test
    void delete_should_delete_note_when_note_exists() {
        restTemplate.delete("/api/notes/" + note.getId());

        var actualInDatabase = noteRepository.findById(note.getId()).orElse(null);
        assertThat(actualInDatabase).isNull();
    }

}
