package org.atlas.bank.atlas_bank.domain.strategy.fee;

import org.atlas.bank.atlas_bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public class PremiumFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(AccountType accountType) {
        return accountType == AccountType.PREMIUM;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
