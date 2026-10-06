package org.atlas.bank.atlas_bank.transaction.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.atlas.bank.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Enviando comprobante de {} por ${} desde la cuenta {} a la cuenta {} con comisión de ${}",
                event.type(), event.amount(), event.sourceAccountId(), event.destinationAccountId(), event.fee());
    }
}
