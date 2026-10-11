package org.atlas.bank.atlas_bank.application.service;

import org.atlas.bank.atlas_bank.domain.model.transaction.Transaction;

import java.util.List;

public interface ITransactionQueryService {

   List<Transaction> getByAccountId(Long id);
}
