package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUserId(Long userId);
    Optional<Address> findByHealthFacilityId(Long id);
}
