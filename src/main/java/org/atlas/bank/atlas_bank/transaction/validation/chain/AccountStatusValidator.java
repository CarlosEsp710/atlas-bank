package org.atlas.bank.atlas_bank.transaction.validation.chain;

import org.atlas.bank.atlas_bank.account.model.AccountStatus;
import org.atlas.bank.atlas_bank.transaction.exception.AccountNotActiveException;
import org.atlas.bank.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
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
