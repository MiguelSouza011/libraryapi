package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.model.Client;
import com.miguelsouza.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Cliente")
public class ClientController implements GenericController{

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "ClientId já cadastrado")
    })
    public ResponseEntity<Void> salvar(@RequestBody Client client) {
        Client clientSalvo = service.salvar(client);
        URI location = gerarHeaderLocation(clientSalvo.getId());
        return ResponseEntity.created(location).build();
    }
}
