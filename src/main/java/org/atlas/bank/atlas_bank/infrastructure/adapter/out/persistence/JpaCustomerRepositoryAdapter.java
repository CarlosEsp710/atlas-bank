package org.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.CustomerRepositoryPort;
import org.atlas.bank.atlas_bank.domain.model.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final SpringDataCustomerRepository repository;
    private final CustomerPersistenceMapper mapper;

    @Override
    public Optional<Customer> findById(Long customerId) {
        return repository.findById(customerId).map(mapper::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        customer.initDefaults();
        CustomerJpaEntity customerJpaEntity = mapper.toEntity(customer);
        CustomerJpaEntity savedCustomerJpaEntity = repository.save(customerJpaEntity);
        return mapper.toDomain(savedCustomerJpaEntity);
    }
}
