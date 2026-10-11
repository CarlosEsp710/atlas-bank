package org.atlas.bank.atlas_bank.domain.validation;

import org.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

public class SufficientFundValidator implements TransferValidator {
    @Override
    public void validate(TransferContext context) {
        if (context.fromAccount().getBalance().getAmount().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.fromAccount().getId(), context.fromAccount().getBalance().getAmount(), context.amount());
        }
    }
}
