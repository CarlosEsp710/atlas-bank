package org.atlas.bank.atlas_bank.costumer.model;

import jakarta.persistence.*;
import lombok.*;
import org.atlas.bank.atlas_bank.shared.model.Email;

import java.time.LocalDateTime;

@Entity
@Table(name = "costumers")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CostumerStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (status == null) status = CostumerStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == CostumerStatus.ACTIVE;
    }
}
