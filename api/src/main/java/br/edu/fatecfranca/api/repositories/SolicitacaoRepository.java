package br.edu.fatecfranca.api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Solicitacao;

public interface SolicitacaoRepository extends MongoRepository<Solicitacao, String> {
    List<Solicitacao> findByClienteId(String clienteId);

    List<Solicitacao> findByStatusIgnoreCase(String status);

    List<Solicitacao> findByClienteIdAndStatusIgnoreCase(String clienteId, String status);
    
}
