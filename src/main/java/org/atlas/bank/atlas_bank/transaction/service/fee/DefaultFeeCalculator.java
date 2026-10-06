package org.atlas.bank.atlas_bank.transaction.service.fee;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return true;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
