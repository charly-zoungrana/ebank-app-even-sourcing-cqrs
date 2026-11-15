package net.zoungrana.ebankescqrs.commands.dtos;

import net.zoungrana.ebankescqrs.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}
