package org.atlas.bank.atlas_bank.transaction.service.fraud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalFraudResponse {
    private String riskLevel;
    private double score;
    private String recommendation;
}
