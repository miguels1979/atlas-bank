package com.atlas.bank.shared.model;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Email {

    private String value;

    private Email(String value) {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if(!normalized.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
        this.value = normalized;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
