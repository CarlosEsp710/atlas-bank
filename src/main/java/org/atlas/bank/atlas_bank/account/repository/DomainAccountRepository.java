package org.atlas.bank.atlas_bank.account.repository;

import org.atlas.bank.atlas_bank.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface DomainAccountRepository {
    Optional<Account> findById(Long accountId);

    List<Account> findAll();

    Account save(Account account);
}
