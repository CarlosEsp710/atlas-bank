package org.atlas.bank.atlas_bank.domain.model.account;

import org.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import org.atlas.bank.atlas_bank.domain.model.shared.Currency;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;
import org.atlas.bank.atlas_bank.domain.model.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountDomainTest {
    private Account createAccountWithBalance(BigDecimal balance) {
        return Account.builder()
                .id(1L)
                .ownerName("John Doe")
                .email(Email.of("john.doe@example.com"))
                .balance(Money.of(balance, Currency.MXN))
                .type(AccountType.SAVINGS)
                .status(AccountStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Deposit should increase the account balance")
    void shouldIncreaseBalanceOnDeposit() {
        Account account = createAccountWithBalance(BigDecimal.valueOf(1000));
        Money depositAmount = Money.of(BigDecimal.valueOf(500), Currency.MXN);

        account.deposit(depositAmount);

        assertEquals(Money.of(BigDecimal.valueOf(1500), Currency.MXN), account.getBalance());
    }

    @Test
    @DisplayName("Withdraw should decrease the account balance when sufficient funds are available")
    void shouldDecreaseBalanceOnWithdraw() {
        Account account = createAccountWithBalance(BigDecimal.valueOf(1000));
        Money withdrawAmount = Money.of(BigDecimal.valueOf(400), Currency.MXN);

        account.withdraw(withdrawAmount);

        assertEquals(Money.of(BigDecimal.valueOf(600), Currency.MXN), account.getBalance());
    }

    @Test
    @DisplayName("Withdraw should throw InsufficientFundsException when funds are insufficient")
    void shouldThrowExceptionOnInsufficientFunds() {
        Account account = createAccountWithBalance(BigDecimal.valueOf(1000));
        Money withdrawAmount = Money.of(BigDecimal.valueOf(1500), Currency.MXN);

        assertThrows(InsufficientFundsException.class, () -> account.withdraw(withdrawAmount));
    }

    @Test
    @DisplayName("Withdraw should throw IllegalArgumentException when amount is negative")
    void shouldThrowExceptionOnNegativeWithdraw() {
        Account account = createAccountWithBalance(BigDecimal.valueOf(1000));
        Money withdrawAmount = Money.of(BigDecimal.valueOf(-100), Currency.MXN);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(withdrawAmount));
    }

    @Test
    @DisplayName("Should initialize with default values when using the builder")
    void shouldInitializeWithDefaultValues() {
        Account account = Account.builder()
                .id(1L)
                .ownerName("John Doe")
                .email(Email.of("john.doe@example.com"))
                .type(AccountType.SAVINGS)
                .build();

        account.initDefaults();

        assertEquals(AccountStatus.ACTIVE, account.getStatus());
        assertEquals(Money.of(BigDecimal.ZERO, Currency.MXN), account.getBalance());
        assertNotNull(account.getCreatedAt());
    }
}