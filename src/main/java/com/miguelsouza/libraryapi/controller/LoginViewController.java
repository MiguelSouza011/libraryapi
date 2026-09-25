package com.miguelsouza.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping
    public String paginaLogin() {
        return "login";
    }
}
