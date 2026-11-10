package com.atlas.bank.application.port.out;

import com.atlas.bank.domain.model.transaction.Transaction;

import java.util.List;

public interface TransactionRepositoryPort {

    Transaction save (Transaction transaction);
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}
