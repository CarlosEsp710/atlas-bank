package org.atlas.bank.atlas_bank.transaction.validation.chain;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.transaction.service.exception.FraudCheckException;
import org.atlas.bank.atlas_bank.transaction.service.fraud.FraudCheckResult;
import org.atlas.bank.atlas_bank.transaction.service.fraud.FraudChecker;
import org.atlas.bank.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidator {
    private final FraudChecker fraudChecker;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult fraudCheckResult = fraudChecker.checkTransaction(context.fromAccount().getId(), context.amount());

        if (fraudCheckResult.isBlocked()) {
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
