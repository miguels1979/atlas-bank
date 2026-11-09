package com.atlas.bank.application.port.in;

import com.atlas.bank.transaction.model.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction execute (Long fromId, Long toId, BigDecimal amount);
}
