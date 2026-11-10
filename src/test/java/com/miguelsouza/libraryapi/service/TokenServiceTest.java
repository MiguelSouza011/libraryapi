package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.security.CustomAuthentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock private JwtEncoder jwtEncoder;
    @Mock private Jwt jwt;
    @InjectMocks private TokenService tokenService;

    @Test
    void deveGerarTokenComClaimsDeUsuarioEExpiracaoDeUmaHora() {
        Usuario usuario = new Usuario();
        usuario.setLogin("miguel");
        usuario.setEmail("miguel@email.com");
        usuario.setRoles(List.of("GERENTE"));
        when(jwtEncoder.encode(any())).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("jwt-assinado");

        String token = tokenService.generateToken(new CustomAuthentication(usuario));

        assertEquals("jwt-assinado", token);
        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(captor.capture());
        JwtClaimsSet claims = captor.getValue().getClaims();
        assertEquals("miguel", claims.getSubject());
        assertEquals("miguel@email.com", claims.getClaimAsString("email"));
        assertEquals(List.of("GERENTE"), claims.getClaimAsStringList("authorities"));
        assertTrue(Duration.between(claims.getIssuedAt(), claims.getExpiresAt())
                .equals(Duration.ofMinutes(60)));
    }
}
