package com.miguelsouza.libraryapi.exceptions;

public class RegistroDuplicadoException extends RuntimeException {

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
