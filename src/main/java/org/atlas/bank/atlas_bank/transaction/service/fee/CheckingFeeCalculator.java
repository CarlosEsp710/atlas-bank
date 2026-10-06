package org.atlas.bank.atlas_bank.transaction.service.fee;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class CheckingFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return accountType.equalsIgnoreCase("checking");
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.015"));
    }
}
