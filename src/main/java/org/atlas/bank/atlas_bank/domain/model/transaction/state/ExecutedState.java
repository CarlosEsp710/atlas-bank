package org.atlas.bank.atlas_bank.domain.model.transaction.state;

import org.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;

public record ExecutedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.EXECUTED;
    }

    @Override
    public TransactionState revert() {
        return new RevertedState();
    }
}
