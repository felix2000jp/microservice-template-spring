package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.appusers.Appuser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String title;

    @Size(min = 3, max = 500)
    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id", nullable = false)
    private Appuser appuser;

    public Note() {
    }

    Note(String title, String content, Appuser appuser) {
        this.title = title;
        this.content = content;
        this.appuser = appuser;
    }

    public UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
