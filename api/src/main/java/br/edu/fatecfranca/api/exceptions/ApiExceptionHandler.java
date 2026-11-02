package br.edu.fatecfranca.api.exceptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
      @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> tratarValidacao(
            MethodArgumentNotValidException exception) {

        Map<String, List<String>> erros = new LinkedHashMap<>();

        for (var erro : exception.getBindingResult().getFieldErrors()) {
            String campo = erro.getField();
            if (!erros.containsKey(campo)) {
                erros.put(campo, new ArrayList<>());
            }
            erros.get(campo).add(erro.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> tratarStatus(ResponseStatusException exception) {
        return resposta(HttpStatus.valueOf(exception.getStatusCode().value()), exception.getReason());
    }

    private ResponseEntity<Map<String, Object>> resposta(HttpStatus status, String mensagem) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("erro", status.getReasonPhrase());
        body.put("mensagem", mensagem);
        return ResponseEntity.status(status).body(body);
    }
}
