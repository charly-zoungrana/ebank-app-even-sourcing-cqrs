package net.zoungrana.ebankescqrs.commands.dtos;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}
