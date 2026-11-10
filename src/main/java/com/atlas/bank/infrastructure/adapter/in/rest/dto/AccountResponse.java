package com.atlas.bank.infrastructure.adapter.in.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type; //SAVINGS, CHECKING
    private BigDecimal balance;
    private String status; //ACTIVE, CLOSED, FROZEN
    private LocalDateTime createdAt;
}
