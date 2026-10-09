package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.transaction.model.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
