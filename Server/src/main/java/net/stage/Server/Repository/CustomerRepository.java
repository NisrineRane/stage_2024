package net.stage.Server.Repository;

import net.stage.Server.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//un repository est une interface dans Spring Data JPA qui est utilisée pour accéder à la base de données.
// CustomerRepository hérite des fonctionnalités de JpaRepository pour interagir avec CustomerEntity dans la base de données
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    // JpaRepository: une interface fournie par Spring Data JPA
    // Long : Le type de l'identifiant de l'entité
    // jparepository fournit divers methods comme :save(), findById(), findAll(), deleteById()
}