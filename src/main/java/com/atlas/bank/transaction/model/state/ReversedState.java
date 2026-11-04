package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

public record ReversedState() implements TransactionState {
    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.REVERSED;
    }
}
