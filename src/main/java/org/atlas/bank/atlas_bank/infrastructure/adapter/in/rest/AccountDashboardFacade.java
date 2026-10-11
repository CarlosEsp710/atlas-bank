package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final GetAccountUseCase getAccountUseCase;
    private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;
    private final TransactionMapper transactionMapper;

    public DashboardResponse getAccountDashboard(Long accountId) {
        Account account = getAccountUseCase.getAccountById(accountId);

        List<TransactionResponse> recentTransactions = getTransactionsByAccountUseCase
                .getTransactionsByAccount(accountId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        return DashboardResponse.builder()
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
