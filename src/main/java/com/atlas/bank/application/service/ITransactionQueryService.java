package com.atlas.bank.transaction.service;

import com.atlas.bank.domain.model.transaction.Transaction;

import java.util.List;

public interface ITransactionQueryService {

    List<Transaction> getByAccountId (Long accountId);
}
