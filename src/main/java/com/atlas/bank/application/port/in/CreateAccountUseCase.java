package com.atlas.bank.application.port.in;

import com.atlas.bank.account.model.Account;

public interface CreateAccountUseCase {

        Account execute (Account account);
}
