package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.repositories.ClienteRepository;


@Service
public class ClienteService {

    private final ClienteRepository repository;
        
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        verificarEmail(cliente);
        cliente.setSenha(encoder.encode(cliente.getSenha()));
        return repository.save(cliente);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }
    private void verificarEmail(Cliente cliente) {
        boolean emailEmUso;
        if (cliente.getId() == null) {
            emailEmUso = repository.existsByEmail(cliente.getEmail());
        } else {
            emailEmUso = repository.existsByEmailAndIdNot(cliente.getEmail(), cliente.getId());
        }
        if (emailEmUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }
    }

}
