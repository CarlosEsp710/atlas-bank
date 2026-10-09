package org.atlas.bank.atlas_bank.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {
    private final SpringDataAccountRepository accountRepository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Account save(Account account) {
        account.initDefaults();
        AccountJpaEntity jpaEntity = mapper.toJpaEntity(account);
        AccountJpaEntity savedEntity = accountRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }
}
