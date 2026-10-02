package org.atlas.bank.atlas_bank.transaction.DTO;

import org.atlas.bank.atlas_bank.transaction.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);

}
