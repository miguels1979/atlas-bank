package com.atlas.bank.account.service;

import com.atlas.bank.account.model.Account;

import java.util.List;

public interface IAccountService {

    Account create(Account account);

    List<Account> findAll();

    Account findById(Long id);
}
