package org.atlas.bank.atlas_bank.transaction.model.state;

import org.atlas.bank.atlas_bank.transaction.model.TransactionStatus;

public sealed interface TransactionState permits
        PendingState,
        ValidatedState,
        ExecutedState,
        RejectedState,
        RevertedState {
    TransactionStatus status();

    default TransactionState execute() {
        throw new UnsupportedOperationException("Cannot execute transaction in state: " + status());
    }

    default TransactionState validate() {
        throw new UnsupportedOperationException("Cannot validate transaction in state: " + status());
    }

    default TransactionState reject() {
        throw new UnsupportedOperationException("Cannot reject transaction in state: " + status());
    }

    default TransactionState revert() {
        throw new UnsupportedOperationException("Cannot revert transaction in state: " + status());
    }
}
