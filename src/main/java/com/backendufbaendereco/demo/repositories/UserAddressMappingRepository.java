package com.backendufbaendereco.demo.repositories;

import com.backendufbaendereco.demo.entities.user.UserAddressMapping;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressMappingRepository extends JpaRepository<UserAddressMapping, Long> {

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE user_address uam SET uam.is_main_address = false WHERE uam.user_id = :userId")
    void updateIsMainAddressByUserId(Long userId);
}
