package com.atlas.bank.domain.validation;

import com.atlas.bank.domain.model.transaction.TransferContext;

public interface TransferValidator {

    void validate(TransferContext context);
}
