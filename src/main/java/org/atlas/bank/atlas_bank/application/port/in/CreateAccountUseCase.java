package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import org.atlas.bank.atlas_bank.domain.model.account.Account;

public interface CreateAccountUseCase {
    Account createAccount(CreateAccountCommand command);
}
