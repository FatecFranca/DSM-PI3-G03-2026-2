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

import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.services.SolicitacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

        private final SolicitacaoService service;

        public SolicitacaoController(SolicitacaoService service) {
                this.service = service;
        }

        @GetMapping
        public List<Solicitacao> listarTodos() {
                return service.listar(null, null);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Solicitacao> buscarPorId(@PathVariable String id) {
                return service.buscarPorId(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/cliente/{clienteId}")
        public List<Solicitacao> listarPorCliente(@PathVariable String clienteId) {
                return service.listar(clienteId, null);
        }

        @GetMapping("/status/{status}")
        public List<Solicitacao> listarPorStatus(@PathVariable String status) {
                return service.listar(null, status);
        }

        @PostMapping
        public ResponseEntity<Solicitacao> criar(@Valid @RequestBody Solicitacao solicitacao) {
                Solicitacao solicitacaoSalva = service.criar(solicitacao);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(solicitacaoSalva);
        }
}
