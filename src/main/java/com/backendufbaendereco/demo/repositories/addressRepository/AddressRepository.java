package com.backendufbaendereco.demo.repositories.addressRepository;


import com.backendufbaendereco.demo.entities.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT COUNT(uam) FROM UserAddressMapping uam " +
            "WHERE uam.userId.id = :userId " +
            "AND uam.id IN (SELECT ua.id FROM UserAddressMapping ua WHERE ua.isMainAddress = false)")
    long countAddressesByUser(@Param("userId") Long userId);
}
