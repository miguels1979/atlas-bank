package com.atlas.bank.domain.model.transaction.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

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
