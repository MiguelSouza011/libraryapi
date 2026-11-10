package com.miguelsouza.libraryapi.validator;

import com.miguelsouza.libraryapi.exceptions.CampoInvalidoException;
import com.miguelsouza.libraryapi.exceptions.RegistroDuplicadoException;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LivroValidatorTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroValidator validator;

    @Test
    void deveRejeitarIsbnDuplicado() {
        Livro livro = livro(LocalDate.of(2019, 1, 1), BigDecimal.TEN);
        when(repository.findByIsbn(livro.getIsbn())).thenReturn(Optional.of(livro));

        assertThrows(RegistroDuplicadoException.class, () -> validator.validar(livro));
    }

    @Test
    void deveExigirPrecoParaLivroPublicadoAPartirDe2020() {
        Livro livro = livro(LocalDate.of(2020, 1, 1), null);
        when(repository.findByIsbn(livro.getIsbn())).thenReturn(Optional.empty());

        CampoInvalidoException exception =
                assertThrows(CampoInvalidoException.class, () -> validator.validar(livro));

        assertEquals("preco", exception.getCampo());
    }

    @Test
    void devePermitirLivroAnteriorA2020SemPreco() {
        Livro livro = livro(LocalDate.of(2019, 12, 31), null);
        when(repository.findByIsbn(livro.getIsbn())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validar(livro));
    }

    private Livro livro(LocalDate data, BigDecimal preco) {
        Livro livro = new Livro();
        livro.setIsbn("9783161484100");
        livro.setDataPublicacao(data);
        livro.setPreco(preco);
        return livro;
    }
}
