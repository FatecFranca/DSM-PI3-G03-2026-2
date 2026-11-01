package br.edu.fatecfranca.api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.edu.fatecfranca.api.models.Orcamento;

public interface OrcamentoRepository extends MongoRepository<Orcamento, String> {
    List<Orcamento> findBySolicitacaoId(String solicitacaoId);

    List<Orcamento> findByPrestadorId(String prestadorId);

    List<Orcamento> findBySolicitacaoIdAndPrestadorId(String solicitacaoId, String prestadorId);
    
    boolean existsBySolicitacaoIdAndPrestadorId(String solicitacaoId, String prestadorId);

    boolean existsBySolicitacaoIdAndStatusIgnoreCase(String solicitacaoId, String status);
    
}
