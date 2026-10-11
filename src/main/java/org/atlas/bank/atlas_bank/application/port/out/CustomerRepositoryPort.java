package org.atlas.bank.atlas_bank.application.port.out;

import org.atlas.bank.atlas_bank.domain.model.customer.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Optional<Customer> findById(Long customerId);
    Customer save(Customer customer);
}
