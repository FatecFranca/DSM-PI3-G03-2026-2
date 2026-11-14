package br.edu.fatecfranca.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecfranca.api.dtos.LoginRequest;
import br.edu.fatecfranca.api.dtos.LoginResponse;
import br.edu.fatecfranca.api.services.LoginService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public LoginResponse entrar(@Valid @RequestBody LoginRequest dados) {
        return service.entrar(dados);
    }
}
