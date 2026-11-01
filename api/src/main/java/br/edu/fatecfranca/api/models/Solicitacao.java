package br.edu.fatecfranca.api.models;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Document(collection = "solicitacoes")
public class Solicitacao {


    @Id
    private String id;

    @NotBlank(message = "O clienteId é obrigatório")
    private String clienteId;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotEmpty(message = "Informe ao menos uma foto")

    
    @Valid
    private List<Foto> fotos;
    private LocalDateTime data;
    private String status;

    public Solicitacao() {
    }

    public String getId() {
         return id;
        }

    public void setId(String id) {
         this.id = id;
        }

    public String getClienteId() {
         return clienteId;
         }

    public void setClienteId(String clienteId) {
         this.clienteId = clienteId; 
        }

    public String getDescricao() {
         return descricao; 
        }

    public void setDescricao(String descricao) {
         this.descricao = descricao; 
        }

    public List<Foto> getFotos() {
         return fotos; 
        }

    public void setFotos(List<Foto> fotos) {
         this.fotos = fotos; 
        }

    public LocalDateTime getData() {
         return data;
         }

    public void setData(LocalDateTime data) {
         this.data = data;
         }

    public String getStatus() {
         return status; 

        }
    
    public void setStatus(String status) {
         this.status = status; 
        }
}

