package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }
}
