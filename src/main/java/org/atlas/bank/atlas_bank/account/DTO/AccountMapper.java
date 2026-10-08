package org.atlas.bank.atlas_bank.account.DTO;

import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.shared.model.Currency;
import org.atlas.bank.atlas_bank.shared.model.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "balance", source = "balance", qualifiedByName = "toMoney")
    Account toEntity(CreateAccountRequest request);

    @Mapping(target = "balance", source = "balance", qualifiedByName = "toAmount")
    AccountResponse toResponse(Account account);

    @Named("toMoney")
    default Money toMoney(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        return Money.of(amount, Currency.MXN);
    }

    @Named("toAmount")
    default BigDecimal toAmount(Money money) {
        if (money == null) {
            return null;
        }
        return money.getAmount();
    }
}
