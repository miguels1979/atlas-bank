package com.atlas.bank.application.service;

import com.atlas.bank.application.command.CreateAccountCommand;
import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountUseCase;
import com.atlas.bank.domain.model.account.Account;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, ListAccountUseCase , GetAccountUseCase {

    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountUseCase listAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AuditableAccountService(@Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
                                   @Qualifier("accountService") ListAccountUseCase listAccountUseCase,
                                   @Qualifier("accountService") GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.listAccountUseCase = listAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }


    @PostConstruct
    public void init() {

        log.info("Clase real del accountService: {}", createAccountUseCase.getClass().getName());
        log.info("Clase real del accountService: {}", listAccountUseCase.getClass().getName());
        log.info("Clase real del accountService: {}", getAccountUseCase.getClass().getName());
    }

    @Override
    public Account create(CreateAccountCommand command) {
        //Se estaría persistiendo en una tabla diferente, de auditoría
        log.info("Creando cuenta - numero: {}, titular:{}",
                command.accountNumber(),command.ownerName());
        Account created = createAccountUseCase.create(command);
        log.info("Cuenta creada exitosamente - ID: {}", created.getId());
        return created;
    }

    @Override
    public List<Account> findAll() {
        return listAccountUseCase.findAll();
    }

    @Override
    public Account findById(Long id) {
        return getAccountUseCase.findById(id);
    }

}
