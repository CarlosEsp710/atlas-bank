package org.atlas.bank.atlas_bank.transaction.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
}
