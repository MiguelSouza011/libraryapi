package com.miguelsouza.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "Usuario")
public record UsuarioDTO(
        @Schema(description = "Login do usuário", example = "joao.silva")
        @NotBlank(message = "campo obrigatório")
        String login,
        @Schema(description = "Email do usuário", example = "joao@email.com")
        @Email(message = "inválido")
        @NotBlank(message = "campo obrigatório")
        String email,
        @Schema(description = "Senha do usuário")
        @NotBlank(message = "campo obrigatório")
        String senha,
        @Schema(description = "Roles do usuário", example = "[\"GERENTE\"]")
        List<String> roles
) {
}
