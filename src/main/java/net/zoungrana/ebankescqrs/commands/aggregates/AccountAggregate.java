package net.zoungrana.ebankescqrs.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import net.zoungrana.ebankescqrs.commands.commands.AddAccountCommand;
import net.zoungrana.ebankescqrs.enums.AccountStatus;
import net.zoungrana.ebankescqrs.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;
    private String currency;

    public AccountAggregate() {}


    @CommandHandler
    public AccountAggregate(AddAccountCommand command){
        log.info("########## An account created command was received with id: {}####################", command.getId());
        if(command.getInitialBalance()<0) throw new IllegalArgumentException("Initial balance must be positive");

        AggregateLifecycle.apply(
                AccountCreatedEvent.builder()
                        .id(command.getId())
                        .initialBalance(command.getInitialBalance())
                        .currency(command.getCurrency())
                        .status(AccountStatus.CREATED)
                        .build()
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("########## An account created event was received with id: {}####################", event.getId());
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
        this.currency = event.getCurrency();
    }
}
