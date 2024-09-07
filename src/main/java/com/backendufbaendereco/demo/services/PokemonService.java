package com.backendufbaendereco.demo.services;

import com.backendufbaendereco.demo.DTO.PokemonRequestDTO;
import com.backendufbaendereco.demo.DTO.PokemonResponseDTO;
import com.backendufbaendereco.demo.entities.Pokemon;
import com.backendufbaendereco.demo.entities.address.Address;
import com.backendufbaendereco.demo.entities.user.User;
import com.backendufbaendereco.demo.repositories.PokemonRepository;
import com.backendufbaendereco.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PokemonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private AddressService addressService;

    public PokemonResponseDTO save(PokemonRequestDTO pokemonRequest) {

        User user = userRepository.findById(pokemonRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address address = addressService.findById(pokemonRequest.addressId());

        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonId(pokemonRequest.pokemonId());
        pokemon.getAddresses().addAll(addresses);
        pokemon.setUser(user);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return new PokemonResponseDTO(
                savedPokemon.getId(),
                savedPokemon.getAddresses().stream().findFirst().get().getId(),
                savedPokemon.getPokemonId(),
                savedPokemon.getUser().getId());

    }
}
