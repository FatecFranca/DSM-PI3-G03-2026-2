package br.edu.fatecfranca.api.models;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Cliente {
        
   @Id
   private String id;


   private String nome;
   private String telefone;
   private String email;


   private List<Endereco> enderecos;


   // Construtor genérico
   public Cliente() {
   }


   // Construtor personalizado completo, com todos os atributos
   public Cliente(
           String id,
           String nome,
           String telefone,
           String email,
           List<Endereco> enderecos) {
       this.id = id;
       this.nome = nome;
       this.telefone = telefone;
       this.email = email;
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


   public List<Endereco> getEnderecos() {
        return enderecos;
   }


   public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
   }



   //*
}



