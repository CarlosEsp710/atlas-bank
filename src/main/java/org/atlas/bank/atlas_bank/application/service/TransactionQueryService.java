package org.atlas.bank.atlas_bank.application.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import org.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import org.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements GetTransactionsByAccountUseCase {
    private final TransactionRepositoryPort transactionRepositoryPort;

    @Override
    public List<TransactionReadModel> getTransactionsByAccount(GetAccountStatementQuery query) {
        return transactionRepositoryPort
                .findAllBySourceAccountIdOrTargetAccountId(query.accountId(), query.accountId())
                .stream()
                .map(this::toReadModel)
                .toList();
    }

    private TransactionReadModel toReadModel(Transaction transaction) {
        return new TransactionReadModel(
                transaction.getId(),
                transaction.getType().name(),
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount(),
                transaction.getFee(),
                transaction.getStatus().name(),
                transaction.getCreatedAt()
        );
    }
}
