package com.miguelsouza.libraryapi.handler;

import com.miguelsouza.libraryapi.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveMapearRegistroDuplicadoParaConflito() {
        ErrorResponse response = handler.handleRegistroDuplicadoException(
                new RegistroDuplicadoException("ISBN já cadastrado!"));

        assertEquals(409, response.status());
        assertEquals("ISBN já cadastrado!", response.mensage());
    }

    @Test
    void deveMapearRecursosNaoEncontrados() {
        ErrorResponse response = handler.handleResourceNotFoundException(
                new ResourceNotFoundException("Livro não encontrado"));

        assertEquals(404, response.status());
        assertEquals("Livro não encontrado", response.mensage());
    }

    @Test
    void deveMapearAcessoNegadoEAutenticacao() {
        ErrorResponse denied = handler.handleAccesDaniedException(
                new AccessDeniedException("negado"));
        ErrorResponse unauthorized = handler.handleAuthenticationException(
                new BadCredentialsException("credenciais inválidas"));

        assertEquals(403, denied.status());
        assertEquals(401, unauthorized.status());
    }

    @Test
    void deveMapearCampoInvalidoComCampo() {
        ErrorResponse response = handler.handleCampoInvalidoException(
                new CampoInvalidoException("Preço obrigatório", "preco"));

        assertEquals(422, response.status());
        assertEquals("preco", response.erro().getFirst().camp());
        assertEquals("Preço obrigatório", response.erro().getFirst().error());
    }
}
