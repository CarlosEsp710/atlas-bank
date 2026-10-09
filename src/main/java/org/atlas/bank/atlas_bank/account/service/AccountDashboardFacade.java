package org.atlas.bank.atlas_bank.account.service;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import org.atlas.bank.atlas_bank.application.service.IAccountService;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.transaction.DTO.TransactionMapper;
import org.atlas.bank.atlas_bank.transaction.DTO.TransactionResponse;
import org.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {
    private final IAccountService accountService;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    public DashboardResponse getAccountDashboard(Long accountId) {
        Account account = accountService.getAccountById(accountId);

        List<TransactionResponse> recentTransactions = transactionQueryService
                .getByAccountId(accountId)
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
