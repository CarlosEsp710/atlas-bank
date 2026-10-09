package org.atlas.bank.atlas_bank.account.repository;

import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends
        JpaRepository<Account, Long>,
        DomainAccountRepository,
        AccountRepositoryPort {
}
