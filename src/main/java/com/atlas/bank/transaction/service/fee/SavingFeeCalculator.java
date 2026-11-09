package com.atlas.bank.transaction.service.fee;

import com.atlas.bank.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Order(1)
public class SavingFeeCalculator implements FeeCalculator{

    @Override
    public boolean supports(AccountType accountType) {
        return accountType == AccountType.SAVINGS;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01"));
    }
}
