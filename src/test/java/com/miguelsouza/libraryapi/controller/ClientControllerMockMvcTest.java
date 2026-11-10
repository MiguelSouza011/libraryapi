package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.model.Client;
import com.miguelsouza.libraryapi.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerMockMvcTest {

    @Mock private ClientService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ClientController(service))
                .build();
    }

    @Test
    void deveCriarClientEInformarLocation() throws Exception {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        client.setId(id);
        client.setClientId("library-client");
        when(service.salvar(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"clientId":"library-client","clientSecret":"secret","redirectUri":"http://localhost/callback","scope":"GERENTE"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/clients/" + id));
    }
}
