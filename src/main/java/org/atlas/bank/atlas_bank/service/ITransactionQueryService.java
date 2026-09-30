package org.atlas.bank.atlas_bank.service;

import org.atlas.bank.atlas_bank.model.Transaction;

import java.util.List;

public interface ITransactionQueryService {

   List<Transaction> getByAccountId(Long id);
}
