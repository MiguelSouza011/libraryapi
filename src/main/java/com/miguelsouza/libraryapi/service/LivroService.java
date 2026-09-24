package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.controller.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

   public Livro salvar(Livro livro) {
       return repository.save(livro);
   }

   public Optional<Livro> obterPorId(UUID id) {
       return repository.findById(id);
   }
}
