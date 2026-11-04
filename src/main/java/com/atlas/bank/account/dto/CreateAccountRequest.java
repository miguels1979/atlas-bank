package com.atlas.bank.account.dto;

import com.atlas.bank.account.model.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "El numero de cuenta es obligatorio")
    private String accountNumber;

    @NotBlank(message = "El nombre del titular es obligatorio")
    private String ownerName;

    @Email(message = "El email no tiene un formato valido")
    @NotBlank
    private String email;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType type;

    @PositiveOrZero(message= "El balance no puede ser negativo")
    private BigDecimal balance;
}
