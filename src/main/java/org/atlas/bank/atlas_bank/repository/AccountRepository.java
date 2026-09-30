package org.atlas.bank.atlas_bank.repository;

import org.atlas.bank.atlas_bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
