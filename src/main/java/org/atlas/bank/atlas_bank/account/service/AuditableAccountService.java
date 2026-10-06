package org.atlas.bank.atlas_bank.account.service;

import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements IAccountService {
    private final IAccountService delegate;

    public AuditableAccountService(@Qualifier("accountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        log.info("Creating account - Account Number: {}, Owner: {}", account.getAccountNumber(), account.getOwnerName());
        Account createdAccount = delegate.createAccount(account);
        log.info("Account created successfully - Account ID: {}", createdAccount.getId());

        return createdAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        log.info("Retrieving all accounts");
        List<Account> accounts = delegate.getAllAccounts();
        log.info("Retrieved {} accounts", accounts.size());

        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        log.info("Retrieving account by ID: {}", id);
        Account account = delegate.getAccountById(id);
        if (account != null) {
            log.info("Account retrieved successfully - Account ID: {}", account.getId());
        } else {
            log.warn("Account not found - Account ID: {}", id);
        }
        return account;
    }
}
