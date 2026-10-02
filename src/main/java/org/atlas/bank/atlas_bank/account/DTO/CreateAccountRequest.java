package org.atlas.bank.atlas_bank.account.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type;
    private BigDecimal balance;
}
