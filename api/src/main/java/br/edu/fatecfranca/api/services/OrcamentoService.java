package br.edu.fatecfranca.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Orcamento;
import br.edu.fatecfranca.api.models.Solicitacao;
import br.edu.fatecfranca.api.repositories.OrcamentoRepository;
import br.edu.fatecfranca.api.repositories.SolicitacaoRepository;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;

@Service
public class OrcamentoService {
    private final OrcamentoRepository repository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final PrestadorRepository prestadorRepository;

    public OrcamentoService(OrcamentoRepository repository, SolicitacaoRepository solicitacaoRepository,
            PrestadorRepository prestadorRepository) {
        this.repository = repository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.prestadorRepository = prestadorRepository;
    }

    public List<Orcamento> listar(String solicitacaoId, String prestadorId) {
        if (solicitacaoId != null && !solicitacaoId.isBlank() && prestadorId != null && !prestadorId.isBlank()) {
            return repository.findBySolicitacaoIdAndPrestadorId(solicitacaoId, prestadorId);
        }
        if (solicitacaoId != null && !solicitacaoId.isBlank()) return repository.findBySolicitacaoId(solicitacaoId);
        if (prestadorId != null && !prestadorId.isBlank()) return repository.findByPrestadorId(prestadorId);
        return repository.findAll();
    }

    public Optional<Orcamento> buscarPorId(String id) { return repository.findById(id); }

    public Orcamento criar(Orcamento orcamento) {
        Solicitacao solicitacao = solicitacaoRepository.findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada"));
        if (!prestadorRepository.existsById(orcamento.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado");
        }
        if (SolicitacaoService.FECHADA.equalsIgnoreCase(solicitacao.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A solicitação já foi fechada");
        }
        if (repository.existsBySolicitacaoIdAndPrestadorId(orcamento.getSolicitacaoId(), orcamento.getPrestadorId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este prestador já enviou orçamento para a solicitação");
        }
        orcamento.setId(null);
        orcamento.setStatus("PENDENTE");
        orcamento.setDataEnvio(LocalDateTime.now());
        solicitacao.setStatus(SolicitacaoService.EM_NEGOCIACAO);
        solicitacaoRepository.save(solicitacao);
        return repository.save(orcamento);
    }

    public Orcamento atualizarStatus(String id, String statusRecebido) {
        String status = statusRecebido == null ? "" : statusRecebido.trim().toUpperCase();
        if (!status.equals("ACEITO") && !status.equals("RECUSADO")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status permitido: ACEITO ou RECUSADO");
        }
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado"));
        if (!"PENDENTE".equalsIgnoreCase(orcamento.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Somente orçamentos pendentes podem ser decididos");
        }
        Solicitacao solicitacao = solicitacaoRepository.findById(orcamento.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação vinculada não encontrada"));
        if ("ACEITO".equals(status)) {
            if (repository.existsBySolicitacaoIdAndStatusIgnoreCase(orcamento.getSolicitacaoId(), "ACEITO")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe orçamento aceito para esta solicitação");
            }
            solicitacao.setStatus(SolicitacaoService.FECHADA);
            for (Orcamento outro : repository.findBySolicitacaoId(orcamento.getSolicitacaoId())) {
                if (!outro.getId().equals(id) && "PENDENTE".equalsIgnoreCase(outro.getStatus())) {
                    outro.setStatus("RECUSADO");
                    repository.save(outro);
                }
            }
        }
        orcamento.setStatus(status);
        solicitacaoRepository.save(solicitacao);
        return repository.save(orcamento);
    }

    public Orcamento concluir(String id) {
        Orcamento orcamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado"));
        if (!"ACEITO".equalsIgnoreCase(orcamento.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Somente um orçamento aceito pode ser concluído");
        }
        orcamento.setStatus("CONCLUIDO");
        return repository.save(orcamento);
    }
}
