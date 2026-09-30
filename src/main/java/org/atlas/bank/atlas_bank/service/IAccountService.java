package org.atlas.bank.atlas_bank.service;

import org.atlas.bank.atlas_bank.model.Account;

import java.util.List;

public interface IAccountService {
    Account createAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
}
