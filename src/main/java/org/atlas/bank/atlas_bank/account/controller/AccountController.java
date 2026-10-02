package org.atlas.bank.atlas_bank.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.account.DTO.AccountMapper;
import org.atlas.bank.atlas_bank.account.DTO.AccountResponse;
import org.atlas.bank.atlas_bank.account.DTO.CreateAccountRequest;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.account.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(createdAccount));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = accountService.getAllAccounts().stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toResponse(accountService.getAccountById(id)));
    }
}
