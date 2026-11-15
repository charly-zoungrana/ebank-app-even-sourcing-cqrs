package net.zoungrana.ebankescqrs.query.controllers;

import lombok.RequiredArgsConstructor;
import net.zoungrana.ebankescqrs.query.dtos.AccountStatementResponseDTO;
import net.zoungrana.ebankescqrs.query.entities.Account;
import net.zoungrana.ebankescqrs.query.queries.GetAccountStatementQuery;
import net.zoungrana.ebankescqrs.query.queries.GetAllAccountQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@RequiredArgsConstructor
public class AccountQueryController {
    private final QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        return queryGateway.query(new GetAllAccountQuery(),
                ResponseTypes.multipleInstancesOf(Account.class));
    }

    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId){

        return queryGateway.query(
                new GetAccountStatementQuery(accountId),
                ResponseTypes.instanceOf(AccountStatementResponseDTO.class)
        );
    }


}
