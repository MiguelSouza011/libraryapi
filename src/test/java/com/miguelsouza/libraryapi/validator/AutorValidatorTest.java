package com.miguelsouza.libraryapi.validator;

import com.miguelsouza.libraryapi.exceptions.RegistroDuplicadoException;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorValidatorTest {

    @Mock
    private AutorRepository repository;

    @InjectMocks
    private AutorValidator validator;

    @Test
    void deveRejeitarAutorDuplicadoAoCadastrar() {
        Autor autor = autor(null);
        when(repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()))
                .thenReturn(Optional.of(autor(UUID.randomUUID())));

        assertThrows(RegistroDuplicadoException.class, () -> validator.validar(autor));
    }

    @Test
    void devePermitirAtualizacaoDoMesmoAutor() {
        UUID id = UUID.randomUUID();
        Autor autor = autor(id);
        when(repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()))
                .thenReturn(Optional.of(autor));

        assertDoesNotThrow(() -> validator.validar(autor));
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
