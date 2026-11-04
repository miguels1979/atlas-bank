package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

public record ValidatedState() implements TransactionState {
    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.VALIDATED;
    }

    @Override
    public TransactionState reject(String reason) {
        return new RejectedState();
    }

    @Override
    public TransactionState execute() {
        return new ExecutedState();
    }
}
