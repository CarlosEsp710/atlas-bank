package org.atlas.bank.atlas_bank.transaction.validation.chain;

import org.atlas.bank.atlas_bank.transaction.service.transfer.TransferContext;

public interface TransferValidator {
    void validate(TransferContext context);
}
