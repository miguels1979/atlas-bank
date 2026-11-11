package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {

}