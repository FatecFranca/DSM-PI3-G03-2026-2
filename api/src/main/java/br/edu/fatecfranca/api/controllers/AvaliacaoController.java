package br.edu.fatecfranca.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecfranca.api.models.Avaliacao;
import br.edu.fatecfranca.api.services.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Avaliacao> listarTodos() {
        return service.listar(null, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Avaliacao> listarPorPrestador(@PathVariable String prestadorId) {
        return service.listar(prestadorId, null);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Avaliacao> listarPorCliente(@PathVariable String clienteId) {
        return service.listar(null, clienteId);
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criar(@Valid @RequestBody Avaliacao avaliacao) {
        Avaliacao avaliacaoSalva = service.criar(avaliacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(avaliacaoSalva);
    }

}