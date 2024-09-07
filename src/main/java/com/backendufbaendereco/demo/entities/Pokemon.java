package com.backendufbaendereco.demo.entities;

import com.backendufbaendereco.demo.entities.address.Address;
import com.backendufbaendereco.demo.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pokemonId;

    @ManyToMany
    @JoinTable(
            name = "pokemon_address",
            joinColumns = @JoinColumn(name = "pokemon_id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "address_id",nullable = true)
    )
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
}
