package org.atlas.bank.atlas_bank.transaction.service.domain;

import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.domain.model.shared.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferDomainService {

    public void transfer(Account sourceAccount, Account targetAccount, BigDecimal amount, BigDecimal fee) {
        Money totalDebit = Money.of(amount.add(fee), sourceAccount.getBalance().getCurrency());
        Money depositAmount = Money.of(amount, targetAccount.getBalance().getCurrency());

        sourceAccount.withdraw(totalDebit);
        targetAccount.deposit(depositAmount);
    }
}
