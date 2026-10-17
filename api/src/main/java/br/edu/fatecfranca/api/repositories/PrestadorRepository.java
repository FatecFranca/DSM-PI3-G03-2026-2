package br.edu.fatecfranca.api.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;


import br.edu.fatecfranca.api.models.Prestador;

public interface  PrestadorRepository  extends MongoRepository<Prestador, String> {
    
}
