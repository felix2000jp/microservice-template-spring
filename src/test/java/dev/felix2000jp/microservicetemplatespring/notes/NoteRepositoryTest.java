//package dev.felix2000jp.microservicetemplatespring.notes;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Testcontainers
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class NoteRepositoryTest {
//
//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");
//
//    @Autowired
//    private NoteRepository noteRepository;
//
//    private Note note;
//
//    @BeforeEach
//    void setUp() {
//        note = new Note("title", "content");
//        note = noteRepository.save(note);
//    }
//
//    @AfterEach
//    void tearDown() {
//        noteRepository.deleteAll();
//    }
//
//    @Test
//    void findByTitle_should_return_note_when_title_exists() {
//        var actual = noteRepository.findByTitle(note.getTitle());
//
//        assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.of(note));
//    }
//
//    @Test
//    void findByTitle_Should_return_empty_when_title_not_exists() {
//        var actual = noteRepository.findByTitle("some other title that does not exist");
//
//        assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.empty());
//    }
//
//}