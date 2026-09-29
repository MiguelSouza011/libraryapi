package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.dto.UsuarioDTO;
import com.miguelsouza.libraryapi.mappers.UsuarioMapper;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastrar novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Login ou email já cadastrado")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuarioService.save(usuario);
        URI location = gerarHeaderLocation(usuario.getId());
        return ResponseEntity.created(location).build();
    }
}
