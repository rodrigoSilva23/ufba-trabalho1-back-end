package com.backendufbaendereco.demo.repositories;

import com.backendufbaendereco.demo.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository  extends JpaRepository<Pokemon, Long> {
}
