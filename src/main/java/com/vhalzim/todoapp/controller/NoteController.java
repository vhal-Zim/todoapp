package com.vhalzim.todoapp.controller;

import com.vhalzim.todoapp.model.NoteEntity;
import com.vhalzim.todoapp.repository.NoteRepository;
import com.vhalzim.todoapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes") // ✅ Agregar path basez
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @PostMapping
    public NoteEntity createNote(@RequestBody NoteEntity note) {
        return noteService.createNote(note);
    }

    @GetMapping
    public List<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> getNoteById(@PathVariable long id) {
        Optional<NoteEntity> note = noteService.getNoteById(id);

        if (note.isPresent()) {
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        //return note
        //        .map(ResponseEntity::ok)
        //        .orElse(ResponseEntity.notFound().build());

        //return ResponseEntity.ok(
        //        noteRepository.findById(id)
        //                .orElseThrow(() -> new EntityNotFoundException("Note not found"))
        //);
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
