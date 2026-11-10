package com.atlas.bank.domain.model.transaction.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

public record ExecutedState() implements TransactionState {
    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.EXECUTED;
    }

    @Override
    public TransactionState reverse() {
        return new ReversedState();
    }
}
