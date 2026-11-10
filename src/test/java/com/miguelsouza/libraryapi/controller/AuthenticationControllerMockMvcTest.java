package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.dto.AuthenticationDTO;
import com.miguelsouza.libraryapi.dto.LoginResponseDTO;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.security.CustomAuthentication;
import com.miguelsouza.libraryapi.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerMockMvcTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenService tokenService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthenticationController(authenticationManager, tokenService))
                .build();
    }

    @Test
    void deveAutenticarEDevolverToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setLogin("miguel");
        usuario.setEmail("miguel@email.com");
        usuario.setRoles(java.util.List.of("OPERADOR"));
        when(authenticationManager.authenticate(any())).thenReturn(new CustomAuthentication(usuario));
        when(tokenService.generateToken(any())).thenReturn("jwt-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"login":"miguel","password":"senha"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));

        verify(authenticationManager).authenticate(any());
        verify(tokenService).generateToken(any(CustomAuthentication.class));
    }

    @Test
    void deveRejeitarLoginSemCamposObrigatorios() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authenticationManager, tokenService);
    }
}
