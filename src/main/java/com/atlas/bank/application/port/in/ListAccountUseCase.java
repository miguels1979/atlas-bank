package com.atlas.bank.application.port.in;

import com.atlas.bank.domain.model.account.Account;

import java.util.List;

public interface ListAccountUseCase {
    List<Account> findAll();
}
