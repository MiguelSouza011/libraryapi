package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.exceptions.ResourceNotFoundException;
import com.miguelsouza.libraryapi.mappers.LivroMapper;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import com.miguelsouza.libraryapi.security.SecurityService;
import com.miguelsouza.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.miguelsouza.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;
    private final AutorRepository autorRepository;
    private final LivroMapper mapper;

   public Livro salvar(CadastroLivroDTO dto) {
       Usuario usuario = securityService.obterUsuarioLogado();
       Autor autor = autorRepository.findById(dto.idAutor())
               .orElseThrow( () ->
                       new ResourceNotFoundException(
                               "Autor não encontrado com o ID: " + dto.idAutor()
                       ));

       Livro livro = mapper.toEntity(dto);
       livro.setAutor(autor);
       livro.setUsuario(usuario);
       validator.validar(livro);
       return repository.save(livro);
   }

   public Optional<Livro> obterPorId(UUID id) {
       return repository.findById(id);
   }

   public void deletar(Livro livro) {
       if (livro.getId() == null) {
           throw new ResourceNotFoundException("Para deletar, é necessário que o autor esteja cadastrado!");
       }
       repository.delete(livro);
   }

   public Page<Livro> pesquisa(
           String isbn,
           String titulo,
           String nomeAutor,
           GeneroLivro genero,
           Integer anoPublicacao,
           Integer pagina,
           Integer tamanhoPagina) {

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

       Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
       return repository.findAll(specs, pageRequest);
   }

    public void atualizar(UUID id, CadastroLivroDTO dto) {
        Livro livro = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Livro não encontrado com o ID: " + id
                        ));

        Autor autor = autorRepository.findById(dto.idAutor())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Autor não encontrado com o ID: " + dto.idAutor()
                        ));

        mapper.updateEntityFromDTO(dto, livro);
        livro.setAutor(autor);
        validator.validar(livro);
       repository.save(livro);
    }
}
