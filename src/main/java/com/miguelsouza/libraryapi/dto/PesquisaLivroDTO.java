package com.miguelsouza.libraryapi.dto;

import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "PesquisaLivro")
public record PesquisaLivroDTO(
        @Schema(description = "ID do livro")
        UUID id,
        @Schema(description = "ISBN do livro")
        String isbn,
        @Schema(description = "Título do livro")
        String titulo,
        @Schema(description = "Data de publicação")
        LocalDate dataPublicacao,
        @Schema(description = "Gênero literário")
        GeneroLivro genero,
        @Schema(description = "Preço do livro")
        BigDecimal preco,
        @Schema(description = "Autor do livro")
        AutorDTO autor
) {
}
