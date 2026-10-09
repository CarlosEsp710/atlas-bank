package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.account.model.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);
}
