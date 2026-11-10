package com.atlas.bank.account.repository;

import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.application.port.out.AccountRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, DomainAccountRepository, AccountRepositoryPort {
}
