package br.edu.fatecfranca.api.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Document(collection = "clientes")
public class Cliente {
        
   @Id
   private String id;


   @NotBlank(message = "O nome é obrigatório")
   private String nome;

   @NotBlank(message = "O CPF é obrigatório")
   private String cpf;

   @NotBlank(message = "O telefone é obrigatório")
   private String telefone;

   @NotBlank(message = "O e-mail é obrigatório")
   
   @Email(message = "Informe um e-mail válido")
   private String email;


   @NotEmpty(message = "Informe ao menos um endereço")
   @Valid
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



