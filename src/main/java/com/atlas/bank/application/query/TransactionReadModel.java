package com.atlas.bank.application.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReadModel(
        Long id,
        String type,
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        BigDecimal fee,
        String status,
        LocalDateTime createdAt
) {

}
