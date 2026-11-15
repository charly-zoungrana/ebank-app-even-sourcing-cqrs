package net.zoungrana.ebankescqrs.commands.controllers;

import lombok.RequiredArgsConstructor;
import net.zoungrana.ebankescqrs.commands.commands.AddAccountCommand;
import net.zoungrana.ebankescqrs.commands.commands.CreditAccountCommand;
import net.zoungrana.ebankescqrs.commands.commands.DebitAccountCommand;
import net.zoungrana.ebankescqrs.commands.commands.UpdateAccountStatusCommand;
import net.zoungrana.ebankescqrs.commands.dtos.AddAccountRequestDTO;
import net.zoungrana.ebankescqrs.commands.dtos.CreditAccountRequestDTO;
import net.zoungrana.ebankescqrs.commands.dtos.DebitAccountRequestDTO;
import net.zoungrana.ebankescqrs.commands.dtos.UpdateAccountStatusRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/command/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping("/add")
    public  CompletableFuture<String> createAccount(@RequestBody AddAccountRequestDTO requestDTO){

        AddAccountCommand createAccountCommand= new AddAccountCommand(
                UUID.randomUUID().toString(),
                requestDTO.initialBalance(),
                requestDTO.currency());

       return commandGateway.send(createAccountCommand);
    }

    @PostMapping("/credit")
    public  CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO requestDTO){

        CreditAccountCommand creditAccountCommand= new CreditAccountCommand(
                requestDTO.accountId(),
                requestDTO.amount(),
                requestDTO.currency());

       return commandGateway.send(creditAccountCommand);

    }

    @PostMapping("/debit")
    public  CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO requestDTO){

        DebitAccountCommand debitAccountCommand= new DebitAccountCommand(
                requestDTO.accountId(),
                requestDTO.amount(),
                requestDTO.currency());

        return commandGateway.send(debitAccountCommand);

    }

    @PutMapping("/updateAccountStatus")
    public CompletableFuture<String> updateAccountStatus(@RequestBody UpdateAccountStatusRequestDTO requestDTO){
        UpdateAccountStatusCommand updateAccountStatusCommand=new UpdateAccountStatusCommand(
                requestDTO.accountId(),
                requestDTO.status()
        );

        return commandGateway.send(updateAccountStatusCommand);
    }

    @GetMapping("/events/{accountId}")
    public Stream getEvent(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }


    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }
}
