package com.miguelsouza.libraryapi.security;

import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtCustomAuthenticationFilterTest {

    @Mock private UsuarioService usuarioService;
    @Mock private FilterChain filterChain;
    @InjectMocks private JwtCustomAuthenticationFilter filter;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveConverterJwtParaUsuarioDaAplicacao() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setLogin("miguel");
        usuario.setRoles(java.util.List.of("GERENTE"));
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .subject("miguel")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .claims(claims -> claims.putAll(Map.of()))
                .build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
        when(usuarioService.obterPorLogin("miguel")).thenReturn(usuario);

        filter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), filterChain);

        assertInstanceOf(CustomAuthentication.class,
                SecurityContextHolder.getContext().getAuthentication());
        assertSame(usuario, ((CustomAuthentication) SecurityContextHolder.getContext()
                .getAuthentication()).getUsuario());
        verify(filterChain).doFilter(any(), any());
    }

    @Test
    void deveManterJwtQuandoUsuarioNaoExiste() throws Exception {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .subject("inexistente")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .build();
        var authentication = new JwtAuthenticationToken(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(usuarioService.obterPorLogin("inexistente")).thenReturn(null);

        filter.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse(), filterChain);

        assertSame(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}
