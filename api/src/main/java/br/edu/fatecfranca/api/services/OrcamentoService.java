package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.OrcamentoResponse;
import br.edu.fatecfranca.api.models.Orcamento;
import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.repositories.OrcamentoRepository;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;

@Service
public class OrcamentoService {

    private final OrcamentoRepository repository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final PrestadorRepository prestadorRepository;

    public OrcamentoService(
            OrcamentoRepository repository,
            SolicitacaoRepository solicitacaoRepository,
            PrestadorRepository prestadorRepository) {
        this.repository = repository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.prestadorRepository = prestadorRepository;
    }

    // Este método monta uma resposta para o GET de /orcamentos
    // incluindo os dados da solicitação e do prestador
    private OrcamentoResponse montarResposta(Orcamento orcamento) {
        Solicitacao solicitacao = solicitacaoRepository.findById(orcamento.getSolicitacaoId())
                .orElse(null);

        Prestador prestador = prestadorRepository.findById(orcamento.getPrestadorId())
                .orElse(null);

        return new OrcamentoResponse(
                orcamento.getId(),
                orcamento.getSolicitacaoId(),
                orcamento.getPrestadorId(),
                orcamento.getValor(),
                orcamento.getPrazoDias(),
                orcamento.getMensagem(),
                orcamento.getStatus(),
                orcamento.getDataEnvio(),
                solicitacao,
                prestador);
    }

    public List<OrcamentoResponse> listarComDetalhes() {
        return repository.findAll().stream()
                .map(this::montarResposta)
                .toList();
    }

    public Optional<OrcamentoResponse> buscarComDetalhes(String id) {
        return repository.findById(id)
                .map(this::montarResposta);
    }

    public List<OrcamentoResponse> listarPorSolicitacaoComDetalhes(String solicitacaoId) {
        if (!solicitacaoRepository.existsById(solicitacaoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada.");
        }
        return repository.findBySolicitacaoId(solicitacaoId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<OrcamentoResponse> listarPorPrestadorComDetalhes(String prestadorId) {
        if (!prestadorRepository.existsById(prestadorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado.");
        }
        return repository.findByPrestadorId(prestadorId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public Optional<Orcamento> buscarPorId(String id) {
        return repository.findById(id);
    }

    // Busca o orçamento ou devolve 404
    private Orcamento buscarOuFalhar(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orçamento não encontrado."));
    }

    // Busca a solicitação do orçamento ou devolve 404
    private Solicitacao buscarSolicitacao(String solicitacaoId) {
        return solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solicitação não encontrada."));
    }

    public Orcamento criar(Orcamento orcamento) {
        Solicitacao solicitacao = buscarSolicitacao(orcamento.getSolicitacaoId());

        if (!prestadorRepository.existsById(orcamento.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado.");
        }

        // só aceita orçamento enquanto o pedido está aberto
        if (!"ABERTA".equals(solicitacao.getStatus())
                && !"ORCAMENTO_ENVIADO".equals(solicitacao.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A solicitação não aceita novos orçamentos.");
        }

        if (repository.existsBySolicitacaoIdAndPrestadorId(
                orcamento.getSolicitacaoId(), orcamento.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este prestador já enviou orçamento para a solicitação.");
        }

        orcamento.setId(null);
        orcamento.setStatus("PENDENTE");
        orcamento.setDataEnvio(LocalDateTime.now());

        solicitacao.setStatus("ORCAMENTO_ENVIADO");
        solicitacaoRepository.save(solicitacao);

        return repository.save(orcamento);
    }

    // novoStatus já chega validado pelo AtualizarStatusRequest: ACEITO ou RECUSADO
    public Orcamento atualizarStatus(String id, String novoStatus) {
        Orcamento orcamento = buscarOuFalhar(id);

        if (!"PENDENTE".equals(orcamento.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Somente orçamentos pendentes podem ser decididos.");
        }

        Solicitacao solicitacao = buscarSolicitacao(orcamento.getSolicitacaoId());

        if ("ACEITO".equals(novoStatus)) {
            if (repository.existsBySolicitacaoIdAndStatus(orcamento.getSolicitacaoId(), "ACEITO")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Já existe orçamento aceito para esta solicitação.");
            }

            solicitacao.setStatus("EM_ANDAMENTO");
            solicitacaoRepository.save(solicitacao);

            // os outros orçamentos pendentes do mesmo pedido são recusados
            for (Orcamento outro : repository.findBySolicitacaoId(orcamento.getSolicitacaoId())) {
                if (!outro.getId().equals(id) && "PENDENTE".equals(outro.getStatus())) {
                    outro.setStatus("RECUSADO");
                    repository.save(outro);
                }
            }
        }

        orcamento.setStatus(novoStatus);
        return repository.save(orcamento);
    }

    public Orcamento concluir(String id) {
        Orcamento orcamento = buscarOuFalhar(id);

        if (!"ACEITO".equals(orcamento.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Somente um orçamento aceito pode ser concluído.");
        }

        Solicitacao solicitacao = buscarSolicitacao(orcamento.getSolicitacaoId());

        orcamento.setStatus("CONCLUIDO");
        solicitacao.setStatus("CONCLUIDA");

        solicitacaoRepository.save(solicitacao);
        return repository.save(orcamento);
    }
}