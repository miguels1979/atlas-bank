package com.atlas.bank.account.service;

import com.atlas.bank.domain.model.account.Account;

import java.util.List;

public interface IAccountService {

    Account create(Account account);

    List<Account> findAll();

    Account findById(Long id);
}
