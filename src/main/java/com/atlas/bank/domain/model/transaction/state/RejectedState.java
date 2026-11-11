package com.atlas.bank.domain.model.transaction.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

public record RejectedState() implements TransactionState {
    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.REJECTED;
    }
}
