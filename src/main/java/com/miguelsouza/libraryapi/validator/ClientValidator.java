package com.miguelsouza.libraryapi.validator;

import com.miguelsouza.libraryapi.exceptions.RegistroDuplicadoException;
import com.miguelsouza.libraryapi.model.Client;
import com.miguelsouza.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;

    public void validar(Client client) {
        if (existeClientIdCadastrado(client)) {
            throw new RegistroDuplicadoException("ClientId já cadastrado!");
        }
    }

    private boolean existeClientIdCadastrado(Client client) {
        var clientEncontrado = repository.findByClientId(client.getClientId());

        if (client.getId() == null) {
            return clientEncontrado != null;
        }

        return clientEncontrado != null &&
                !clientEncontrado.getId().equals(client.getId());
    }
}
