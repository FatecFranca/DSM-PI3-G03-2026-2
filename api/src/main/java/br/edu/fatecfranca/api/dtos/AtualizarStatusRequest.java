package br.edu.fatecfranca.api.dtos;

import br.edu.fatecfranca.api.enums.StatusOrcamento;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusRequest {
    
    @NotNull(message = "O status é obrigatório e deve ser um valor válido")
    private StatusOrcamento status;

    public AtualizarStatusRequest() {
    }

    public StatusOrcamento getStatus() { 
        return status; 
    }

    public void setStatus(StatusOrcamento status) { 
        this.status = status; 
    }
}
