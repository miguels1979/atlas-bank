package com.atlas.bank.infrastructure.config;

import com.atlas.bank.application.port.out.FraudCheckPort;
import com.atlas.bank.domain.service.TransferDomainService;
import com.atlas.bank.domain.strategy.fee.CheckingFeeCalculator;
import com.atlas.bank.domain.strategy.fee.DefaultFeeCalculator;
import com.atlas.bank.domain.strategy.fee.PremiumFeeCalculator;
import com.atlas.bank.domain.strategy.fee.SavingFeeCalculator;
import com.atlas.bank.domain.validation.AccountStatusValidator;
import com.atlas.bank.domain.validation.FraudValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DomainBeanConfig {

    @Bean
    public TransferDomainService transferDomainService() {
        return new TransferDomainService();
    }

    @Bean @Order(1)
    public SavingFeeCalculator savingsFeeCalculator() {
        return new SavingFeeCalculator();
    }

    @Bean @Order(2)
    public CheckingFeeCalculator checkingFeeCalculator() {
        return new CheckingFeeCalculator();
    }

    @Bean @Order(3)
    public PremiumFeeCalculator premiumFeeCalculator() {
        return new PremiumFeeCalculator();
    }

    @Bean
    public DefaultFeeCalculator defaultFeeCalculator() {
        return new DefaultFeeCalculator();
    }

    @Bean @Order(1)
    public AccountStatusValidator accountStatusValidator() {
        return new AccountStatusValidator();
    }

    @Bean @Order(2)
    public FraudValidator fraudValidator(FraudCheckPort fraudCheckPort) {
        return new FraudValidator(fraudCheckPort);
    }
}