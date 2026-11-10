package com.atlas.bank.account.service;


import com.atlas.bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepositoryPort accountRepository;

    @Override
    @Transactional
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accounts", key = "#id")
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                ()-> new AccountNotFoundException(id)
        );
    }

}
