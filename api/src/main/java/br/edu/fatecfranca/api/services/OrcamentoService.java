package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.enums.StatusOrcamento;
import br.edu.fatecfranca.api.enums.StatusSolicitacao;
import br.edu.fatecfranca.api.models.Orcamento;
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

    public List<Orcamento> listar(String solicitacaoId, String prestadorId) {
        if (solicitacaoId != null && !solicitacaoId.isBlank()
                && prestadorId != null && !prestadorId.isBlank()) {
            return repository.findBySolicitacaoIdAndPrestadorId(
                    solicitacaoId, prestadorId);
        }

        if (solicitacaoId != null && !solicitacaoId.isBlank()) {
            return repository.findBySolicitacaoId(solicitacaoId);
        }

        if (prestadorId != null && !prestadorId.isBlank()) {
            return repository.findByPrestadorId(prestadorId);
        }

        return repository.findAll();
    }

    public Optional<Orcamento> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Orcamento criar(Orcamento orcamento) {
        Solicitacao solicitacao = solicitacaoRepository
                .findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solicitação não encontrada"));

        if (!prestadorRepository.existsById(orcamento.getPrestadorId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Prestador não encontrado");
        }

        StatusSolicitacao statusSolicitacao = solicitacao.getStatus();

        if (statusSolicitacao != StatusSolicitacao.ABERTA
                && statusSolicitacao != StatusSolicitacao.ORCAMENTO_ENVIADO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não aceita novos orçamentos");
        }

        if (repository.existsBySolicitacaoIdAndPrestadorId(
                orcamento.getSolicitacaoId(), orcamento.getPrestadorId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este prestador já enviou orçamento para a solicitação");
        }

        orcamento.setId(null);
        orcamento.setStatus(StatusOrcamento.PENDENTE);
        orcamento.setDataEnvio(LocalDateTime.now());

        solicitacao.setStatus(StatusSolicitacao.ORCAMENTO_ENVIADO);
        solicitacaoRepository.save(solicitacao);

        return repository.save(orcamento);
    }

    public Orcamento atualizarStatus(String id, StatusOrcamento novoStatus) {
    if (novoStatus != StatusOrcamento.ACEITO
            && novoStatus != StatusOrcamento.RECUSADO) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Status permitido: ACEITO ou RECUSADO");
    }

        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orçamento não encontrado"));

        if (orcamento.getStatus() != StatusOrcamento.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente orçamentos pendentes podem ser decididos");
        }

        Solicitacao solicitacao = solicitacaoRepository
                .findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação vinculada não encontrada"));

        if (novoStatus == StatusOrcamento.ACEITO) {
            boolean jaExisteAceito =
                    repository.existsBySolicitacaoIdAndStatus(
                            orcamento.getSolicitacaoId(),
                            StatusOrcamento.ACEITO);

            if (jaExisteAceito) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Já existe orçamento aceito para esta solicitação");
            }

            solicitacao.setStatus(StatusSolicitacao.EM_ANDAMENTO);

            List<Orcamento> outrosOrcamentos =
                    repository.findBySolicitacaoId(
                            orcamento.getSolicitacaoId());

            for (Orcamento outro : outrosOrcamentos) {
                if (!outro.getId().equals(id)
                        && outro.getStatus() == StatusOrcamento.PENDENTE) {
                    outro.setStatus(StatusOrcamento.RECUSADO);
                    repository.save(outro);
                }
            }
        }

        orcamento.setStatus(novoStatus);
        solicitacaoRepository.save(solicitacao);

        return repository.save(orcamento);
    }

    public Orcamento concluir(String id) {
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orçamento não encontrado"));

        if (orcamento.getStatus() != StatusOrcamento.ACEITO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente um orçamento aceito pode ser concluído");
        }

        Solicitacao solicitacao = solicitacaoRepository
                .findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação vinculada não encontrada"));

        orcamento.setStatus(StatusOrcamento.CONCLUIDO);
        solicitacao.setStatus(StatusSolicitacao.CONCLUIDA);

        solicitacaoRepository.save(solicitacao);
        return repository.save(orcamento);
    }
}
