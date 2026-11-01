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


   public static void setId(String id2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setId'");
   }

        
}
