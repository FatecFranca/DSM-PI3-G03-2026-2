package br.edu.fatecfranca.api.dtos;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.models.Foto;

// Solicitação com os dados do cliente que abriu o pedido
public record SolicitacaoResponse(
        String id,
        String clienteId,
        String descricao,
        List<Foto> fotos,
        LocalDateTime data,
        String status,
        Cliente cliente) {
}