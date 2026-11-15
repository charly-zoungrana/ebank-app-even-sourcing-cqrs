package net.zoungrana.ebankescqrs.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

@Getter
public class AccountActivatedEvent {
    private String id;
    private AccountStatus status;

    @JsonCreator
    public AccountActivatedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("status") AccountStatus status) {
        this.id = id;
        this.status = status;
    }
}
