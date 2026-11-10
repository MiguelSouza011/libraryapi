package com.miguelsouza.libraryapi.service;

import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.repository.UsuarioRepository;
import com.miguelsouza.libraryapi.validator.UsuarioValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository repository;
    @Mock private PasswordEncoder encoder;
    @Mock private UsuarioValidator validator;
    @InjectMocks private UsuarioService service;

    @Test
    void deveSalvarUsuarioOperadorComSenhaCriptografada() {
        Usuario usuario = usuario();
        when(encoder.encode("senha")).thenReturn("hash");

        service.save(usuario);

        assertEquals(List.of("OPERADOR"), usuario.getRoles());
        assertEquals("hash", usuario.getSenha());
        verify(validator).validar(usuario);
        verify(repository).save(usuario);
    }

    @Test
    void deveSalvarGerenteComRoleGerente() {
        Usuario usuario = usuario();
        when(encoder.encode("senha")).thenReturn("hash-gerente");

        service.saveGerente(usuario);

        assertEquals(List.of("GERENTE"), usuario.getRoles());
        assertEquals("hash-gerente", usuario.getSenha());
        verify(repository).save(usuario);
    }

    private Usuario usuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("miguel");
        usuario.setEmail("miguel@email.com");
        usuario.setSenha("senha");
        return usuario;
    }
}
