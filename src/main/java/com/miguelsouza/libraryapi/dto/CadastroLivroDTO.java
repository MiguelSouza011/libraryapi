package com.miguelsouza.libraryapi.dto;

import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "CadastroLivro")
public record CadastroLivroDTO(
        @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
        @ISBN
        @NotBlank(message = "campo obrigatorio")
        String isbn,
        @Schema(description = "Título do livro", example = "O Senhor dos Anéis")
        @NotBlank(message = "campo obrigatorio")
        String titulo,
        @Schema(description = "Data de publicação", example = "1954-07-29")
        @NotNull(message = "campo obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,
        @Schema(description = "Gênero literário")
        GeneroLivro genero,
        @Schema(description = "Preço do livro", example = "59.90")
        BigDecimal preco,
        @Schema(description = "ID do autor")
        @NotNull(message = "campo obrigatorio")
        UUID idAutor) {
}
