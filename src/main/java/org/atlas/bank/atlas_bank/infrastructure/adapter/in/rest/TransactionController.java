package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransactionResponse;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import org.atlas.bank.atlas_bank.application.service.ITransactionQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        return ResponseEntity.ok(transactionMapper.toResponse(transferMoneyUseCase.execute(
                        transferRequest.getSourceAccountId(),
                        transferRequest.getTargetAccountId(),
                        transferRequest.getAmount()
                )
        ));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(transactionQueryService.getByAccountId(id).stream().map(transactionMapper::toResponse).toList());
    }
}
