package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.exceptions.ResourceNotFoundException;
import com.miguelsouza.libraryapi.mappers.LivroMapper;
import com.miguelsouza.libraryapi.model.Autor;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import com.miguelsouza.libraryapi.repository.LivroRepository;
import com.miguelsouza.libraryapi.security.SecurityService;
import com.miguelsouza.libraryapi.validator.LivroValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock private LivroRepository repository;
    @Mock private LivroValidator validator;
    @Mock private SecurityService securityService;
    @Mock private AutorRepository autorRepository;
    @Mock private LivroMapper mapper;
    @InjectMocks private LivroService service;

    @Test
    void deveSalvarLivroComAutorEUsuarioLogado() {
        UUID autorId = UUID.randomUUID();
        CadastroLivroDTO dto = dto(autorId);
        Autor autor = new Autor();
        Livro livro = new Livro();
        Usuario usuario = new Usuario();
        when(autorRepository.findById(autorId)).thenReturn(Optional.of(autor));
        when(securityService.obterUsuarioLogado()).thenReturn(usuario);
        when(mapper.toEntity(dto)).thenReturn(livro);
        when(repository.save(livro)).thenReturn(livro);

        assertEquals(livro, service.salvar(dto));

        assertEquals(autor, livro.getAutor());
        assertEquals(usuario, livro.getUsuario());
        verify(validator).validar(livro);
    }

    @Test
    void deveFalharAoSalvarComAutorInexistente() {
        UUID autorId = UUID.randomUUID();
        CadastroLivroDTO dto = dto(autorId);
        when(autorRepository.findById(autorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.salvar(dto));
        verifyNoInteractions(mapper, validator, repository);
    }

    @Test
    void naoDeveDeletarLivroSemId() {
        Livro livro = new Livro();

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(livro));
        verifyNoInteractions(repository);
    }

    private CadastroLivroDTO dto(UUID autorId) {
        return new CadastroLivroDTO(
                "9783161484100", "Livro", LocalDate.of(2019, 1, 1),
                null, BigDecimal.TEN, autorId);
    }
}
