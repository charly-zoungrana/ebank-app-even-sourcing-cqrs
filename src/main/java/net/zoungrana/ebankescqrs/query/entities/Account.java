package net.zoungrana.ebankescqrs.query.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

import java.time.Instant;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @Builder
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private Instant createdAt;
    @OneToMany(mappedBy="account")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<AccountOperation> operations;

    @JsonCreator
    public Account(
            @JsonProperty("id") String id,
            @JsonProperty("balance") double balance,
            @JsonProperty("status") AccountStatus status,
            @JsonProperty("currency") String currency,
            @JsonProperty("createdAt") Instant createdAt
    ){
        this.id=id;
        this.balance=balance;
        this.status=status;
        this.currency=currency;
        this.createdAt=createdAt;
    }
}
