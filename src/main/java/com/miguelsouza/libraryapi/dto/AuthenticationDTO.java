package com.miguelsouza.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Authentication")
public record AuthenticationDTO(
        @Schema(description = "Login do usuário", example = "joao.silva")
        @NotBlank(message = "Login é obrigatório")
        String login,

        @Schema(description = "Senha do usuário", example = "senha-segura")
        @NotBlank(message = "Senha é obrigatória")
        String password) {
}
