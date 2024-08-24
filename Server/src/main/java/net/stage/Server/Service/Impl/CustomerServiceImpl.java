package net.stage.Server.Service.Impl;

import net.stage.Server.Dto.CustomerDto;
import net.stage.Server.Entity.CustomerEntity;
import net.stage.Server.Repository.CustomerRepository;
import net.stage.Server.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerEntity addCustomer(CustomerDto customerDto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Optional<CustomerEntity> getCustomer(Long id) {
        return Optional.ofNullable(customerRepository.findById(id).orElse(null));
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity updateCustomer(Long id, CustomerDto customerDto) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            return customerRepository.save(customer);
        }
        throw new IllegalArgumentException("Customer not found");
    }

    @Override
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Customer not found");
        }

    }
}