package com.atlas.bank.account.service;

import com.atlas.bank.application.service.IAccountService;
import com.atlas.bank.domain.model.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class CachedAccountService implements IAccountService {

    private final IAccountService delegate;
    private final Map<Long, Account> cache = new ConcurrentHashMap<>();

    public CachedAccountService(@Qualifier("auditableAccountService") IAccountService delegate) {
        this.delegate = delegate;
    }


    @Override
    public Account create(Account account) {
        Account created = delegate.create(account);
        cache.put(created.getId(), created);
        log.info("Cuenta {} agregada al caché", created.getId());
        return created;
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public Account findById(Long id) {
        Account cached = cache.get(id);
        if (cached != null) {
            log.info("Cuenta {} encontrada en caché -NO se llama al service real", id);
            return cached;
        }
        log.info("Cuenta {} no esta en caché - delegando al servicio real", id);
        Account account = delegate.findById(id);
        cache.put(id, account);
        return account;
    }
}
