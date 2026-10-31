package br.edu.fatecfranca.api.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prestadores")
public class Prestador {

   @Id
   private String id;


   private String nome;
   private String cpf;
   private String telefone;
   private String email; 
   private List<String> especialidade;
   private Boolean verificado;
   private Double notaMedia;
   private Integer qtdAvaliacao;


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


   
//*
   

        
}
