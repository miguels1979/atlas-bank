package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

public record PendingState() implements TransactionState {

    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.PENDING;
    }

    @Override
    public TransactionState validate() {
        return new ValidatedState();
    }

    @Override
    public TransactionState reject(String reason) {
        return new RejectedState();
    }
}
