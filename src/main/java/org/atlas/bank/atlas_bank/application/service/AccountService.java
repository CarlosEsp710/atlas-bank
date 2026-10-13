package org.atlas.bank.atlas_bank.application.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import org.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import org.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.domain.event.AccountClosedEvent;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.domain.model.shared.Currency;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;
import org.atlas.bank.atlas_bank.domain.model.shared.Money;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase, CloseAccountUseCase, ListAccountsUseCase, GetAccountUseCase {
    private final AccountRepositoryPort accountRepository;
    private final ApplicationEventPublisher eventPublisher;

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
    @Transactional
    @CacheEvict(value = "accounts", key = "#command.accountId()")
    public Account close(CloseAccountCommand command) {
        Account account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new AccountNotFoundException(command.accountId()));
        account.close();
        Account closedAccount = accountRepository.save(account);
        eventPublisher.publishEvent(new AccountClosedEvent(closedAccount.getId(), closedAccount.getAccountNumber()));
        return closedAccount;
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
