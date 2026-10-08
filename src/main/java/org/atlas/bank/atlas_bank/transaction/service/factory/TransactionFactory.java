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
        Transaction transaction = Transaction.builder()
                .type(TransactionType.TRANSFER)
                .sourceAccountId(context.fromAccount().getId())
                .targetAccountId(context.toAccount().getId())
                .amount(context.amount())
                .fee(fee)
                .status(TransactionStatus.PENDING)
                .build();

        transaction.advanceTo(new PendingState());

        return transaction;
    }
}
