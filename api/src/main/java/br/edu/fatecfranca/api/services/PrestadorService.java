package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;

@Service
public class PrestadorService {

    private final PrestadorRepository repository;
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public PrestadorService(PrestadorRepository repository) {
        this.repository = repository;
    }

    public List<Prestador> listarTodos() {
        return repository.findAll();
    }

    public Optional<Prestador> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Prestador salvar(Prestador prestador) {
        verificarEmail(prestador);
        prestador.setSenha(encoder.encode(prestador.getSenha()));
        return repository.save(prestador);
    }
    public void excluir(String id) {
        repository.deleteById(id);
    }

    public Optional<Prestador> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }
        private void verificarEmail(Prestador prestador) {
        boolean emailEmUso;
        if (prestador.getId() == null) {
            emailEmUso = repository.existsByEmail(prestador.getEmail());
        } else {
            emailEmUso = repository.existsByEmailAndIdNot(prestador.getEmail(), prestador.getId());
        }
        if (emailEmUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }
    }


}
