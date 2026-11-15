package net.zoungrana.ebankescqrs.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.zoungrana.ebankescqrs.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private AccountStatus status;
}
