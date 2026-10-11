package org.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {
    private final SpringDataTransactionRepository transactionRepository;
    private final TransactionPersistenceMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Transaction save(Transaction transaction) {
        transaction.initDefaults();
        TransactionJpaEntity transactionJpaEntity = mapper.toJpaEntity(transaction);
        TransactionJpaEntity savedEntity = transactionRepository.save(transactionJpaEntity);

        Transaction savedTransaction = mapper.toDomain(savedEntity);

        transaction.getDomainEvents().forEach(eventPublisher::publishEvent);
        transaction.clearDomainEvents();

        return savedTransaction;
    }

    @Override
    public List<Transaction> findAllBySourceAccountIdOrTargetAccountId(Long sourceAccountId, Long targetAccountId) {
        return transactionRepository.findAllBySourceAccountIdOrTargetAccountId(sourceAccountId, targetAccountId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
