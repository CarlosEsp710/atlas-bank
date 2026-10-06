package org.atlas.bank.atlas_bank.transaction.service.transfer;

import org.atlas.bank.atlas_bank.transaction.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
