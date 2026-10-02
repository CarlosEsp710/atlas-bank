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
@RequiredArgsConstructor
public class TransferService implements ITransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final List<FeeCalculator> feeCalculators;

    @Override
    @Transactional
    public Transaction execute(Long fromId, Long toId, BigDecimal amount) {
        // Buscar cuentas
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        // Validar que la cuenta esté activa
        if (!"ACTIVE".equals(from.getStatus())) {
            throw new AccountNotActiveException(fromId, from.getStatus());
        }
        if (!"ACTIVE".equals(to.getStatus())) {
            throw new AccountNotActiveException(toId, to.getStatus());
        }

        // Validar fondos
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(fromId, from.getBalance(), amount);
        }

        // Calcular comisión
        BigDecimal fee = feeCalculators.stream()
                .filter(fc -> fc.supports(from.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró un calculador de comisiones para el tipo de cuenta"))
                .calculateFee(amount);

        // Actualizar saldos
        from.setBalance(from.getBalance().subtract(amount).subtract(fee));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(fromId);
        transaction.setTargetAccountId(toId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setStatus("EXECUTED");

        return transactionRepository.save(transaction);
    }
}
