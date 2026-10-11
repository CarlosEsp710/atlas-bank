package org.atlas.bank.atlas_bank.domain.model.transaction.state;

import org.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

public record RejectedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REJECTED;
    }
}
