package com.atlas.bank.infrastructure.adapter.listener;

import com.atlas.bank.domain.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        //En una aplicación podría llamar a un servicio para enviar un sms, o email
        log.info("Enviando comprobante de {} por ${} - Transaction #{}",
                event.type(), event.amount(), event.transactionId());
    }
}
