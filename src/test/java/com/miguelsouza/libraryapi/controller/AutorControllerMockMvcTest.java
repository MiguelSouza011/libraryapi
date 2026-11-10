package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.mappers.AutorMapper;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AutorControllerMockMvcTest {

    @Mock private AutorService service;
    @Mock private AutorMapper mapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AutorController(service, mapper))
                .build();
    }

    @Test
    void deveCriarAutorEInformarLocation() throws Exception {
        UUID id = UUID.randomUUID();
        Autor autor = autor(id);
        when(mapper.toEntity(any())).thenReturn(autor);
        when(service.salvar(autor)).thenReturn(autor);

        mockMvc.perform(post("/autores")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"nome":"Machado de Assis","dataNascimento":"1839-06-21","nacionalidade":"Brasileira"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/autores/" + id));
    }

    @Test
    void deveRetornar404QuandoAutorNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.obterPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/autores/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400ParaAutorInvalido() throws Exception {
        mockMvc.perform(post("/autores")
                        .contentType(APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service, mapper);
    }

    private Autor autor(UUID id) {
        Autor autor = new Autor();
        autor.setId(id);
        autor.setNome("Machado de Assis");
        autor.setDataNascimento(LocalDate.of(1839, 6, 21));
        autor.setNacionalidade("Brasileira");
        return autor;
    }
}
