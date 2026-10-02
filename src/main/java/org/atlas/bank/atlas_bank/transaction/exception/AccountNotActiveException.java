package org.atlas.bank.atlas_bank.transaction.exception;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(Long accountId, String status) {
        super("Account with ID " + accountId + " is not active. Current status: " + status);
    }
}
