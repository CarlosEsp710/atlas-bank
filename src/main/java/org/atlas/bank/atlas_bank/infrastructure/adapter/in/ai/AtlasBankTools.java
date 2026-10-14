package org.atlas.bank.atlas_bank.infrastructure.adapter.in.ai;

import lombok.RequiredArgsConstructor;
import org.atlas.bank.atlas_bank.application.command.TransferMoneyCommand;
import org.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import org.atlas.bank.atlas_bank.application.port.in.TransferMoneyUseCase;
import org.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AtlasBankTools {
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Tool(
            name = "transferMoney",
            description = "Transfer money from one account to another"
    )
    public String transferMoney(
            @ToolParam(description = "The ID of the source account") Long sourceAccountId,
            @ToolParam(description = "The ID of the target account") Long targetAccountId,
            @ToolParam(description = "The amount to transfer") BigDecimal amount
    ) {
        try {
            TransferMoneyCommand command = TransferMoneyCommand.builder()
                    .fromAccountId(sourceAccountId)
                    .toAccountId(targetAccountId)
                    .amount(amount)
                    .build();

            transferMoneyUseCase.transfer(command);

            return "Transfer successful: $" + amount + " transferred from account " + sourceAccountId + " to account " + targetAccountId;
        } catch (InsufficientFundsException e) {
            return "Transfer failed: Insufficient funds in account " + sourceAccountId;
        } catch (Exception e) {
            return "Transfer failed: " + e.getMessage();
        }
    }

    @Tool(
            name = "getAccountBalance",
            description = "Get the balance of an account"
    )
    public String getAccountBalance(
            @ToolParam(description = "The ID of the account") Long accountId
    ) {
        try {
            var account = getAccountUseCase.getAccountById(accountId);
            return "Account balance for account " + accountId + ": $" + account.getBalance();
        } catch (Exception e) {
            return "Failed to get account balance: " + e.getMessage();
        }
    }
}
