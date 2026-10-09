package org.atlas.bank.atlas_bank.application.port.out;

import org.atlas.bank.atlas_bank.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
    Optional<Account> findById(Long accountId);

    List<Account> findAll();

    Account save(Account account);
}
