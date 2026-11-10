package com.atlas.bank.application.port.out;

import com.atlas.bank.domain.model.customer.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
}
