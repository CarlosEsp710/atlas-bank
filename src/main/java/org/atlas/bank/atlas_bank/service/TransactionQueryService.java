package org.atlas.bank.atlas_bank.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.model.Transaction;
import org.atlas.bank.atlas_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getByAccountId(Long accountId) {
        return transactionRepository
                .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
