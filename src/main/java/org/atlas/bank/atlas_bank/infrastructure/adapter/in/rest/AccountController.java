package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.account.service.AccountDashboardFacade;
import org.atlas.bank.atlas_bank.application.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final IAccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;

    @PostConstruct
    public void init() {
        log.info("Real class used for IAccountService: {}", accountService.getClass().getName());
    }

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

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getAccountDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(accountDashboardFacade.getAccountDashboard(id));
    }
}
