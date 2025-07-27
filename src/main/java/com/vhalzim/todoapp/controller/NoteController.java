package com.vhalzim.todoapp.controller;

import com.vhalzim.todoapp.model.NoteEntity;
import com.vhalzim.todoapp.repository.NoteRepository;
import com.vhalzim.todoapp.service.NoteService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@Slf4j
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @PostMapping
    public NoteEntity createNote(@RequestBody NoteEntity note) {
        log.info(String.format("Note Created: %s", note.getBody()));
        return noteService.createNote(note);

    }

    @GetMapping
    public List<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> getNoteById(@PathVariable long id) {
        Optional<NoteEntity> note = noteService.getNoteById(id);

        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteEntity> updateNote(@PathVariable Long id, @RequestBody NoteEntity noteDetails) {
        Optional<NoteEntity> optionalNote = noteService.getNoteById(id);
        if (optionalNote.isPresent()) {
            NoteEntity note = optionalNote.get();
            note.setBody(noteDetails.getBody());
            note.setCompleted(noteDetails.isCompleted());
            return ResponseEntity.ok(noteService.createNote(note));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (noteService.noteExistsById(id)) {
            noteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
