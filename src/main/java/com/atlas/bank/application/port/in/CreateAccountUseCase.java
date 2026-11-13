package com.atlas.bank.application.port.in;

import com.atlas.bank.application.command.CreateAccountCommand;
import com.atlas.bank.domain.model.account.Account;

public interface CreateAccountUseCase {

        Account create (CreateAccountCommand command);
}
