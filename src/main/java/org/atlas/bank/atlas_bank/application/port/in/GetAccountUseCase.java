package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.domain.model.account.Account;

public interface GetAccountUseCase {
    Account getAccountById(Long accountId);
}
