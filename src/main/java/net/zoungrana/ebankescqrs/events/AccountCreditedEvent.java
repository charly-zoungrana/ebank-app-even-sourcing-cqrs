package net.zoungrana.ebankescqrs.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;

    @JsonCreator
    public AccountCreditedEvent(
            @JsonProperty("accountId") String accountId,
            @JsonProperty("amount") double amount,
            @JsonProperty("currency") String currency) {
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
    }
}
