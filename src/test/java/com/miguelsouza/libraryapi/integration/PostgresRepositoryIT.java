package com.miguelsouza.libraryapi.integration;

import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
@Transactional
class PostgresRepositoryIT {

    @Autowired private AutorRepository autorRepository;
    @Autowired private LivroRepository livroRepository;

    @Test
    void devePersistirAutorELivroNoPostgresql() {
        Autor autor = new Autor();
        autor.setNome("Autor de Integração");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 1, 1));
        Autor salvo = autorRepository.saveAndFlush(autor);

        Livro livro = new Livro();
        livro.setIsbn("9783161484100");
        livro.setTitulo("Livro de Integração");
        livro.setDataPublicacao(LocalDate.of(2019, 1, 1));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(39.90));
        livro.setAutor(salvo);
        livroRepository.saveAndFlush(livro);

        assertTrue(autorRepository.findById(salvo.getId()).isPresent());
        Livro encontrado = livroRepository.findByIsbn("9783161484100").orElseThrow();
        assertEquals("Livro de Integração", encontrado.getTitulo());
        assertEquals(salvo.getId(), encontrado.getAutor().getId());
    }

    @Test
    void deveConsultarLivroPorIsbnInexistente() {
        assertTrue(livroRepository.findByIsbn("isbn-inexistente").isEmpty());
    }
}
