package com.miguelsouza.libraryapi.security;

import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationProviderTest {

    @Mock private UsuarioService usuarioService;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private CustomAuthenticationProvider provider;

    @Test
    void deveAutenticarUsuarioComSenhaValida() {
        Usuario usuario = usuario("miguel", "hash");
        when(usuarioService.obterPorLogin("miguel")).thenReturn(usuario);
        when(passwordEncoder.matches("senha", "hash")).thenReturn(true);

        var result = provider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated("miguel", "senha"));

        assertInstanceOf(CustomAuthentication.class, result);
        assertEquals("miguel", result.getName());
        assertTrue(result.isAuthenticated());
    }

    @Test
    void deveRejeitarLoginInexistente() {
        when(usuarioService.obterPorLogin("inexistente")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> provider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated("inexistente", "senha")));
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void deveRejeitarSenhaInvalida() {
        when(usuarioService.obterPorLogin("miguel")).thenReturn(usuario("miguel", "hash"));
        when(passwordEncoder.matches("errada", "hash")).thenReturn(false);

        assertThrows(UsernameNotFoundException.class, () -> provider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated("miguel", "errada")));
    }

    @Test
    void deveSuportarSomenteUsernamePasswordAuthenticationToken() {
        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
        assertFalse(provider.supports(org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken.class));
    }

    private Usuario usuario(String login, String senha) {
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setRoles(java.util.List.of("GERENTE"));
        return usuario;
    }
}
