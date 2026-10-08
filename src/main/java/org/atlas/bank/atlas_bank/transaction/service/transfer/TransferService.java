package org.atlas.bank.atlas_bank.transaction.service.transfer;

import jakarta.transaction.Transactional;
import org.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.atlas.bank.atlas_bank.account.repository.AccountRepository;
import org.atlas.bank.atlas_bank.transaction.repository.TransactionRepository;
import org.atlas.bank.atlas_bank.transaction.service.domain.TransferDomainService;
import org.atlas.bank.atlas_bank.transaction.service.factory.TransactionFactory;
import org.atlas.bank.atlas_bank.transaction.service.fee.FeeCalculator;
import org.atlas.bank.atlas_bank.transaction.validation.chain.TransferValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {
    private final AccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;

    public TransferService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            List<FeeCalculator> feeCalculators,
            List<TransferValidator> validators,
            TransferDomainService transferDomainService
    ) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
        this.transferDomainService = transferDomainService;
        this.validators = validators;
    }


    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        Transaction transaction = process(new TransferContext(from, to, amount));

        transaction.advanceTo(transaction.getState().validate());
        transaction.advanceTo(transaction.getState().execute());
        transaction.markAsExecuted();
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

        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        // Crear y guardar transacción
        Transaction transaction = TransactionFactory.createTransaction(context, fee);

        return transactionRepository.save(transaction);
    }
}
