package org.atlas.bank.atlas_bank.application.service;

import jakarta.transaction.Transactional;
import org.atlas.bank.atlas_bank.application.port.out.TransactionRepositoryPort;
import org.atlas.bank.atlas_bank.domain.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.domain.model.account.Account;
import org.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import org.atlas.bank.atlas_bank.application.port.out.AccountRepositoryPort;
import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;
import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;
import org.atlas.bank.atlas_bank.domain.service.TransferDomainService;
import org.atlas.bank.atlas_bank.domain.strategy.fee.FeeCalculator;
import org.atlas.bank.atlas_bank.domain.validation.TransferValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService, TransferMoneyUseCase {
    private final AccountRepositoryPort accountRepositoryPort;
    private final List<FeeCalculator> feeCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(
            TransactionRepositoryPort transactionRepositoryPort,
            AccountRepositoryPort accountRepositoryPort,
            List<FeeCalculator> feeCalculators,
            List<TransferValidator> validators,
            TransferDomainService transferDomainService
    ) {
        super(transactionRepositoryPort);
        this.accountRepositoryPort = accountRepositoryPort;
        this.feeCalculators = feeCalculators;
        this.transferDomainService = transferDomainService;
        this.validators = validators;
    }


    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepositoryPort.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepositoryPort.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));
        transaction.executeTransfer();
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    protected void validate(TransferContext context) {
        validators.forEach(validator -> validator.validate(context));
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {
        // Calcular comisión según el tipo de cuenta
        return feeCalculators.stream()
                .filter(fc -> fc.supports(context.fromAccount().getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró un calculador de comisiones para el tipo de cuenta"))
                .calculateFee(context.amount());
    }

    @Override
    protected void execute(TransferContext context, BigDecimal fee) {
        // Actualizar saldos
        Account from = context.fromAccount();
        Account to = context.toAccount();

        transferDomainService.transfer(from, to, context.amount(), fee);

        accountRepositoryPort.save(from);
        accountRepositoryPort.save(to);
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        // Crear y guardar transacción
        Transaction transaction = TransactionFactory.createTransaction(context, fee);

        return transactionRepository.save(transaction);
    }
}
