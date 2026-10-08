package org.atlas.bank.atlas_bank.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.atlas.bank.atlas_bank.shared.model.Currency;
import org.atlas.bank.atlas_bank.shared.model.Email;
import org.atlas.bank.atlas_bank.shared.model.Money;
import org.atlas.bank.atlas_bank.transaction.exception.InsufficientFundsException;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency", nullable = false, length = 3))
    })
    private Money balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    @Column(name = "costumer_id", nullable = true)
    private Long costumerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.MXN);
    }

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
}
