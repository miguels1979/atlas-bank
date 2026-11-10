package com.atlas.bank.application.service;

import com.atlas.bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface ITransferService {

    Transaction execute (Long fromId, Long toId, BigDecimal amount);
}
