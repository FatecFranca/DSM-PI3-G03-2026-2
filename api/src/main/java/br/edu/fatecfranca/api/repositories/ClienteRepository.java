package br.edu.fatecfranca.api.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;


import br.edu.fatecfranca.api.models.Cliente;


public interface ClienteRepository extends MongoRepository<Cliente, String> {
    
}
