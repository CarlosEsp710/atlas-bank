package org.atlas.bank.atlas_bank.domain.validation;

import org.atlas.bank.atlas_bank.domain.model.account.AccountStatus;
import org.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

public class AccountStatusValidator implements TransferValidator {
    @Override
    public void validate(TransferContext context) {
        if (context.fromAccount().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.fromAccount().getId(), context.fromAccount().getStatus().name());
        }
        if (context.toAccount().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(context.toAccount().getId(), context.toAccount().getStatus().name());
        }
    }
}
