package com.atlas.bank.transaction.repository;

import com.atlas.bank.domain.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    //querymethod
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}
