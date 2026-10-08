package org.atlas.bank.atlas_bank.transaction.model.state;

import org.atlas.bank.atlas_bank.transaction.model.TransactionStatus;

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
