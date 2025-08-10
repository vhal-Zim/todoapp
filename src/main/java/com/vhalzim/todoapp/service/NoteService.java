package com.vhalzim.todoapp.service;

import com.vhalzim.todoapp.model.NoteEntity;
import com.vhalzim.todoapp.repository.NoteRepository;

import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Mono<NoteEntity> createNote(NoteEntity note) {

        return Mono.fromCallable(() -> noteRepository.save(note))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<NoteEntity> getAllNotes() {
        return Flux.fromIterable(noteRepository.findAll())
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<NoteEntity> getNoteById(long id) {
        return Mono.fromCallable(() -> noteRepository.findById(id).orElse(null))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> deleteNoteById(Long id) {
        return Mono.fromRunnable(() -> noteRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic()).then();
    }
}