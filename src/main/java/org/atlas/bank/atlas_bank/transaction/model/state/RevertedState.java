package org.atlas.bank.atlas_bank.transaction.model.state;

import org.atlas.bank.atlas_bank.transaction.model.TransactionStatus;

public record RevertedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REVERTED;
    }
}
