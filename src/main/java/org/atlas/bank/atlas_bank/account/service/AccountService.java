package org.atlas.bank.atlas_bank.account.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
