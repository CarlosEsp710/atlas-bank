package org.atlas.bank.atlas_bank.transaction.service.event;

import java.math.BigDecimal;

public record TransactionExecutedEvent(
        Long transactionId,
        String type,
        Long sourceAccountId,
        Long destinationAccountId,
        BigDecimal amount,
        BigDecimal fee
) {
}
