package net.zoungrana.ebankescqrs.commands.controllers;

import lombok.RequiredArgsConstructor;
import net.zoungrana.ebankescqrs.commands.commands.AddAccountCommand;
import net.zoungrana.ebankescqrs.commands.dtos.AddAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/command/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/add")
    public  CompletableFuture<String> createAccount(@RequestBody AddAccountRequestDTO requestDTO){

        AddAccountCommand createAccountCommand= AddAccountCommand.builder()
                .id(UUID.randomUUID().toString())
                .initialBalance(requestDTO.initialBalance())
                .currency(requestDTO.currency())
                .build();

        CompletableFuture<String> response = commandGateway.send(createAccountCommand);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }
}
