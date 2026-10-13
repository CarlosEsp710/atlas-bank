package org.atlas.bank.atlas_bank.application.command;

import lombok.Builder;
import org.atlas.bank.atlas_bank.domain.model.account.AccountType;

import java.math.BigDecimal;

@Builder
public record CreateAccountCommand(
        String accountNumber,
        String ownerName,
        String email,
        AccountType type,
        BigDecimal balance
) {
}
