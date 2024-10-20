//package dev.felix2000jp.microservicetemplatespring.notes;
//
//import dev.felix2000jp.microservicetemplatespring.notes.exceptions.NoteNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(NoteController.class)
//public class NoteControllerTest {
//
//    @MockBean
//    private NoteService noteService;
//
//    @SpyBean(NoteMapperImpl.class)
//    private NoteMapper noteMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private Note note;
//    private String noteDtoJson;
//
//    @BeforeEach
//    void setUp() {
//        note = new Note(UUID.randomUUID(), "title", "content");
//
//        noteDtoJson = String.format("""
//                {
//                    "id": "%s",
//                    "title": "%s",
//                    "content": "%s"
//                }
//                """, note.getId(), note.getTitle(), note.getContent());
//    }
//
//    @Test
//    void getById_should_return_200_and_note_when_note_exists() throws Exception {
//        when(noteService.findById(note.getId())).thenReturn(note);
//
//        mockMvc
//                .perform(get("/api/notes/" + note.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(noteDtoJson));
//    }
//
//    @Test
//    void getById_should_return_404_when_note_not_exists() throws Exception {
//        when(noteService.findById(note.getId())).thenThrow(new NoteNotFoundException());
//
//        mockMvc
//                .perform(get("/api/notes/" + note.getId()))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title").value("Not Found"))
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.detail").value("Note was not found"));
//
//    }
//
//    @Test
//    void getByTitle_should_return_200_and_note_when_note_exists() throws Exception {
//        when(noteService.findByTitle(note.getTitle())).thenReturn(note);
//
//        mockMvc
//                .perform(get("/api/notes/title/" + note.getTitle()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(noteDtoJson));
//    }
//
//    @Test
//    void getByTitle_should_return_404_when_note_not_exists() throws Exception {
//        when(noteService.findByTitle(note.getTitle())).thenThrow(new NoteNotFoundException());
//
//        mockMvc
//                .perform(get("/api/notes/title/" + note.getTitle()))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title").value("Not Found"))
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.detail").value("Note was not found"));
//    }
//
//    @Test
//    void create_should_return_201_and_location_when_note_created() throws Exception {
//        when(noteService.create(any(Note.class))).thenReturn(note);
//
//        var bodyJson = String.format("""
//                {
//                    "title": "%s",
//                    "content": "%s"
//                }
//                """, note.getTitle(), note.getContent());
//
//        mockMvc
//                .perform(post("/api/notes").contentType(MediaType.APPLICATION_JSON).content(bodyJson))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/api/notes/" + note.getId()));
//    }
//
//    @Test
//    void update_should_return_204_when_note_updated() throws Exception {
//        when(noteService.update(any(Note.class))).thenReturn(note);
//
//        var bodyJson = String.format("""
//                {
//                    "title": "%s",
//                    "content": "%s"
//                }
//                """, note.getTitle(), note.getContent());
//
//        mockMvc
//                .perform(put("/api/notes/" + note.getId()).contentType(MediaType.APPLICATION_JSON).content(bodyJson))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void update_should_return_404_when_note_not_exists() throws Exception {
//        when(noteService.update(any(Note.class))).thenThrow(new NoteNotFoundException());
//
//        var bodyJson = String.format("""
//                {
//                    "title": "%s",
//                    "content": "%s"
//                }
//                """, note.getTitle(), note.getContent());
//
//        mockMvc
//                .perform(put("/api/notes/" + note.getId()).contentType(MediaType.APPLICATION_JSON).content(bodyJson))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title").value("Not Found"))
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.detail").value("Note was not found"));
//    }
//
//    @Test
//    void delete_should_return_204_when_note_deleted() throws Exception {
//        mockMvc
//                .perform(delete("/api/notes/" + note.getId()))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void delete_should_return_404_when_note_not_exists() throws Exception {
//        when(noteService.delete(note.getId())).thenThrow(new NoteNotFoundException());
//
//        mockMvc
//                .perform(delete("/api/notes/" + note.getId()))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title").value("Not Found"))
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.detail").value("Note was not found"));
//    }
//
//}
