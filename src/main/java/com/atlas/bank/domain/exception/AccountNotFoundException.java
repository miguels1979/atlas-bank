package com.atlas.bank.domain.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Long id) {
        super("No se encontró el registro para el account " + id);
    }
}
