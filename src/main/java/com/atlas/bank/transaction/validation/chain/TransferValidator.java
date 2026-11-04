package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.transaction.service.transfer.TransferContext;

public interface TransferValidator {

    void validate(TransferContext context);
}
