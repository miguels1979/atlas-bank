package com.atlas.bank.application.port.in;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.domain.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransferMoneyUseCase {
    Transaction transfer (TransferMoneyCommand command);
}
