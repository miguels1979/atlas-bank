package com.atlas.bank.application.port.in;

import com.atlas.bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction transfer (Long fromId, Long toId, BigDecimal amount);
}
