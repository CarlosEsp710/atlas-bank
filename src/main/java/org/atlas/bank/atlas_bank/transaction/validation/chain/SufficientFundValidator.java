package org.atlas.bank.atlas_bank.transaction.validation.chain;

import org.atlas.bank.atlas_bank.transaction.exception.InsufficientFundsException;
import org.atlas.bank.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SufficientFundValidator implements TransferValidator {
    @Override
    public void validate(TransferContext context) {
        if (context.fromAccount().getBalance().getAmount().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.fromAccount().getId(), context.fromAccount().getBalance().getAmount(), context.amount());
        }
    }
}
