package net.zoungrana.ebankescqrs.query.repository;

import net.zoungrana.ebankescqrs.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
