package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.domain.model.account.Account;

import java.math.BigDecimal;

public record TransferContext(Account from, Account to, BigDecimal amount) {

}
