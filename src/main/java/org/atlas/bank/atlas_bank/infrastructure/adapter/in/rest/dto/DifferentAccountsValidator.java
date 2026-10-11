package org.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransferRequest> {
    @Override
    public boolean isValid(TransferRequest value, ConstraintValidatorContext context) {
        if (value.getSourceAccountId() == null || value.getTargetAccountId() == null) {
            return true; // Let @NotNull handle null cases
        }

        return !value.getSourceAccountId().equals(value.getTargetAccountId());
    }
}
