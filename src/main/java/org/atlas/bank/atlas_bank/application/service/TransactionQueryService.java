package org.atlas.bank.atlas_bank.application.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService {
    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public List<Transaction> getByAccountId(Long accountId) {
        return transactionRepositoryPort
                .findAllBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
