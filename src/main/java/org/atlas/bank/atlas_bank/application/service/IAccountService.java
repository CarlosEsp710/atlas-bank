package org.atlas.bank.atlas_bank.application.service;

import org.atlas.bank.atlas_bank.domain.model.account.Account;

import java.util.List;

public interface IAccountService {
    Account createAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
}
