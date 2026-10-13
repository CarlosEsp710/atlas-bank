package org.atlas.bank.atlas_bank.application.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferMoneyCommand(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount
) {
}
