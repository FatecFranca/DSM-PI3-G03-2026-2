package br.edu.fatecfranca.api.dtos;

import jakarta.validation.constraints.NotBlank;

public class AtualizarStatusRequest {
    
    @NotBlank(message = "O status é obrigatório")
    private String status;

    public AtualizarStatusRequest() {
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }
}
