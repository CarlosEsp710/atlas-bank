package org.atlas.bank.atlas_bank.service;

import org.atlas.bank.atlas_bank.model.Transaction;

import java.math.BigDecimal;

public interface ITransferService {
    Transaction execute(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
