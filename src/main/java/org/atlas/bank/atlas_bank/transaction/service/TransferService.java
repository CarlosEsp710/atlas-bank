package org.atlas.bank.atlas_bank.transaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import org.atlas.bank.atlas_bank.account.model.Account;
import org.atlas.bank.atlas_bank.transaction.exception.AccountNotActiveException;
import org.atlas.bank.atlas_bank.transaction.exception.InsufficientFundsException;
import org.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.atlas.bank.atlas_bank.account.repository.AccountRepository;
import org.atlas.bank.atlas_bank.transaction.repository.TransactionRepository;
import org.atlas.bank.atlas_bank.transaction.service.fee.FeeCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {
    private final AccountRepository accountRepository;
    private final List<FeeCalculator> feeCalculators;

    public TransferService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            List<FeeCalculator> feeCalculators
    ) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feeCalculators = feeCalculators;
    }

    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        return process(new TransferContext(from, to, amount));
    }

    @Override
    protected void validate(TransferContext context) {
        if (!"ACTIVE".equals(context.fromAccount().getStatus())) {
            throw new AccountNotActiveException(context.fromAccount().getId(), context.fromAccount().getStatus());
        }
        if (!"ACTIVE".equals(context.toAccount().getStatus())) {
            throw new AccountNotActiveException(context.toAccount().getId(), context.toAccount().getStatus());
        }

        // Validar fondos
        if (context.fromAccount().getBalance().compareTo(context.amount()) < 0) {
            throw new InsufficientFundsException(context.fromAccount().getId(), context.fromAccount().getBalance(), context.amount());
        }
    }

    @Override
    protected BigDecimal calculateFee(TransferContext context) {
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

        from.setBalance(from.getBalance().subtract(context.amount()).subtract(fee));
        to.setBalance(to.getBalance().add(context.amount()));
        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(context.fromAccount().getId());
        transaction.setTargetAccountId(context.toAccount().getId());
        transaction.setAmount(context.amount());
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }
}
