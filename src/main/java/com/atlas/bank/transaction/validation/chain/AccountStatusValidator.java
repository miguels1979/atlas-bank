package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.account.model.AccountStatus;
import com.atlas.bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class AccountStatusValidator implements TransferValidator {

    @Override
    public void validate(TransferContext context) {

        // Validar que la cuenta este activa
        if(context.from().getStatus() != AccountStatus.ACTIVE){
            throw new AccountNotActiveException(context.from().getId(), context.from().getStatus().name());
        }
        if(context.to().getStatus() != AccountStatus.ACTIVE){
            throw new AccountNotActiveException(context.to().getId(), context.to().getStatus().name());
        }
    }
}
