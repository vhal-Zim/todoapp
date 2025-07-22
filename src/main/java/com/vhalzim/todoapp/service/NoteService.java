package com.vhalzim.todoapp.service;

import com.vhalzim.todoapp.model.NoteEntity;
import com.vhalzim.todoapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;


    public NoteEntity createNote(NoteEntity note) {
        return noteRepository.save(note);
    }

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<NoteEntity> getNoteById(long id) {
        return noteRepository.findById(id);
    }

    public boolean noteExistsById(Long id) {
        return noteRepository.existsById(id);

    }

}
