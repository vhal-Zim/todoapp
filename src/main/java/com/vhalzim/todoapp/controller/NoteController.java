package com.vhalzim.todoapp.controller;

import com.vhalzim.todoapp.model.NoteEntity;
import com.vhalzim.todoapp.repository.NoteRepository;
import com.vhalzim.todoapp.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NoteEntity> createNote(@RequestBody NoteEntity note) {
        return noteService.createNote(note);
    }

    @GetMapping
    public Flux<NoteEntity> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<NoteEntity>> getNoteById(@PathVariable long id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<NoteEntity>> updateNote(@PathVariable Long id, @RequestBody NoteEntity noteDetails) {
        return noteService.getNoteById(id)
                .flatMap(existingNote -> {
                    existingNote.setBody(noteDetails.getBody());
                    existingNote.setCompleted(noteDetails.isCompleted());
                    return noteService.createNote(existingNote);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteNote(@PathVariable Long id) {
        return noteService.getNoteById(id)
                .flatMap(note -> noteService.deleteNoteById(id))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}


