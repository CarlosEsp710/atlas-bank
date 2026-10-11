package org.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import org.atlas.bank.atlas_bank.domain.model.customer.Customer;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceMapper {
    public Customer toDomain(CustomerJpaEntity customerJpaEntity) {
        if (customerJpaEntity == null) return null;
        return Customer.builder()
                .id(customerJpaEntity.getId())
                .name(customerJpaEntity.getName())
                .email(customerJpaEntity.getEmail() != null
                        ? Email.of(customerJpaEntity.getEmail().getValue())
                        : null
                )
                .status(customerJpaEntity.getStatus())
                .createdAt(customerJpaEntity.getCreatedAt())
                .build();
    }

    public CustomerJpaEntity toEntity(Customer customer) {
        if (customer == null) return null;
        CustomerJpaEntity customerJpaEntity = new CustomerJpaEntity();
        customerJpaEntity.setId(customer.getId());
        customerJpaEntity.setName(customer.getName());
        customerJpaEntity.setEmail(customer.getEmail() != null
                ? Email.of(customer.getEmail().getValue())
                : null
        );
        customerJpaEntity.setStatus(customer.getStatus());
        customerJpaEntity.setCreatedAt(customer.getCreatedAt());
        return customerJpaEntity;
    }
}
