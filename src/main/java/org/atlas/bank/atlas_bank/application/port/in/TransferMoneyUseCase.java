package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction transfer(TransferMoneyCommand command);
}
