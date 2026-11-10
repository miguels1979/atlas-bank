package com.atlas.bank.domain.model.transaction.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

public record ReversedState() implements TransactionState {
    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.REVERSED;
    }
}
