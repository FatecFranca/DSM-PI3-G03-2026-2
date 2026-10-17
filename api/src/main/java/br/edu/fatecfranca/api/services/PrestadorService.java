package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.models.Prestador;
import br.edu.fatecfranca.api.repositories.PrestadorRepository;

@Service
public class PrestadorService {



   private final PrestadorRepository repository;


   public PrestadorService(PrestadorRepository repository) {
       this.repository = repository;
   }


   public List<Prestador> listarTodos() {
       return repository.findAll();
   }


   public Optional<Prestador> buscarPorId(String id) {
       return repository.findById(id);
   }


   public Prestador salvar(Prestador prestador) {
       return repository.save(prestador);
   }


   public void excluir(String id) {
       repository.deleteById(id);
   }

    
}
