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
        try {
            Thread.sleep(2000);
            return noteRepository.save(note);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NoteEntity> getAllNotes() {
        try {
            Thread.sleep(2000);
            return noteRepository.findAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<NoteEntity> getNoteById(long id) {
        try {
            Thread.sleep(2000);
            return noteRepository.findById(id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean noteExistsById(Long id) {
        try {
            Thread.sleep(2000);
            return noteRepository.existsById(id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
