package org.atlas.bank.atlas_bank.domain.validation;

import org.atlas.bank.atlas_bank.domain.model.transaction.TransferContext;

public interface TransferValidator {
    void validate(TransferContext context);
}
