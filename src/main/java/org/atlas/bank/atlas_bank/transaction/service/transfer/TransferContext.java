package org.atlas.bank.atlas_bank.transaction.service.transfer;

import org.atlas.bank.atlas_bank.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account fromAccount, Account toAccount, BigDecimal amount) {

}
