package org.atlas.bank.atlas_bank.domain.model.customer;

import lombok.*;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private Email email;
    private CustomerStatus status;
    private LocalDateTime createdAt;

    public void initDefaults() {
        this.createdAt = LocalDateTime.now();
        if (status == null) status = CustomerStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == CustomerStatus.ACTIVE;
    }
}