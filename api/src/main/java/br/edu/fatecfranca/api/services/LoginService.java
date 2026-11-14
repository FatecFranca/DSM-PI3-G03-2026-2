package br.edu.fatecfranca.api.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.LoginRequest;
import br.edu.fatecfranca.api.dtos.LoginResponse;
import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.repositories.ClienteRepository;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;

@Service
public class LoginService {

    private final ClienteRepository clienteRepository;
    private final PrestadorRepository prestadorRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginService(ClienteRepository clienteRepository,
            PrestadorRepository prestadorRepository) {
        this.clienteRepository = clienteRepository;
        this.prestadorRepository = prestadorRepository;
    }

    public LoginResponse entrar(LoginRequest dados) {

        // 1) procura o e-mail nos clientes
        Optional<Cliente> cliente = clienteRepository.findByEmail(dados.email());
        if (cliente.isPresent() && senhaConfere(dados.senha(), cliente.get().getSenha())) {
            return new LoginResponse(cliente.get().getId(), cliente.get().getNome(), "cliente");
        }

        // 2) se não achou, procura nos prestadores
        Optional<Prestador> prestador = prestadorRepository.findByEmail(dados.email());
        if (prestador.isPresent() && senhaConfere(dados.senha(), prestador.get().getSenha())) {
            return new LoginResponse(prestador.get().getId(), prestador.get().getNome(), "prestador");
        }

        // 3) não achou ninguém ou a senha está errada (mesma mensagem nos dois casos)
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos.");
    }

    private boolean senhaConfere(String senhaDigitada, String senhaSalva) {
        // cadastros antigos não têm senha
        if (senhaSalva == null) {
            return false;
        }
        return encoder.matches(senhaDigitada, senhaSalva);
    }
}
