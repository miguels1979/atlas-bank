package com.atlas.bank.application.port.in;

import com.atlas.bank.domain.model.account.Account;

public interface GetAccountUseCase {
    Account findById(Long id);
}
