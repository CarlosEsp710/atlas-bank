package org.atlas.bank.atlas_bank.domain.strategy.fee;

import org.atlas.bank.atlas_bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(AccountType accountType);
    BigDecimal calculateFee(BigDecimal amount);
}
