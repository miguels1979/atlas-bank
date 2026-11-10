package com.atlas.bank.transaction.service.factory;

import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.domain.model.transaction.TransactionStatus;
import com.atlas.bank.domain.model.transaction.TransactionType;
import com.atlas.bank.domain.model.transaction.state.PendingState;
import com.atlas.bank.domain.model.transaction.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTransfer(TransferContext context, BigDecimal fee) {
        Transaction transaction =Transaction.builder()
                .type(TransactionType.TRANSFER)
                .sourceAccountId(context.from().getId())
                .targetAccountId(context.to().getId())
                .amount(context.amount())
                .fee(fee)
                .status(TransactionStatus.PENDING)
                .build();
        transaction.advanceTo(new PendingState());
        return transaction;
    }
}
