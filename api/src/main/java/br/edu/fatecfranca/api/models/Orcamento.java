package br.edu.fatecfranca.api.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Document(collection = "orcamentos")
public class Orcamento {

    @Id
    private String id;

    @NotBlank(message = "A solicitação é obrigatória")
    private String solicitacaoId;

    @NotBlank(message = "O prestador é obrigatório")
    private String prestadorId;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Double valor;

    @NotNull(message = "O prazo é obrigatório")
    @Positive(message = "O prazo deve ser de pelo menos um dia")
    private Integer prazoDias;

    private String mensagem;

    @Pattern(
        regexp = "PENDENTE|ACEITO|RECUSADO|CANCELADO|CONCLUIDO",
        message = "O status deve ser PENDENTE, ACEITO, RECUSADO, CANCELADO ou CONCLUIDO."
    )
    private String status;

    private LocalDateTime dataEnvio;

    public Orcamento() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(String solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(String prestadorId) {
        this.prestadorId = prestadorId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getPrazoDias() {
        return prazoDias;
    }

    public void setPrazoDias(Integer prazoDias) {
        this.prazoDias = prazoDias;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}