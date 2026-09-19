package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.controller.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

   // public CadastroLivroDTO salvar(CadastroLivroDTO autor) {
        //validator.validar(autor);
        //return repository.save(autor);
   // }
}
