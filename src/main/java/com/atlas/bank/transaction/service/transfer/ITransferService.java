package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface ITransferService {

    Transaction execute (Long fromId, Long toId, BigDecimal amount);
}
