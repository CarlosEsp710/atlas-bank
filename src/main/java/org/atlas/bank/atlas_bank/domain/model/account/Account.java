package org.atlas.bank.atlas_bank.domain.model.account;

import lombok.*;
import org.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import org.atlas.bank.atlas_bank.domain.model.shared.Currency;
import org.atlas.bank.atlas_bank.domain.model.shared.Email;
import org.atlas.bank.atlas_bank.domain.model.shared.Money;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @EqualsAndHashCode.Include
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Email email;
    private AccountType type;
    private Money balance;
    private AccountStatus status;
    private Long customerId;
    private LocalDateTime createdAt;

    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        if (this.balance.isLessThan(amount)) {
            throw new InsufficientFundsException(this.id, this.balance.getAmount(), amount.getAmount());
        }

        this.balance = this.balance.subtract(amount);
    }

    public void initDefaults() {
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.MXN);
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
