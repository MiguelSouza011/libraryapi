package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.controller.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.controller.dto.PesquisaLivroDTO;
import com.miguelsouza.libraryapi.controller.mappers.LivroMapper;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import com.miguelsouza.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<PesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = service.pesquisa(
                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<PesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                          @RequestBody CadastroLivroDTO dto) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                     Livro entidadeAux = mapper.toEntity(dto);
                     livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                     livro.setGenero(entidadeAux.getGenero());
                     livro.setIsbn(entidadeAux.getIsbn());
                     livro.setAutor(entidadeAux.getAutor());
                     livro.setPreco(entidadeAux.getPreco());
                     livro.setTitulo(entidadeAux.getTitulo());
                     service.atualizar(livro);
                     return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
