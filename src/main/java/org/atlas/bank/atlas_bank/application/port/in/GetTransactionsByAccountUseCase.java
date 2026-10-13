package org.atlas.bank.atlas_bank.application.port.in;

import org.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import org.atlas.bank.atlas_bank.application.query.TransactionReadModel;

import java.util.List;

public interface GetTransactionsByAccountUseCase {
    List<TransactionReadModel> getTransactionsByAccount(GetAccountStatementQuery query);
}
