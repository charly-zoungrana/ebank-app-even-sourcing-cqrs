package net.zoungrana.ebankescqrs.query.repository;

import net.zoungrana.ebankescqrs.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByAccountId(String accountId);
}
