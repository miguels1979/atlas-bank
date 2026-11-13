package com.atlas.bank.application.validation;

import com.atlas.bank.application.port.out.FraudCheckPort;
import com.atlas.bank.domain.exception.FraudCheckException;
import com.atlas.bank.domain.model.shared.FraudCheckResult;
import com.atlas.bank.domain.model.transaction.TransferContext;
import com.atlas.bank.domain.validation.TransferValidator;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class FraudValidator implements TransferValidator {

    private final FraudCheckPort fraudCheckerPort;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult fraudCheckResult = fraudCheckerPort.check(context.from().getId(), context.amount());
        if(fraudCheckResult.blocked()){
            throw new FraudCheckException(fraudCheckResult.reason());
        }
    }
}
