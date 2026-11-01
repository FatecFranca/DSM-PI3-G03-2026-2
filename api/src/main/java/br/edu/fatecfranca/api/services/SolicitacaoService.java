package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Foto;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;

@Service
public class SolicitacaoService {
    public static final String ABERTA = "ABERTA";
    public static final String EM_NEGOCIACAO = "EM_NEGOCIACAO";
    public static final String FECHADA = "FECHADA";

    private final SolicitacaoRepository repository;
    private final ClienteService clienteService;

    public SolicitacaoService(SolicitacaoRepository repository, ClienteService clienteService) {
        this.repository = repository;
        this.clienteService = clienteService;
    }

    public List<Solicitacao> listar(String clienteId, String status) {
        if (clienteId != null && !clienteId.isBlank() && status != null && !status.isBlank()) {
            return repository.findByClienteIdAndStatusIgnoreCase(clienteId, status);
        }
        if (clienteId != null && !clienteId.isBlank()) return repository.findByClienteId(clienteId);
        if (status != null && !status.isBlank()) return repository.findByStatusIgnoreCase(status);
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
        solicitacao.setStatus(ABERTA);
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
