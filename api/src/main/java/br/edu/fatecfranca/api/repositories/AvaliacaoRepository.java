package br.edu.fatecfranca.api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Avaliacao;

public interface AvaliacaoRepository extends MongoRepository<Avaliacao, String> {
    List<Avaliacao> findByPrestadorId(String prestadorId);

    List<Avaliacao> findByClienteId(String clienteId);

    List<Avaliacao> findByPrestadorIdAndClienteId(String prestadorId, String clienteId);

    boolean existsByOrcamentoId(String orcamentoId);
    
}
