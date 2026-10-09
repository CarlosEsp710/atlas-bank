package org.atlas.bank.atlas_bank.application.port.out;

import org.atlas.bank.atlas_bank.transaction.service.fraud.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudCheckPort {
    FraudCheckResult checkTransaction(Long accountId, BigDecimal amount);
}
