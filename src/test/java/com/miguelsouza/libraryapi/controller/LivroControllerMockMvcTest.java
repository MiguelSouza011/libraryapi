package com.miguelsouza.libraryapi.controller;

import com.miguelsouza.libraryapi.dto.PesquisaLivroDTO;
import com.miguelsouza.libraryapi.mappers.LivroMapper;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import com.miguelsouza.libraryapi.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LivroControllerMockMvcTest {

    @Mock private LivroService service;
    @Mock private LivroMapper mapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LivroController(service, mapper))
                .build();
    }

    @Test
    void deveCriarLivro() throws Exception {
        UUID id = UUID.randomUUID();
        Livro livro = livro(id);
        when(service.salvar(any())).thenReturn(livro);

        mockMvc.perform(post("/livros")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"isbn":"9783161484100","titulo":"O Livro","dataPublicacao":"2019-01-01","genero":"FICCAO","preco":29.90,"idAutor":"11111111-1111-1111-1111-111111111111"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/livros/" + id));

        verify(service).salvar(any());
    }

    @Test
    void devePesquisarLivrosComFiltros() throws Exception {
        Livro livro = livro(UUID.randomUUID());
        PesquisaLivroDTO dto = new PesquisaLivroDTO(
                livro.getId(), livro.getIsbn(), livro.getTitulo(),
                livro.getDataPublicacao(), livro.getGenero(), livro.getPreco(), null);
        when(service.pesquisa("9783161484100", "O Livro", null,
                GeneroLivro.FICCAO, 2019, 0, 10))
                .thenReturn(new PageImpl<>(List.of(livro), PageRequest.of(0, 10), 1));
        when(mapper.toDTO(livro)).thenReturn(dto);

        mockMvc.perform(get("/livros")
                        .param("isbn", "9783161484100")
                        .param("titulo", "O Livro")
                        .param("genero", "FICCAO")
                        .param("ano-publicacao", "2019"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo").value("O Livro"));
    }

    @Test
    void deveRetornar404QuandoLivroNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.obterPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/livros/" + id))
                .andExpect(status().isNotFound());
    }

    private Livro livro(UUID id) {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setIsbn("9783161484100");
        livro.setTitulo("O Livro");
        livro.setDataPublicacao(LocalDate.of(2019, 1, 1));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(29.90));
        return livro;
    }
}
