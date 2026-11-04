package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account from, Account to, BigDecimal amount) {

}
