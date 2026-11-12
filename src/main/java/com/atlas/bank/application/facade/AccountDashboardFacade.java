package com.atlas.bank.application.facade;


import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.GetTransactionsByAccountUseCase;
import com.atlas.bank.application.query.DashBoardReadModel;
import com.atlas.bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.application.query.TransactionReadModel;
import com.atlas.bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final GetAccountUseCase getAccountUseCase;
    private final GetTransactionsByAccountUseCase getTransactionsByAccountUseCase;


    public DashBoardReadModel getDashboard(Long accountId) {

        Account account =  getAccountUseCase.findById(accountId);

        List<TransactionReadModel> transactions = getTransactionsByAccountUseCase
                .getByAccountId(new GetAccountStatementQuery(accountId));


        return DashBoardReadModel.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();


    }

}
