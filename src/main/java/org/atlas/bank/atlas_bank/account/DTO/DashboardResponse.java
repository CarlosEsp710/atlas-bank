package org.atlas.bank.atlas_bank.account.DTO;

import lombok.Builder;
import lombok.Data;
import org.atlas.bank.atlas_bank.transaction.DTO.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private Long accountId;
    private String accountNumber;
    private String ownerName;
    private String type;
    private BigDecimal balance;
    private String status;
    private List<TransactionResponse> recentTransactions;
}
