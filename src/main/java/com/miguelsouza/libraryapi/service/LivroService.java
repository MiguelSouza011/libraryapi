package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import com.miguelsouza.libraryapi.repository.specs.LivroSpecs;
import com.miguelsouza.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.miguelsouza.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;

   public Livro salvar(Livro livro) {
       validator.validar(livro);
       return repository.save(livro);
   }

   public Optional<Livro> obterPorId(UUID id) {
       return repository.findById(id);
   }

   public void deletar(Livro livro) {
       repository.delete(livro);
   }

   public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {
       //Specification<Livro> specs = Specification
               //.where(LivroSpecs.isbnEqual(isbn))
               //.and(LivroSpecs.tituloLike(titulo))
               //.and(LivroSpecs.generoEqual(genero));

       Specification<Livro> specs = Specification.where((root, query, cb) ->
               cb.conjunction());

       if(isbn != null) {
           specs = specs.and(isbnEqual(isbn));
       }
       if(titulo != null) {
           specs = specs.and(tituloLike(titulo));
       }
       if(genero != null) {
           specs = specs.and(generoEqual(genero));
       }
       if(anoPublicacao != null) {
           specs = specs.and(anoPublicacao(anoPublicacao));
       }
       if(nomeAutor != null) {
           specs = specs.and(nomeAutorLike(nomeAutor));
       }

       return repository.findAll(specs);
   }

    public void atualizar(Livro livro) {
       if (livro.getId() == null) {
           throw new IllegalArgumentException("Para atualizar, é necessário que o autor esteja cadastrado!");
       }
       validator.validar(livro);
       repository.save(livro);
    }
}
