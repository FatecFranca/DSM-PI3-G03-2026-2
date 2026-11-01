package br.edu.fatecfranca.api.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "avaliacoes")
public class Avaliacao {


    @Id
    private String id;

    @NotBlank(message = "O prestador é obrigatório")
    private String prestadorId;

    @NotBlank(message = "O cliente é obrigatório")
    private String clienteId;

    @NotBlank(message = "O orçamento é obrigatório")
    private String orcamentoId;

    @NotNull(message = "A nota é obrigatória")

    @DecimalMin(value = "1.0", message = "A nota mínima é 1")

    @DecimalMax(value = "5.0", message = "A nota máxima é 5")

    private Double nota;
    private String comentario;
    private LocalDateTime data;

    public Avaliacao() {
    }

    public String getId() { 
        return id; 
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public String getPrestadorId() { 
        return prestadorId; 
    }

    public void setPrestadorId(String prestadorId) { 
        this.prestadorId = prestadorId; 
    }

    public String getClienteId() { 
        return clienteId; 
    }

    public void setClienteId(String clienteId) { 
        this.clienteId = clienteId; 
    }

    public String getOrcamentoId() { 
        return orcamentoId; 
    }

    public void setOrcamentoId(String orcamentoId) { 
        this.orcamentoId = orcamentoId; 
    }

    public Double getNota() { 
        return nota; 
    }

    public void setNota(Double nota) { 
        this.nota = nota; 
    }

    public String getComentario() { 
        return comentario; 
    }

    public void setComentario(String comentario) { 
        this.comentario = comentario; 
    }

    public LocalDateTime getData() { 
        return data; 
    }

    public void setData(LocalDateTime data) { 
        this.data = data; 
    }
    
}