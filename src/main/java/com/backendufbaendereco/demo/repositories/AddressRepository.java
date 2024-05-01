package com.backendufbaendereco.demo.repositories;


import com.backendufbaendereco.demo.entities.andress.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


}
