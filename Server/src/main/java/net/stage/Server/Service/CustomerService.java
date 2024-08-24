package net.stage.Server.Service;

import net.stage.Server.Dto.CustomerDto;
import net.stage.Server.Entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerEntity> getAllCustomers();

    Optional<CustomerEntity> getCustomer(Long id); // Retourne un Optional pour le cas où le client n'existe pas

    CustomerEntity addCustomer(CustomerDto customerDto);

    CustomerEntity updateCustomer(Long id, CustomerDto customerDto); // Pour enregistrer ou modifier

    void deleteCustomer(Long id); // Suppression

}