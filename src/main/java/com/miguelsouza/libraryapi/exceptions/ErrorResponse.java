package com.miguelsouza.libraryapi.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String mensage, List<ErrorCamp> erro) {

    public static ErrorResponse response(String mensage) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensage, List.of());
    }

    public static ErrorResponse conflict(String mensage) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), mensage, List.of());
    }

}
