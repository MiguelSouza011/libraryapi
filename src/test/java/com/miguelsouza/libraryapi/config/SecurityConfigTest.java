package com.miguelsouza.libraryapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityConfigTest {

    private final SecurityConfig config = new SecurityConfig();

    @Test
    void deveConverterAuthoritiesDoEscopoComPrefixoScope() {
        Jwt jwt = jwt("GERENTE", List.of("OPERADOR"));

        var authorities = config.jwtAuthenticationConverter().convert(jwt).getAuthorities();

        var authorityNames = authorities.stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());
        assertEquals(
                java.util.Set.of("SCOPE_GERENTE", "OPERADOR", "FACTOR_BEARER"),
                authorityNames);
    }

    private Jwt jwt(String scope, List<String> authorities) {
        return Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .subject("miguel")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .claim("scope", scope)
                .claim("authorities", authorities)
                .build();
    }
}
