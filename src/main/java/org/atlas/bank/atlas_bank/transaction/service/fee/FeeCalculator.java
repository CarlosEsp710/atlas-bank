package org.atlas.bank.atlas_bank.transaction.service.fee;

import org.atlas.bank.atlas_bank.account.model.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(AccountType accountType);
    BigDecimal calculateFee(BigDecimal amount);
}
