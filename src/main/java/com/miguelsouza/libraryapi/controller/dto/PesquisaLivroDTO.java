package com.miguelsouza.libraryapi.controller.dto;

import com.miguelsouza.libraryapi.model.enums.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
) {
}
