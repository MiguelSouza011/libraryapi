package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.mappers.UsuarioMapper;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ContextConfiguration(classes = UsuarioAuthorizationTest.TestConfig.class)
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class UsuarioAuthorizationTest {

    @Autowired private WebApplicationContext context;
    @MockitoBean private UsuarioService usuarioService;
    @MockitoBean private UsuarioMapper mapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void deveExigirAutenticacaoParaCadastrarGerente() throws Exception {
        mockMvc.perform(post("/usuarios/gerentes")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void deveNegarOperadorAoCadastrarGerente() throws Exception {
        mockMvc.perform(post("/usuarios/gerentes")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void devePermitirGerenteNaRotaProtegida() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        when(mapper.toEntity(org.mockito.ArgumentMatchers.any())).thenReturn(usuario);

        mockMvc.perform(post("/usuarios/gerentes")
                        .contentType("application/json")
                        .content("""
                                {"login":"gerente","email":"gerente@email.com","senha":"senha"}
                                """))
                .andExpect(status().isCreated());
    }

    @Configuration
    @EnableWebSecurity
    @EnableWebMvc
    @EnableMethodSecurity(proxyTargetClass = true)
    static class TestConfig {
        @Bean
        UsuarioController usuarioController(UsuarioService service, UsuarioMapper mapper) {
            return new UsuarioController(service, mapper);
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                            .requestMatchers(HttpMethod.POST, "/usuarios/gerentes").hasRole("GERENTE")
                            .anyRequest().authenticated())
                    .httpBasic(httpBasic -> {})
                    .build();
        }
    }
}
