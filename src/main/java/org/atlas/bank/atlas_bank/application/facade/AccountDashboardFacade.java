package org.atlas.bank.atlas_bank.application.facade;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import org.atlas.bank.atlas_bank.application.query.DashboardReadModel;
import org.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import org.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final GetAccountUseCase getAccountUseCase;
    private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;

    public DashboardReadModel getAccountDashboard(Long accountId) {
        Account account = getAccountUseCase.getAccountById(accountId);

        List<TransactionReadModel> recentTransactions = getTransactionsByAccountUseCase
                .getTransactionsByAccount(new GetAccountStatementQuery(accountId));

        return DashboardReadModel.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(recentTransactions)
                .build();
    }
}
