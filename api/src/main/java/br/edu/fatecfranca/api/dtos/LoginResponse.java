package br.edu.fatecfranca.api.dtos;

// O que a API devolve no login (o front guarda no localStorage)
public record LoginResponse(
        String id,
        String nome,
        String tipo) {
}
