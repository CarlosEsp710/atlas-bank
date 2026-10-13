package org.atlas.bank.atlas_bank.application.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import org.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, ListAccountsUseCase, GetAccountUseCase {
    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AuditableAccountService(
            @Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
            @Qualifier("accountService") ListAccountsUseCase listAccountsUseCase,
            @Qualifier("accountService") GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.listAccountsUseCase = listAccountsUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    @PostConstruct
    public void init() {
        log.info("AuditableAccountService initialized with CreateAccountUseCase: {}, ListAccountsUseCase: {}, GetAccountUseCase: {}",
                createAccountUseCase.getClass().getSimpleName(),
                listAccountsUseCase.getClass().getSimpleName(),
                getAccountUseCase.getClass().getSimpleName());
    }

    @Override
    @Transactional
    public Account createAccount(CreateAccountCommand command) {
        log.info("Creating account - Account Number: {}, Owner: {}", command.accountNumber(), command.ownerName());
        Account createdAccount = createAccountUseCase.createAccount(command);
        log.info("Account created successfully - Account ID: {}", createdAccount.getId());

        return createdAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> listAccounts() {
        log.info("Retrieving all accounts");
        List<Account> accounts = listAccountsUseCase.listAccounts();
        log.info("Retrieved {} accounts", accounts.size());

        return accounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        log.info("Retrieving account by ID: {}", id);
        Account account = getAccountUseCase.getAccountById(id);
        if (account != null) {
            log.info("Account retrieved successfully - Account ID: {}", account.getId());
        } else {
            log.warn("Account not found - Account ID: {}", id);
        }
        return account;
    }
}
