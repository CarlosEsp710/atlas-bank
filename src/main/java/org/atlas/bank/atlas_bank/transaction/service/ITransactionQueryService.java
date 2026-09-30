package org.atlas.bank.atlas_bank.transaction.service;

import org.atlas.bank.atlas_bank.transaction.model.Transaction;

import java.util.List;

public interface ITransactionQueryService {

   List<Transaction> getByAccountId(Long id);
}
