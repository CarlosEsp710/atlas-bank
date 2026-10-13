package org.atlas.bank.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyDomainTest {

    @Test
    @DisplayName("Should increase the amount of money correctly")
    void shouldIncreaseAmountCorrectly() {
        Money a = Money.of(new BigDecimal(100), Currency.MXN);
        Money b = Money.of(new BigDecimal(50), Currency.MXN);

        Money result = a.add(b);

        assertEquals(Money.of(new BigDecimal(150), Currency.MXN), result);
    }

    @Test
    @DisplayName("Should reject addition of different currencies")
    void shouldRejectAdditionOfDifferentCurrencies() {
        Money a = Money.of(new BigDecimal(100), Currency.MXN);
        Money b = Money.of(new BigDecimal(50), Currency.USD);

        assertThrows(IllegalArgumentException.class, () -> a.add(b));
    }

    @Test
    @DisplayName("Should detect negative amounts correctly")
    void shouldDetectNegativeAmountsCorrectly() {
        Money a = Money.of(new BigDecimal(-100), Currency.MXN);

        assertTrue(a.isNegative());
    }

    @Test
    @DisplayName("Should compare amounts correctly")
    void shouldCompareAmountsCorrectly() {
        Money a = Money.of(new BigDecimal(100), Currency.MXN);
        Money b = Money.of(new BigDecimal(50), Currency.MXN);

        assertTrue(a.isGreaterThan(b));
    }

    @Test
    @DisplayName("Money.ZERO should return a Money instance with zero amount and specified currency")
    void shouldReturnZeroMoneyInstance() {
        Money zeroMoney = Money.zero(Currency.MXN);

        assertEquals(Money.of(BigDecimal.ZERO, Currency.MXN), zeroMoney);
    }
}