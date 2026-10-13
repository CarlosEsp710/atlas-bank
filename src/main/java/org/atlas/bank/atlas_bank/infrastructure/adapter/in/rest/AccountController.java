package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import org.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import org.atlas.bank.atlas_bank.application.facade.AccountDashboardFacade;
import org.atlas.bank.atlas_bank.application.port.in.CloseAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import org.atlas.bank.atlas_bank.application.query.DashboardReadModel;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final CloseAccountUseCase closeAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        CreateAccountCommand command = CreateAccountCommand.builder()
                .accountNumber(request.getAccountNumber())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .type(request.getType())
                .balance(request.getBalance())
                .build();
        Account createdAccount = createAccountUseCase.createAccount(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(createdAccount));
    }

    @PatchMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponse> close(@PathVariable Long id) {
        Account closedAccount = closeAccountUseCase.close(new CloseAccountCommand(id));
        return ResponseEntity.ok(accountMapper.toResponse(closedAccount));
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
    public ResponseEntity<DashboardReadModel> getAccountDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(accountDashboardFacade.getAccountDashboard(id));
    }
}
