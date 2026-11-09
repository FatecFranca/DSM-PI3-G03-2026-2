package br.edu.fatecfranca.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecfranca.api.dtos.AtualizarStatusRequest;
import br.edu.fatecfranca.api.models.Orcamento;
import br.edu.fatecfranca.api.services.OrcamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service;

    public OrcamentoController(OrcamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Orcamento> listarTodos() {
        return service.listar(null, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public List<Orcamento> listarPorSolicitacao(@PathVariable String solicitacaoId) {
        return service.listar(solicitacaoId, null);
    }

    @GetMapping("/prestador/{prestadorId}")
    public List<Orcamento> listarPorPrestador(@PathVariable String prestadorId) {
        return service.listar(null, prestadorId);
    }

    @PostMapping
    public ResponseEntity<Orcamento> criar(@Valid @RequestBody Orcamento orcamento) {
        Orcamento orcamentoSalvo = service.criar(orcamento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orcamentoSalvo);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Orcamento> atualizarStatus(
            @PathVariable String id,
            @Valid @RequestBody AtualizarStatusRequest request) {

        Orcamento orcamentoAtualizado = service.atualizarStatus(id, request.getStatus());


        return ResponseEntity.ok(orcamentoAtualizado);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<Orcamento> concluir(@PathVariable String id) {
        Orcamento orcamentoConcluido = service.concluir(id);

        return ResponseEntity.ok(orcamentoConcluido);
    }

}