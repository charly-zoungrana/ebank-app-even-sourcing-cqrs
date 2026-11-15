package net.zoungrana.ebankescqrs.query.entities;

import jakarta.persistence.*;
import lombok.*;
import net.zoungrana.ebankescqrs.enums.AccountStatus;

import java.time.Instant;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private Instant createdAt;
    @OneToMany(mappedBy="account")
    private List<AccountOperation> operations;
}
