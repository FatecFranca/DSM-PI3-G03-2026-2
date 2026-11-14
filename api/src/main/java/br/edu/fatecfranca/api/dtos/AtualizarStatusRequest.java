package br.edu.fatecfranca.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// O que o front manda para aceitar ou recusar um orçamento
public record AtualizarStatusRequest(

        @NotBlank(message = "O status é obrigatório.")
        @Pattern(regexp = "ACEITO|RECUSADO", message = "O status deve ser ACEITO ou RECUSADO.")
        String status) {
}