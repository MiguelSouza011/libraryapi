package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.controller.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.controller.dto.ErrorResponse;
import com.miguelsouza.libraryapi.controller.mappers.LivroMapper;
import com.miguelsouza.libraryapi.exceptions.RegistroDuplicadoException;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = mapper.toEntity(dto);

            service.salvar(livro);

            return ResponseEntity.ok(livro);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
