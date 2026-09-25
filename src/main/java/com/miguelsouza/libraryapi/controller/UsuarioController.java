package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.controller.dto.UsuarioDTO;
import com.miguelsouza.libraryapi.controller.mappers.UsuarioMapper;
import com.miguelsouza.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = mapper.toEntity(dto);
        usuarioService.save(usuario);
    }
}
