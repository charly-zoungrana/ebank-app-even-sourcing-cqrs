package net.zoungrana.ebankescqrs.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

@Getter @AllArgsConstructor @Builder
public class AccountCreatedEvent {
    private String id;
    private double initialBalance;
    private String currency;
    private AccountStatus status;
}
