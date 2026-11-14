package br.edu.fatecfranca.api.models;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Document(collection = "prestadores")
public class Prestador {

   @Id
   private String id;

   @NotBlank(message = "O nome é obrigatório")
   private String nome;

   @NotBlank(message = "O CPF é obrigatório")
   @CPF(message = "O CPF informado é inválido")
   private String cpf;

   @NotBlank(message = "O telefone é obrigatório")
   private String telefone;

   @NotBlank(message = "O e-mail é obrigatório")

   @Email(message = "Informe um e-mail válido")
   private String email;

   @NotEmpty(message = "Informe ao menos uma especialidade")

   private List<String> especialidade;
   private Boolean verificado;
   private Double notaMedia;
   private Integer qtdAvaliacao;

   @NotEmpty(message = "Informe ao menos um endereço")

   @Valid
   private List<Endereco> enderecos;

   // Construtor genérico
   public Prestador() {
   }

   public Prestador(String id, String nome, String cpf, String telefone, String email, List<String> especialidade,
         Boolean verificado, Double notaMedia, Integer qtdAvaliacao, List<Endereco> enderecos) {
      this.id = id;
      this.nome = nome;
      this.cpf = cpf;
      this.telefone = telefone;
      this.email = email;
      this.especialidade = especialidade;
      this.verificado = verificado;
      this.notaMedia = notaMedia;
      this.qtdAvaliacao = qtdAvaliacao;
      this.enderecos = enderecos;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getCpf() {
      return cpf;
   }

   public void setCpf(String cpf) {
      this.cpf = cpf;
   }

   public String getTelefone() {
      return telefone;
   }

   public void setTelefone(String telefone) {
      this.telefone = telefone;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public List<String> getEspecialidade() {
      return especialidade;
   }

   public void setEspecialidade(List<String> especialidade) {
      this.especialidade = especialidade;
   }

   public Boolean getVerificado() {
      return verificado;
   }

   public void setVerificado(Boolean verificado) {
      this.verificado = verificado;
   }

   public Double getNotaMedia() {
      return notaMedia;
   }

   public void setNotaMedia(Double notaMedia) {
      this.notaMedia = notaMedia;
   }

   public Integer getQtdAvaliacao() {
      return qtdAvaliacao;
   }

   public void setQtdAvaliacao(Integer qtdAvaliacao) {
      this.qtdAvaliacao = qtdAvaliacao;
   }

   public List<Endereco> getEnderecos() {
      return enderecos;
   }

   public void setEnderecos(List<Endereco> enderecos) {
      this.enderecos = enderecos;
   }

   // *

}
