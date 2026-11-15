package net.zoungrana.ebankescqrs.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import net.zoungrana.ebankescqrs.commands.commands.AddAccountCommand;
import net.zoungrana.ebankescqrs.commands.commands.CreditAccountCommand;
import net.zoungrana.ebankescqrs.commands.commands.DebitAccountCommand;
import net.zoungrana.ebankescqrs.enums.AccountStatus;
import net.zoungrana.ebankescqrs.events.AccountActivatedEvent;
import net.zoungrana.ebankescqrs.events.AccountCreatedEvent;
import net.zoungrana.ebankescqrs.events.AccountCreditedEvent;
import net.zoungrana.ebankescqrs.events.AccountDebitedEvent;
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
              new AccountCreatedEvent(
                      command.getId(),
                      command.getInitialBalance(),
                      command.getCurrency(),
                      AccountStatus.CREATED)
        );

        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("########## An account created event was received with id: {}####################", event.getId());
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
        this.currency = event.getCurrency();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("########## An account activated event was received with id: {}####################", event.getId());
        this.status=event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        log.info("############### Crediting account with id: {} ####################", command.getAccountId());
        if(!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("The Account " + command.getAccountId() + " is not activated");
        if(command.getAmount()<0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        log.info("############### Debiting account with id: {} ####################", command.getAccountId());
        if(!status.equals(AccountStatus.ACTIVATED)) throw new RuntimeException("The Account " + command.getAccountId() + " is not activated");
        if(balance< command.getAmount()) throw new IllegalArgumentException("Insufficient balance");
        if(command.getAmount()<0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));

    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("############### Account credited event was received with id: {} ####################", event.getAccountId());
        this.balance+=event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        log.info("############### Account debited event was received with id: {} ####################", event.getAccountId());
        this.balance-=event.getAmount();
    }
}
