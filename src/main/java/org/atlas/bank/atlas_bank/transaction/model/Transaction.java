package org.atlas.bank.atlas_bank.transaction.model;

import jakarta.persistence.*;
import lombok.*;
import org.atlas.bank.atlas_bank.transaction.model.state.*;
import org.atlas.bank.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Transaction extends AbstractAggregateRoot<Transaction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(name = "source_account_id", nullable = false)
    private Long sourceAccountId;

    @Column(name = "target_account_id", nullable = false)
    private Long targetAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @Transient
    private TransactionState state;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = TransactionStatus.PENDING;
    }

    public TransactionState getState() {
        if (state == null) {
            switch (status) {
                case PENDING -> state = new PendingState();
                case VALIDATED -> state = new ValidatedState();
                case EXECUTED -> state = new ExecutedState();
                case REVERTED -> state = new RevertedState();
                case REJECTED -> state = new RejectedState();
            }
        }
        return state;
    }

    public void advanceTo(TransactionState newState) {
        this.state = newState;
        this.status = newState.status();
    }

    public void markAsExecuted() {
        registerEvent(new TransactionExecutedEvent(
                this.id,
                this.type.name(),
                this.sourceAccountId,
                this.targetAccountId,
                this.amount,
                this.fee
        ));
    }
}
