package com.atlas.bank.application.service;


import com.atlas.bank.application.port.out.TransactionRepositoryPort;
import com.atlas.bank.domain.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class TransactionProcessor<T> {

    protected final TransactionRepositoryPort transactionRepository;

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
