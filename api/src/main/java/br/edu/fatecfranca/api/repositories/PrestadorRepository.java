package br.edu.fatecfranca.api.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Prestador;

public interface PrestadorRepository extends MongoRepository<Prestador, String> {
        Optional<Prestador> findByEmail(String email);
         boolean existsByEmail(String email);
         boolean existsByEmailAndIdNot(String email, String id);

}
