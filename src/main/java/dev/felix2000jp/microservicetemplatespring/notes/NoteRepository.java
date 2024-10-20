package dev.felix2000jp.microservicetemplatespring.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByAppuserId(UUID appuserId);

    Optional<Note> findByIdAndAppuserId(UUID id, UUID appuserId);

}
