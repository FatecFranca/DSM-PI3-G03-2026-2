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

import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {



   private final ClienteService service;


   public ClienteController(ClienteService service) {
       this.service = service;
   }


   @GetMapping
   public List<Cliente> listarTodos() {
       return service.listarTodos();
   }


   @GetMapping("/{id}")
   public ResponseEntity<Cliente> buscarPorId(@PathVariable String id) {
       return service.buscarPorId(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }


   @PostMapping
   public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
       Cliente clienteSalvo = service.salvar(cliente);


       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(clienteSalvo);
   }


   @PutMapping("/{id}")
   public ResponseEntity<Cliente> atualizar(
           @PathVariable String id,
           @RequestBody Cliente cliente) {


       if (service.buscarPorId(id).isEmpty()) {
           return ResponseEntity.notFound().build();
       }


       cliente.setId(id);


       Cliente clienteAtualizado = service.salvar(cliente);


       return ResponseEntity.ok(clienteAtualizado);
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
