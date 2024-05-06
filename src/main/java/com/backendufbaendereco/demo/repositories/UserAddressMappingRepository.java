package com.backendufbaendereco.demo.repositories;

import com.backendufbaendereco.demo.Projections.AddressResponseProjections;
import com.backendufbaendereco.demo.entities.user.UserAddressMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressMappingRepository extends JpaRepository<UserAddressMapping, Long> {

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE user_address uam SET uam.is_main_address = false WHERE uam.user_id = :userId")
    void updateIsMainAddressByUserId(Long userId);



    @Query(nativeQuery = true,value = "SELECT * FROM user_address uam WHERE uam.user_id = :userId AND uam.address_id = :addressId")
    Optional<UserAddressMapping> findByUserIdAndByAddressId(Long userId, Long addressId);

    @Query(nativeQuery = true,value = "SELECT " +
                    "addr.id, addr.postal_code as postalCode, addr.street, addr.location, " +
                    "addr.location_type as locationType, addr.neighborhood, addr.street_type as streetType, " +
                    "addr.number, addr.block, addr.lot, addr.complement, addr.city_id as cityId, addr.state_id as stateId," +
                    "c.name as cityName, s.name as stateName, " +
                    "uam.is_main_address  as isMainAddress FROM addresses addr " +
                    "INNER JOIN user_address uam ON uam.address_id = addr.id " +
                    "INNER JOIN cities c ON c.id = addr.city_id " +
                    "INNER JOIN states s ON s.id = addr.state_id " +
                    "WHERE uam.user_id = :userId order by uam.is_main_address desc")
    Page<AddressResponseProjections> findAllUserAddress(Pageable pageable,Long userId);

    @Query(nativeQuery = true,value = "SELECT " +
            "addr.id, addr.postal_code as postalCode, addr.street, addr.location, " +
            "addr.location_type as locationType, addr.neighborhood, addr.street_type as streetType, " +
            "addr.number, addr.block, addr.lot, addr.complement, addr.city_id as cityId, addr.state_id as stateId, " +
            "c.name as cityName, s.name as stateName, " +
            "uam.is_main_address  as isMainAddress FROM addresses addr " +
            "INNER JOIN user_address uam ON uam.address_id = addr.id " +
            "INNER JOIN cities c ON c.id = addr.city_id " +
            "INNER JOIN states s ON s.id = addr.state_id " +
            "WHERE uam.user_id = :userId " +
            " AND uam.address_id = :addressId " +
            "limit 1")

    Optional<AddressResponseProjections> findByAddressIdUserAddress(Long userId, Long addressId);
}
