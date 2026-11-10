package com.atlas.bank.transaction.service.fraud;

import com.atlas.bank.domain.model.shared.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudChecker {
    FraudCheckResult check(Long accountId, BigDecimal amount);
}
