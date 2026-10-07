package org.atlas.bank.atlas_bank.transaction.service.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ExternalFraudCheckAdapter implements FraudChecker {
    @Override
    public FraudCheckResult checkTransaction(Long accountId, BigDecimal amount) {
        ExternalFraudResponse response = callExternalFraudService(accountId, amount);
        log.info("External fraud check response for account {}: {}", accountId, response);

        if ("BLOCK".equalsIgnoreCase(response.getRecommendation())) {
            return FraudCheckResult.blocked("Transaction blocked due to external fraud check: " + response.getRiskLevel());
        }

        return FraudCheckResult.allowed();
    }

    private ExternalFraudResponse callExternalFraudService(Long accountId, BigDecimal amount) {
        // Simulate an external service call
        // In a real implementation, this would involve making an HTTP request to the external service
        // For demonstration purposes, we'll return a mock response
        if (amount.compareTo(BigDecimal.valueOf(1000000)) > 0) {
            return new ExternalFraudResponse("HIGH", 0.9, "BLOCK");
        } else {
            return new ExternalFraudResponse("LOW", 0.1, "ALLOW");
        }
    }
}
