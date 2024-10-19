package dev.felix2000jp.microservicetemplatespring.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    Optional<Note> findByTitle(String title);

    boolean existsByTitle(String title);

}
