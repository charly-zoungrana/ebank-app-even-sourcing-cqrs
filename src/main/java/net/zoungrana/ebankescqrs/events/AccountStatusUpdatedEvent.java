package net.zoungrana.ebankescqrs.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

@Getter
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;

    @JsonCreator
    public AccountStatusUpdatedEvent(
            @JsonProperty("accountId") String accountId,
            @JsonProperty("status") AccountStatus status) {
        this.accountId = accountId;
        this.status = status;
    }
}
