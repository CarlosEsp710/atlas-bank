package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface GetTransactionsByAccountUseCase {
    List<Transaction> getTransactionsByAccount(Long accountId);
}
