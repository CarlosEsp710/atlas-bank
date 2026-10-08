package org.atlas.bank.atlas_bank.account.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.account.repository.DomainAccountRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{
    private final DomainAccountRepository domainAccountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        return domainAccountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return domainAccountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(Long id) {
        return domainAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
