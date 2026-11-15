package net.zoungrana.ebankescqrs.commands.dtos;

public record AddAccountRequestDTO(double initialBalance, String currency) {
}
