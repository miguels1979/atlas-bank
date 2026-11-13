package com.atlas.bank.application.service;


import com.atlas.bank.application.command.CreateAccountCommand;
import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountUseCase;
import com.atlas.bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.domain.model.shared.Email;
import com.atlas.bank.domain.model.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase , ListAccountUseCase, GetAccountUseCase {

    private final AccountRepositoryPort accountRepository;

    @Override
    @Transactional
    public Account create(CreateAccountCommand command) {
        Account account = Account.builder()
                .accountNumber(command.accountNumber())
                .ownerName(command.ownerName())
                .email(Email.of(command.email()))
                .type(command.type())
                .balance(Money.of(command.balance(), Currency.MXN))
                .build();

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
