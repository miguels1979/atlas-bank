package com.atlas.bank.application.port.in;

import com.atlas.bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.application.query.TransactionReadModel;

import java.util.List;

public interface GetTransactionsByAccountUseCase {
    List<TransactionReadModel> getByAccountId(GetAccountStatementQuery query);
}
