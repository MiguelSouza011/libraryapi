package com.miguelsouza.libraryapi.repository;

import com.miguelsouza.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService service;

    @Test
    void transacoesSimples() {
        service.executar();
    }
}
