package net.zoungrana.ebankescqrs.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

@Getter
public class AccountCreatedEvent {
    private String id;
    private double initialBalance;
    private String currency;
    private AccountStatus status;

    @JsonCreator
    public AccountCreatedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("initialBalance") double initialBalance,
            @JsonProperty("currency") String currency,
            @JsonProperty("status") AccountStatus status) {
        this.id = id;
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
