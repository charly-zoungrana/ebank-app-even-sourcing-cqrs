package net.zoungrana.ebankescqrs.query.controllers;

import lombok.RequiredArgsConstructor;
import net.zoungrana.ebankescqrs.query.dtos.AccountStatementResponseDTO;
import net.zoungrana.ebankescqrs.query.entities.Account;
import net.zoungrana.ebankescqrs.query.entities.AccountOperation;
import net.zoungrana.ebankescqrs.query.queries.GetAccountStatementQuery;
import net.zoungrana.ebankescqrs.query.queries.GetAllAccountQuery;
import net.zoungrana.ebankescqrs.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountOperation, AccountOperation> result = queryGateway.subscriptionQuery(
                new WatchEventQuery(accountId),
                ResponseTypes.instanceOf(AccountOperation.class),
                ResponseTypes.instanceOf(AccountOperation.class)
        );
        return result.initialResult().concatWith(result.updates());
    }
}
