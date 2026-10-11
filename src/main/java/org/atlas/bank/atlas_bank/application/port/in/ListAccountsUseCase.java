package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.domain.model.account.Account;

import java.util.List;

public interface ListAccountsUseCase {
    List<Account> listAccounts();
}
