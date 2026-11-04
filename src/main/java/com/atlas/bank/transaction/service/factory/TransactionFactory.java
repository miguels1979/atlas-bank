package com.atlas.bank.transaction.service.factory;

import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.model.TransactionStatus;
import com.atlas.bank.transaction.model.TransactionType;
import com.atlas.bank.transaction.model.state.PendingState;
import com.atlas.bank.transaction.service.transfer.TransferContext;

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
