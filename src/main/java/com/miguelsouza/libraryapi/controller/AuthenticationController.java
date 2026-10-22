package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.dto.AuthenticationDTO;
import com.miguelsouza.libraryapi.dto.LoginResponseDTO;
import com.miguelsouza.libraryapi.security.CustomAuthentication;
import com.miguelsouza.libraryapi.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realizar login e emitir um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "401", description = "Login ou senha inválidos")
    })
    public LoginResponseDTO login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.login(),
                authenticationDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken);

        return new LoginResponseDTO(
                tokenService.generateToken((CustomAuthentication) authentication));
    }
}
