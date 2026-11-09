package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.enums.StatusSolicitacao;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository repository;
    private final ClienteService clienteService;

    public SolicitacaoService(SolicitacaoRepository repository, ClienteService clienteService) {
        this.repository = repository;
        this.clienteService = clienteService;
    }

    public List<Solicitacao> listar(String clienteId, String status) {
    StatusSolicitacao statusEnum = null;

    if (status != null && !status.isBlank()) {
        try {
            statusEnum = StatusSolicitacao.valueOf(
                    status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Status de solicitação inválido");
        }
    }
     if (clienteId != null && !clienteId.isBlank() && statusEnum != null) {
        return repository.findByClienteIdAndStatus(clienteId, statusEnum);
    }
    if (clienteId != null && !clienteId.isBlank()) {
        return repository.findByClienteId(clienteId);
    }
    if (statusEnum != null) {
        return repository.findByStatus(statusEnum);
    }
    return repository.findAll();
}

    public Optional<Solicitacao> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Solicitacao criar(Solicitacao solicitacao) {
        if (clienteService.buscarPorId(solicitacao.getClienteId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        solicitacao.setId(null);
        solicitacao.setData(LocalDateTime.now());
        solicitacao.setStatus(StatusSolicitacao.ABERTA);
        if (solicitacao.getFotos() != null) {
            solicitacao.getFotos().forEach(foto -> {
                if (foto.getDataEnvio() == null) foto.setDataEnvio(LocalDateTime.now());
            });
        }
        return repository.save(solicitacao);
    }

    public Solicitacao salvar(Solicitacao solicitacao) {
        return repository.save(solicitacao);
    }
}
