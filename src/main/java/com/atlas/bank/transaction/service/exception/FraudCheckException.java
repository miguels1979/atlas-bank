package com.atlas.bank.transaction.service.exception;

public class FraudCheckException extends RuntimeException {
    public FraudCheckException(String reason) {

        super(reason);
    }
}
