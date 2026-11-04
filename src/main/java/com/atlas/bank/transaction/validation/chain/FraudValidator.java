package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.transaction.service.exception.FraudCheckException;
import com.atlas.bank.transaction.service.fraud.FraudCheckResult;
import com.atlas.bank.transaction.service.fraud.FraudChecker;
import com.atlas.bank.transaction.service.transfer.TransferContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(2)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidator{

    private final FraudChecker fraudChecker;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult fraudCheckResult = fraudChecker.check(context.from().getId(), context.amount());
        if(fraudCheckResult.blocked()){
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
