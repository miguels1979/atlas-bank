package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

public sealed interface TransactionState permits PendingState, ValidatedState, ExecutedState, RejectedState, ReversedState {

    TransactionStatus getStatus();

    default TransactionState validate() {
        throw new IllegalStateException("No se puede validar una transacción en estado " + getStatus());
    }
    default TransactionState execute() {
        throw new IllegalStateException("No se puede ejecutar una transacción en estado " + getStatus());
    }
    default TransactionState reject(String reason) {
        throw new IllegalStateException("No se puede rechazar una transacción en estado " + getStatus());
    }
    default TransactionState reverse() {
        throw new IllegalStateException("No se puede revertir una transacción en estado " + getStatus());
    }

}
