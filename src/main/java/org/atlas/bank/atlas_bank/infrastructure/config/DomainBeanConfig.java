package org.atlas.bank.atlas_bank.infrastructure.config;

import org.atlas.bank.atlas_bank.application.port.out.FraudCheckPort;
import org.atlas.bank.atlas_bank.domain.service.TransferDomainService;
import org.atlas.bank.atlas_bank.domain.strategy.fee.CheckingFeeCalculator;
import org.atlas.bank.atlas_bank.domain.strategy.fee.DefaultFeeCalculator;
import org.atlas.bank.atlas_bank.domain.strategy.fee.PremiumFeeCalculator;
import org.atlas.bank.atlas_bank.domain.strategy.fee.SavingsFeeCalculator;
import org.atlas.bank.atlas_bank.domain.validation.AccountStatusValidator;
import org.atlas.bank.atlas_bank.domain.validation.FraudValidator;
import org.atlas.bank.atlas_bank.domain.validation.SufficientFundValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DomainBeanConfig {

    @Bean
    public TransferDomainService transferDomainService() {
        return new TransferDomainService();
    }

    @Bean
    @Order(1)
    public SavingsFeeCalculator savingsFeeCalculator() {
        return new SavingsFeeCalculator();
    }

    @Bean
    @Order(2)
    public CheckingFeeCalculator checkingFeeCalculator() {
        return new CheckingFeeCalculator();
    }

    @Bean
    @Order(3)
    public PremiumFeeCalculator premiumFeeCalculator() {
        return new PremiumFeeCalculator();
    }

    @Bean
    public DefaultFeeCalculator defaultFeeCalculator() {
        return new DefaultFeeCalculator();
    }

    @Bean
    @Order(1)
    public AccountStatusValidator accountStatusValidator() {
        return new AccountStatusValidator();
    }

    @Bean
    @Order(2)
    public SufficientFundValidator sufficientFundsValidator() {
        return new SufficientFundValidator();
    }

    @Bean
    @Order(3)
    public FraudValidator fraudValidator(FraudCheckPort fraudCheckPort) {
        return new FraudValidator(fraudCheckPort);
    }
}