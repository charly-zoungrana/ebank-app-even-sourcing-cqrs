package net.zoungrana.ebankescqrs.query.dtos;

import net.zoungrana.ebankescqrs.query.entities.Account;
import net.zoungrana.ebankescqrs.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account,
        List<AccountOperation> accountOperations
        ) {
}
