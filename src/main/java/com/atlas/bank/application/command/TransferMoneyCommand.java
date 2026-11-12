package com.atlas.bank.application.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferMoneyCommand(
        Long fromId, Long toId, BigDecimal amount
) {
}
