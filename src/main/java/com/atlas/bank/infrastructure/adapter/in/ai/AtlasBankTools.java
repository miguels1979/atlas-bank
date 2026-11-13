package com.atlas.bank.infrastructure.adapter.in.ai;

import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtlasBankTools {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase  getAccountUseCase;


}
