package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

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
