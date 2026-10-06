package org.atlas.bank.atlas_bank.transaction.service.fee;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class PremiumFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return accountType.equalsIgnoreCase("premium");
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
