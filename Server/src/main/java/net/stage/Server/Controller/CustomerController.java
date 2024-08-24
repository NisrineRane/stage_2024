package net.stage.Server.Controller;
//controller c'est l'intermediare entre client et server en utilisants HTTP requests(Handling HTTP Requests,Mapping Requests to Services,Returning Responses)

import net.stage.Server.Dto.CustomerDto;
import net.stage.Server.Entity.CustomerEntity;
import net.stage.Server.Response.ApiResponse;
import net.stage.Server.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController //est utilisée dans les applications Spring pour marquer une classe comme un contrôleur web qui gère les requêtes HTTP et retourne des réponses JSON ou XML
@RequestMapping("/api/v1/customers") // Définit le chemin de base pour toutes les requêtes concernant les clients dans l'API version 1
public class CustomerController {

    @Autowired //injection de dependences
    private CustomerService customerService;// Injection automatique de l'instance de CustomerService


    @PostMapping
    public ResponseEntity<ApiResponse<CustomerEntity>> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerEntity createdCustomer = customerService.addCustomer(customerDto);
        ApiResponse<CustomerEntity> response = ApiResponse.<CustomerEntity>builder()
                .message("Customer created successfully")
                .code(201) // HTTP status code for created
                .data(createdCustomer)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerEntity>> getCustomerById(@PathVariable Long id) {
        Optional<CustomerEntity> customer = customerService.getCustomer(id);
        if (customer.isPresent()) {
            ApiResponse<CustomerEntity> response = ApiResponse.<CustomerEntity>builder()
                    .message("Customer retrieved successfully")
                    .code(200) // HTTP status code for OK
                    .data(customer.get())
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<CustomerEntity> response = ApiResponse.<CustomerEntity>builder()
                    .message("Customer not found")
                    .code(404) // HTTP status code for Not Found
                    .error("Customer with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerEntity>>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.getAllCustomers();
        ApiResponse<List<CustomerEntity>> response = ApiResponse.<List<CustomerEntity>>builder()
                .message("Customers retrieved successfully")
                .code(200)
                .data(customers)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerEntity>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDto customerDto) {
        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customerDto);
        if (updatedCustomer != null) {
            ApiResponse<CustomerEntity> response = ApiResponse.<CustomerEntity>builder()
                    .message("Customer updated successfully")
                    .code(200)
                    .data(updatedCustomer)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<CustomerEntity> response = ApiResponse.<CustomerEntity>builder()
                    .message("Customer not found")
                    .code(404)
                    .error("Customer with ID " + id + " does not exist")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Customer deleted successfully")
                .code(204) // HTTP status code for No Content
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.noContent().build();
    }
}