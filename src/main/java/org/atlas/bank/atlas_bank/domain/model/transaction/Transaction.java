package org.atlas.bank.atlas_bank.domain.model.transaction;

import lombok.*;
import org.atlas.bank.atlas_bank.domain.model.transaction.state.*;
import org.atlas.bank.atlas_bank.domain.event.TransactionExecutedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {
    @EqualsAndHashCode.Include
    private Long id;
    private TransactionType type;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private BigDecimal fee;
    private TransactionStatus status;
    private TransactionState state;
    private LocalDateTime createdAt;

    public void initDefaults() {
        if (status == null) status = TransactionStatus.EXECUTED;
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @Builder.Default
    private final List<Object> domainEvents = new ArrayList<>();

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
        domainEvents.add(new TransactionExecutedEvent(
                this.id,
                this.type.name(),
                this.sourceAccountId,
                this.targetAccountId,
                this.amount,
                this.fee
        ));
    }

    public void executeTransfer() {
        advanceTo(getState().validate());
        advanceTo(getState().execute());
        markAsExecuted();
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
