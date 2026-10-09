package org.atlas.bank.atlas_bank.application.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepositoryPort accountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
