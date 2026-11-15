package net.zoungrana.ebankescqrs.commands.dtos;

public record CreditAccountRequestDTO(String accountId,double amount, String currency) {
}
