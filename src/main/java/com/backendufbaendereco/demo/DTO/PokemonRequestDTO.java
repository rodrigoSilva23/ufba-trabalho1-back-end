package com.backendufbaendereco.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PokemonRequestDTO(
        @NotNull(message = "PokemonId cannot be null")
        Long pokemonId,
        @NotNull(message = "userId cannot be null")
        Long userId,
        @NotNull(message = "addressId cannot be null")
        Long addressId
) {
}
