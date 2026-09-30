package org.atlas.bank.atlas_bank.account.service;

import org.atlas.bank.atlas_bank.account.model.Account;

import java.util.List;

public interface IAccountService {
    Account createAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
}
