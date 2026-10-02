package org.atlas.bank.atlas_bank.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.transaction.DTO.TransactionMapper;
import org.atlas.bank.atlas_bank.transaction.DTO.TransactionResponse;
import org.atlas.bank.atlas_bank.transaction.DTO.TransferRequest;
import org.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import org.atlas.bank.atlas_bank.transaction.service.ITransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest transferRequest) {
        return ResponseEntity.ok(transactionMapper.toResponse(transferService.execute(
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
