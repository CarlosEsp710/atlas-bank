package org.atlas.bank.atlas_bank.application.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import org.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.domain.model.shared.Currency;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;
import org.atlas.bank.atlas_bank.domain.model.shared.Money;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase, ListAccountsUseCase, GetAccountUseCase {
    private final AccountRepositoryPort accountRepository;

    @Override
    @Transactional
    public Account createAccount(CreateAccountCommand command) {
        Account account = Account.builder()
                .accountNumber(command.accountNumber())
                .ownerName(command.ownerName())
                .email(Email.of(command.email()))
                .type(command.type())
                .balance(Money.of(command.balance(), Currency.MXN))
                .build();

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
