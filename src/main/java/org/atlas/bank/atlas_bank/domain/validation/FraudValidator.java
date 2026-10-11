package org.atlas.bank.atlas_bank.domain.validation;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.out.FraudCheckPort;
import org.atlas.bank.atlas_bank.domain.exception.FraudCheckException;
import org.atlas.bank.atlas_bank.domain.model.shared.FraudCheckResult;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

@RequiredArgsConstructor
public class FraudValidator implements TransferValidator {
    private final FraudCheckPort fraudCheckerPort;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult fraudCheckResult = fraudCheckerPort.checkTransaction(context.fromAccount().getId(), context.amount());

        if (fraudCheckResult.isBlocked()) {
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
