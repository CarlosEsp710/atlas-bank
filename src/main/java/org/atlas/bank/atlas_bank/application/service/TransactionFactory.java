package org.atlas.bank.atlas_bank.application.service;

import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransactionStatus;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransactionType;
import org.atlas.bank.atlas_bank.domain.model.transaction.state.PendingState;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

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
