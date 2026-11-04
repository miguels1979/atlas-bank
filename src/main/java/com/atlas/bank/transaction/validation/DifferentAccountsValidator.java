package com.atlas.bank.transaction.validation;

import com.atlas.bank.transaction.dto.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransferRequest> {

    @Override
    public boolean isValid(TransferRequest transferRequest, ConstraintValidatorContext constraintValidatorContext) {
        if( transferRequest == null || transferRequest.getFromAccountId() == null  || transferRequest.getToAccountId() == null) {
            return true;
        }
        boolean valid = !transferRequest.getFromAccountId().equals(transferRequest.getToAccountId());
        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("toAccountId")
                    .addConstraintViolation();
        }
        return valid;
    }
}
