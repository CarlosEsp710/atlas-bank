package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account createdAccount = createAccountUseCase.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(createdAccount));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = listAccountsUseCase.listAccounts().stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toResponse(getAccountUseCase.getAccountById(id)));
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getAccountDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(accountDashboardFacade.getAccountDashboard(id));
    }
}
