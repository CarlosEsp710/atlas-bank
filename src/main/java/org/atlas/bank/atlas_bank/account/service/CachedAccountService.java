package org.atlas.bank.atlas_bank.account.service;

import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @deprecated Caching is disabled; use the active account service instead.
 */
@Deprecated
@Slf4j
public class CachedAccountService implements IAccountService {
    private final IAccountService delegate;
    private final Map<Long, Account> accountCache = new ConcurrentHashMap<>();

    public CachedAccountService(@Qualifier("auditableAccountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Account createAccount(Account account) {
        Account createdAccount = delegate.createAccount(account);
        accountCache.put(createdAccount.getId(), createdAccount);
        log.info("Account cached - Account ID: {}", createdAccount.getId());
        return createdAccount;
    }

    @Override
    public List<Account> getAllAccounts() {
        return delegate.getAllAccounts();
    }

    @Override
    public Account getAccountById(Long id) {
        Account account = accountCache.get(id);

        if (account != null) {
            log.info("Account retrieved from cache - Account ID: {}", id);
        } else {
            log.info("Account not found in cache - Account ID: {}", id);
            account = delegate.getAccountById(id);
            if (account != null) {
                accountCache.put(id, account);
                log.info("Account cached after retrieval - Account ID: {}", id);
            }
        }

        return account;
    }
}
