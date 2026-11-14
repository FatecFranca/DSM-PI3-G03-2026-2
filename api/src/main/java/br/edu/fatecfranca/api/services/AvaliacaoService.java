package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import br.edu.fatecfranca.api.models.Avaliacao;
import br.edu.fatecfranca.api.models.Orcamento;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.repositories.AvaliacaoRepository;
import br.edu.fatecfranca.api.repositories.ClienteRepository;
import br.edu.fatecfranca.api.repositories.OrcamentoRepository;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository repository;
    private final OrcamentoRepository orcamentoRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final ClienteRepository clienteRepository;
    private final PrestadorRepository prestadorRepository;

    public AvaliacaoService(AvaliacaoRepository repository, OrcamentoRepository orcamentoRepository,
            SolicitacaoRepository solicitacaoRepository, ClienteRepository clienteRepository,
            PrestadorRepository prestadorRepository) {
        this.repository = repository;
        this.orcamentoRepository = orcamentoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.clienteRepository = clienteRepository;
        this.prestadorRepository = prestadorRepository;
    }

    public List<Avaliacao> listar(String prestadorId, String clienteId) {
        if (prestadorId != null && !prestadorId.isBlank() && clienteId != null && !clienteId.isBlank()) {
            return repository.findByPrestadorIdAndClienteId(prestadorId, clienteId);
        }
        if (prestadorId != null && !prestadorId.isBlank()) return repository.findByPrestadorId(prestadorId);
        if (clienteId != null && !clienteId.isBlank()) return repository.findByClienteId(clienteId);
        return repository.findAll();
    }

    public Optional<Avaliacao> buscarPorId(String id) { return repository.findById(id); }

    public Avaliacao criar(Avaliacao avaliacao) {
        Orcamento orcamento = orcamentoRepository.findById(avaliacao.getOrcamentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado"));
        if (!"CONCLUIDO".equals(orcamento.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O serviço precisa estar concluído antes da avaliação");
        }
        if (repository.existsByOrcamentoId(avaliacao.getOrcamentoId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este orçamento já possui avaliação");
        }
        Solicitacao solicitacao = solicitacaoRepository.findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação vinculada não encontrada"));
        if (!solicitacao.getClienteId().equals(avaliacao.getClienteId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliação deve ser feita pelo cliente da solicitação");
        }
        if (!orcamento.getPrestadorId().equals(avaliacao.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O prestador informado não corresponde ao orçamento");
        }
        if (!clienteRepository.existsById(avaliacao.getClienteId()) || !prestadorRepository.existsById(avaliacao.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente ou prestador não encontrado");
        }

        avaliacao.setId(null);
        avaliacao.setData(LocalDateTime.now());
        Avaliacao salva = repository.save(avaliacao);

        Prestador prestador = prestadorRepository.findById(avaliacao.getPrestadorId()).orElseThrow();
        int quantidadeAnterior = prestador.getQtdAvaliacao() == null ? 0 : prestador.getQtdAvaliacao();
        double mediaAnterior = prestador.getNotaMedia() == null ? 0.0 : prestador.getNotaMedia();
        double novaMedia = ((mediaAnterior * quantidadeAnterior) + avaliacao.getNota()) / (quantidadeAnterior + 1);
        prestador.setNotaMedia(Math.round(novaMedia * 100.0) / 100.0);
        prestador.setQtdAvaliacao(quantidadeAnterior + 1);
        prestadorRepository.save(prestador);
        return salva;
    }
}
