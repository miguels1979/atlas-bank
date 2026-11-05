package com.atlas.bank.transaction.service;


import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class TransactionProcessor<T> {

    protected final TransactionRepository transactionRepository;

    @Transactional
    public Transaction process(T context){
        validate(context);
        BigDecimal fee = calculateFee(context);
        updateBalances(context,fee);
        return save(context,fee);

    }

    protected abstract void validate(T context);
    protected abstract BigDecimal calculateFee(T context);
    protected abstract void updateBalances(T context, BigDecimal fee);
    protected abstract Transaction save(T context, BigDecimal fee);

}
