package net.zoungrana.ebankescqrs.query.handlers;

import lombok.RequiredArgsConstructor;
import net.zoungrana.ebankescqrs.query.dtos.AccountStatementResponseDTO;
import net.zoungrana.ebankescqrs.query.entities.Account;
import net.zoungrana.ebankescqrs.query.entities.AccountOperation;
import net.zoungrana.ebankescqrs.query.queries.GetAccountStatementQuery;
import net.zoungrana.ebankescqrs.query.queries.GetAllAccountQuery;
import net.zoungrana.ebankescqrs.query.queries.WatchEventQuery;
import net.zoungrana.ebankescqrs.query.repository.AccountOperationRepository;
import net.zoungrana.ebankescqrs.query.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountQueryHandler {
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;

    @QueryHandler
    public List<Account> getAllAccounts(GetAllAccountQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO getAccountStatement(GetAccountStatementQuery query){
        Account account=accountRepository.findById(query.getAccountId()).orElseThrow(()->new RuntimeException("Account not found"));
        List<AccountOperation> accountOperations=accountOperationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account,accountOperations);
    }

    @QueryHandler
    public AccountOperation on(WatchEventQuery query){
        return AccountOperation.builder().build();
    }
}
