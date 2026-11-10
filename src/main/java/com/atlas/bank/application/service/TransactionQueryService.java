package com.atlas.bank.transaction.service;


import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getByAccountId(Long accountId){
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }

}
