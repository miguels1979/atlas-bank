package com.atlas.bank.infrastructure.adapter.in.ai;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class AtlasBankTools {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase  getAccountUseCase;


    @Tool(description = "Transferir dinero entre dos cuentas del banco atlas-bank")
    public String transferMoney(@ToolParam (description = "ID de la cuenta origen") String sourceAccountId,
                                @ToolParam (description = "ID de la cuenta destino") String targetAccountId,
                                @ToolParam (description = "Monto a transferir") BigDecimal amount
    ){
        try{
            TransferMoneyCommand command = TransferMoneyCommand.builder()
                    .fromId(Long.parseLong(sourceAccountId))
                    .toId(Long.parseLong(targetAccountId))
                    .amount(amount)
                    .build();
            transferMoneyUseCase.transfer(command);
            return "Transferencia realizada correctamente. Monto: " + amount ;

        }catch(InsufficientFundsException e){
            return "No se pudo transferir: saldo insuficiente. " + e.getMessage();
        }catch (NumberFormatException e){
            log.error("Error en transferMoney desde IA", e);
            return "No se pudo transferir: " + e.getMessage();
        }
    }


    @Tool(description = "Consultar el saldo inicial de una cuenta del banco")
    public String getAccountBalance(
            @ToolParam(description = "ID de la cuenta a consultar") String accountId
    ){
        try{
            var account = getAccountUseCase.findById(Long.parseLong(accountId));
            return "La cuenta " + account.getAccountNumber()
                    + " tiene un saldo de $" + account.getBalance().getAmount()
                    + " " + account.getBalance().getCurrency();
        }catch(Exception e){
            return "No se pudo consultar la cuenta " + e.getMessage();
        }

    }
}
