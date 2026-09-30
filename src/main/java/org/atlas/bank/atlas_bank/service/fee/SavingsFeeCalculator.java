package org.atlas.bank.atlas_bank.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SavingsFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return accountType.equalsIgnoreCase("savings");
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01"));
    }
}
