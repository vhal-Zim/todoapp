package com.vhalzim.todoapp.repository;

import com.vhalzim.todoapp.model.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByBody(String body);
    List<NoteEntity> findByCompleted(boolean completed);
    List<NoteEntity> findByBodyContainingIgnoreCase(String body);
}
