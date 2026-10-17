package br.edu.fatecfranca.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.services.PrestadorService;

@RestController
@RequestMapping("/prestadores")

public class PrestadorController {



   private final PrestadorService service;


   public PrestadorController(PrestadorService service) {
       this.service = service;
   }


   @GetMapping
   public List<Prestador> listarTodos() {
       return service.listarTodos();
   }


   @GetMapping("/{id}")
   public ResponseEntity<Prestador> buscarPorId(@PathVariable String id) {
       return service.buscarPorId(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }


   @PostMapping
   public ResponseEntity<Prestador> criar(@RequestBody Prestador prestador) {
       Prestador prestadorSalvo = service.salvar(prestador);


       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(prestadorSalvo);
   }


   @PutMapping("/{id}")
   public ResponseEntity<Prestador> atualizar(
           @PathVariable String id,
           @RequestBody Prestador prestador) {


       if (service.buscarPorId(id).isEmpty()) {
           return ResponseEntity.notFound().build();
       }


       Prestador.setId(id);


       Prestador prestadorAtualizado = service.salvar(prestador);


       return ResponseEntity.ok(prestadorAtualizado);
   }


   @DeleteMapping("/{id}")
   public ResponseEntity<Void> excluir(@PathVariable String id) {


       if (service.buscarPorId(id).isEmpty()) {
           return ResponseEntity.notFound().build();
       }


       service.excluir(id);


       return ResponseEntity.noContent().build();
   }

        
}
