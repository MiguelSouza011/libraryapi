package com.miguelsouza.libraryapi.controller;


import com.miguelsouza.libraryapi.controller.dto.AutorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @PostMapping
    public ResponseEntity salvar(@RequestBody AutorDTO autor) {


        return new ResponseEntity("Autor Salvo com sucesso! " + autor, HttpStatus.CREATED);
    }
}
