package br.edu.fatecfranca.api.repositories;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import br.edu.fatecfranca.api.models.Cliente;


public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, String id);

}

