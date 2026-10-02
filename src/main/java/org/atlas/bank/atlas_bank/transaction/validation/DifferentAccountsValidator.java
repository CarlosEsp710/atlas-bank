package org.atlas.bank.atlas_bank.transaction.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.atlas.bank.atlas_bank.transaction.DTO.TransferRequest;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransferRequest> {
    @Override
    public boolean isValid(TransferRequest value, ConstraintValidatorContext context) {
        if (value.getSourceAccountId() == null || value.getTargetAccountId() == null) {
            return true; // Let @NotNull handle null cases
        }

        return !value.getSourceAccountId().equals(value.getTargetAccountId());
    }
}
