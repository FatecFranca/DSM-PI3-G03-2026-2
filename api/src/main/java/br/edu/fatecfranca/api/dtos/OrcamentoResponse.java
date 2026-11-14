package br.edu.fatecfranca.api.dtos;

import java.time.LocalDateTime;

import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.models.Solicitacao;

// Orçamento com os dados da solicitação e do prestador vinculados
public record OrcamentoResponse(
        String id,
        String solicitacaoId,
        String prestadorId,
        Double valor,
        Integer prazoDias,
        String mensagem,
        String status,
        LocalDateTime dataEnvio,
        Solicitacao solicitacao,
        Prestador prestador) {
}