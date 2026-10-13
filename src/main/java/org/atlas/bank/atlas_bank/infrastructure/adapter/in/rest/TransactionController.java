package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import org.atlas.bank.atlas_bank.application.port.in.GetTransactionsByAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import org.atlas.bank.atlas_bank.application.query.GetAccountStatementQuery;
import org.atlas.bank.atlas_bank.application.query.TransactionReadModel;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        TransferMoneyCommand command = TransferMoneyCommand.builder()
                .fromAccountId(transferRequest.getSourceAccountId())
                .toAccountId(transferRequest.getTargetAccountId())
                .amount(transferRequest.getAmount())
                .build();

        return ResponseEntity.ok(transactionMapper.toResponse(transferMoneyUseCase.transfer(command)));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionReadModel>> getTransactions(@PathVariable Long id) {
        GetAccountStatementQuery query = new GetAccountStatementQuery(id);

        List<TransactionReadModel> transactions = getTransactionsByAccountUseCase.getTransactionsByAccount(query);

        return ResponseEntity.ok(transactions);
    }
}
