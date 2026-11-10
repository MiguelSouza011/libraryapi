package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.dto.AtualizacaoAutorDTO;
import com.miguelsouza.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.miguelsouza.libraryapi.exceptions.ResourceNotFoundException;
import com.miguelsouza.libraryapi.mappers.AutorMapper;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import com.miguelsouza.libraryapi.security.SecurityService;
import com.miguelsouza.libraryapi.validator.AutorValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock private AutorRepository repository;
    @Mock private AutorValidator validator;
    @Mock private LivroRepository livroRepository;
    @Mock private SecurityService securityService;
    @Mock private AutorMapper mapper;
    @InjectMocks private AutorService service;

    @Test
    void deveSalvarAutorAssociadoAoUsuarioLogado() {
        Autor autor = new Autor();
        var usuario = new com.miguelsouza.libraryapi.model.Usuario();
        when(securityService.obterUsuarioLogado()).thenReturn(usuario);
        when(repository.save(autor)).thenReturn(autor);

        assertEquals(autor, service.salvar(autor));
        verify(validator).validar(autor);
        verify(repository).save(autor);
        assertEquals(usuario, autor.getUsuario());
    }

    @Test
    void naoDeveAtualizarAutorNaoEncontrado() {
        UUID id = UUID.randomUUID();
        AtualizacaoAutorDTO dto = new AtualizacaoAutorDTO(
                "Machado de Assis",
                LocalDate.of(1839, 6, 21),
                "Brasileira"
        );

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(id, dto)
        );

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, validator);
    }

    @Test
    void naoDeveExcluirAutorComLivros() {
        Autor autor = new Autor();
        when(livroRepository.existsByAutor(autor)).thenReturn(true);

        assertThrows(OperacaoNaoPermitidaException.class, () -> service.deletar(autor));
        verify(repository, never()).delete(any());
    }

    @Test
    void deveExcluirAutorSemLivros() {
        Autor autor = new Autor();
        when(livroRepository.existsByAutor(autor)).thenReturn(false);

        service.deletar(autor);

        verify(repository).delete(autor);
    }
}
