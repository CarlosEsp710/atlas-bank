package org.atlas.bank.atlas_bank.application.port.out;

import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);

    List<Transaction> findAllBySourceAccountIdOrTargetAccountId(Long sourceAccountId, Long targetAccountId);
}
