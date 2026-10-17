package br.edu.fatecfranca.api.models;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Prestador {

   @Id
   private String id;


   private String nome;
   private String cpf;
   private String telefone;
   private String especialidade;
   private String verificado;
   private String notaMedia;
   private String qtdAvaliacao;


   private List<Endereco> enderecos;


   // Construtor genérico
   public Prestador() {
   }


   public Prestador(String id, String nome, String cpf, String telefone, String especialidade, String verificado,
        String notaMedia, String qtdAvaliacao, List<Endereco> enderecos) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
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
