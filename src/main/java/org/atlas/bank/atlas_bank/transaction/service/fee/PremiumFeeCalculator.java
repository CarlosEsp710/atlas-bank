package org.atlas.bank.atlas_bank.transaction.service.fee;

import org.atlas.bank.atlas_bank.account.model.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
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
