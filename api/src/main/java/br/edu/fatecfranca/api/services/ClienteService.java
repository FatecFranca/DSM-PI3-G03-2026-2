package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.models.Cliente;
import br.edu.fatecfranca.api.repositories.ClienteRepository;

@Service
public class ClienteService {
    



   private final ClienteRepository repository;


   public ClienteService(ClienteRepository repository) {
       this.repository = repository;
   }


   public List<Cliente> listarTodos() {
       return repository.findAll();
   }


   public Optional<Cliente> buscarPorId(String id) {
       return repository.findById(id);
   }


   public Cliente salvar(Cliente cliente) {
       return repository.save(cliente);
   }


   public void excluir(String id) {
       repository.deleteById(id);
   }

}
