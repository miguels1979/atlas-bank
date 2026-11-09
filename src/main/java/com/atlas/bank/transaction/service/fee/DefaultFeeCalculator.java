package com.atlas.bank.transaction.service.fee;

import com.atlas.bank.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Order // prioridad más baja: debe evaluarse al final porque supports() siempre devuelve true
public class DefaultFeeCalculator implements FeeCalculator {

    @Override
    public boolean supports(AccountType accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
