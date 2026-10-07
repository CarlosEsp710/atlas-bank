package org.atlas.bank.atlas_bank.transaction.service.fraud;

public record FraudCheckResult(
        boolean isBlocked,
        String reason
) {
    public static FraudCheckResult blocked(String reason) {
        return new FraudCheckResult(true, reason);
    }

    public static FraudCheckResult allowed() {
        return new FraudCheckResult(false, null);
    }
}
