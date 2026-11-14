package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.SolicitacaoResponse;
import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.repositories.ClienteRepository;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository repository;
    private final ClienteRepository clienteRepository;

    public SolicitacaoService(SolicitacaoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    // Este método monta uma resposta para o GET de /solicitacoes
    // incluindo os dados do cliente
    private SolicitacaoResponse montarResposta(Solicitacao solicitacao) {
        Cliente cliente = clienteRepository.findById(solicitacao.getClienteId())
                .orElse(null);

        return new SolicitacaoResponse(
                solicitacao.getId(),
                solicitacao.getClienteId(),
                solicitacao.getDescricao(),
                solicitacao.getFotos(),
                solicitacao.getData(),
                solicitacao.getStatus(),
                cliente);
    }

    public List<SolicitacaoResponse> listarComDetalhes() {
        return repository.findAll().stream()
                .map(this::montarResposta)
                .toList();
    }

    public Optional<SolicitacaoResponse> buscarComDetalhes(String id) {
        return repository.findById(id)
                .map(this::montarResposta);
    }

    public List<SolicitacaoResponse> listarPorClienteComDetalhes(String clienteId) {
        verificarCliente(clienteId);
        return repository.findByClienteId(clienteId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<SolicitacaoResponse> listarPorStatusComDetalhes(String status) {
        return repository.findByStatus(status.toUpperCase()).stream()
                .map(this::montarResposta)
                .toList();
    }

    private void verificarCliente(String clienteId) {
        if (clienteId == null || clienteId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O identificador do cliente é obrigatório.");
        }
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }
    }

    public Optional<Solicitacao> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Solicitacao criar(Solicitacao solicitacao) {
        verificarCliente(solicitacao.getClienteId());

        solicitacao.setId(null);
        solicitacao.setData(LocalDateTime.now());
        solicitacao.setStatus("ABERTA");

        for (var foto : solicitacao.getFotos()) {
            if (foto.getDataEnvio() == null) {
                foto.setDataEnvio(LocalDateTime.now());
            }
        }

        return repository.save(solicitacao);
    }
}