package org.atlas.bank.atlas_bank.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
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
