package org.atlas.bank.atlas_bank.transaction.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {
    @EventListener
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Auditoría: Transacción {} de tipo {} ejecutada desde la cuenta {} a la cuenta {} por un monto de ${} con comisión de ${}",
                event.transactionId(), event.type(), event.sourceAccountId(), event.destinationAccountId(), event.amount(), event.fee());
    }
}
