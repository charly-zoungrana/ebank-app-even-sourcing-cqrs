package net.zoungrana.ebankescqrs.query.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoungrana.ebankescqrs.enums.OperationType;
import net.zoungrana.ebankescqrs.events.*;
import net.zoungrana.ebankescqrs.query.entities.Account;
import net.zoungrana.ebankescqrs.query.entities.AccountOperation;
import net.zoungrana.ebankescqrs.query.repository.AccountOperationRepository;
import net.zoungrana.ebankescqrs.query.repository.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountHandler {
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("================== Query Side AccountCreatedEvent received ==================");
        Account account=Account.builder()
                .id(event.getId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("================== Query Side AccountActivatedEvent received ==================");
        Account account=accountRepository.findById(event.getId()).orElseThrow(()->new RuntimeException("Account not found"));
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("============================ Query Side AccountStatusUpdatedEvent received =============================");
        Account account=accountRepository.findById(event.getAccountId()).orElseThrow(()->new RuntimeException("Account not found"));
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event,EventMessage eventMessage){
        log.info("====================== Query Side AccountDebitedEvent received =======================");
        Account account=accountRepository.findById(event.getAccountId()).orElseThrow(()->new RuntimeException("Account not found"));
        AccountOperation accountOperation=AccountOperation.builder()
                .amount(event.getAmount())
                .type(OperationType.DEBIT)
                .date(eventMessage.getTimestamp())
                .currency(event.getCurrency())
                .account(account)
                .build();

        account.setBalance(account.getBalance()-accountOperation.getAmount());
        accountOperationRepository.save(accountOperation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("====================== Query Side AccountCreditedEvent received =======================");
        Account account=accountRepository.findById(event.getAccountId()).orElseThrow(()->new RuntimeException("Account not found"));
        AccountOperation accountOperation=AccountOperation.builder()
                .amount(event.getAmount())
                .type(OperationType.CREDIT)
                .date(eventMessage.getTimestamp())
                .currency(event.getCurrency())
                .account(account)
                .build();

        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountOperationRepository.save(accountOperation);
    }

}
