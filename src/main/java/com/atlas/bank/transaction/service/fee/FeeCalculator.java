package com.atlas.bank.transaction.service.fee;

import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(AccountType accountType);
    BigDecimal calculate(BigDecimal amount);
}
