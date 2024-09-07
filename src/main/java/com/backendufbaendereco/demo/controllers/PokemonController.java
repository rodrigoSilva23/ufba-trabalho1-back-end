package com.backendufbaendereco.demo.controllers;

import com.backendufbaendereco.demo.DTO.PokemonRequestDTO;
import com.backendufbaendereco.demo.DTO.PokemonResponseDTO;
import com.backendufbaendereco.demo.services.PokemonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;
    @PostMapping
    public ResponseEntity<PokemonResponseDTO> save(@RequestBody @Valid PokemonRequestDTO pokemonRequest) {

        PokemonResponseDTO response = pokemonService.save(pokemonRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
