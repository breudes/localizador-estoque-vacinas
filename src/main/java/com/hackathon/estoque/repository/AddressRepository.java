package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.Address;
import com.hackathon.estoque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserId(Long userId);

    void deleteByUser(User user);
}
