package br.edu.fatecfranca.api.models;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

public class Foto {
    @NotBlank(message = "A URL da foto é obrigatória")
    private String url;
    private String legenda;
    private LocalDateTime dataEnvio;

    public Foto() {
    }

    public Foto(String url, String legenda, LocalDateTime dataEnvio) {
        this.url = url;
        this.legenda = legenda;
        this.dataEnvio = dataEnvio;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getLegenda() { return legenda; }
    public void setLegenda(String legenda) { this.legenda = legenda; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}
