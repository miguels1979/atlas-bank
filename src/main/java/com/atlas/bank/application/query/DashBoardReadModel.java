package com.atlas.bank.application.query;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record DashBoardReadModel(
        Long accountId,
        String accountNumber,
        String ownerName,
        String type,
        BigDecimal balance,
        String status,
        List<TransactionReadModel>recentTransactions
) {
}
