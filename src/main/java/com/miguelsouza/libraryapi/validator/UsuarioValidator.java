package com.miguelsouza.libraryapi.validator;

import com.miguelsouza.libraryapi.exceptions.RegistroDuplicadoException;
import com.miguelsouza.libraryapi.model.Usuario;
import com.miguelsouza.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;

    public void validar(Usuario usuario) {
        if (existeLoginCadastrado(usuario)) {
            throw new RegistroDuplicadoException("Login já cadastrado!");
        }

        if (existeEmailCadastrado(usuario)) {
            throw new RegistroDuplicadoException("Email já cadastrado!");
        }
    }

    private boolean existeLoginCadastrado(Usuario usuario) {
        var usuarioEncontrado = repository.findByLogin(usuario.getLogin());

        if (usuario.getId() == null) {
            return usuarioEncontrado != null;
        }

        return usuarioEncontrado != null &&
                !usuarioEncontrado.getId().equals(usuario.getId());
    }

    private boolean existeEmailCadastrado(Usuario usuario) {
        var usuarioEncontrado = repository.findByEmail(usuario.getEmail());

        if (usuario.getId() == null) {
            return usuarioEncontrado != null;
        }

        return usuarioEncontrado != null &&
                !usuarioEncontrado.getId().equals(usuario.getId());
    }
}

