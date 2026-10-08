package org.atlas.bank.atlas_bank.transaction.service.factory;

import org.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.atlas.bank.atlas_bank.transaction.model.TransactionStatus;
import org.atlas.bank.atlas_bank.transaction.model.TransactionType;
import org.atlas.bank.atlas_bank.transaction.model.state.PendingState;
import org.atlas.bank.atlas_bank.transaction.service.transfer.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {
    public static Transaction createTransaction(TransferContext context, BigDecimal fee) {
        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setSourceAccountId(context.fromAccount().getId());
        transaction.setTargetAccountId(context.toAccount().getId());
        transaction.setAmount(context.amount());
        transaction.setFee(fee);
        transaction.setStatus(TransactionStatus.PENDING);

        transaction.advanceTo(new PendingState());

        return transaction;
    }
}
