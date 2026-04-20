package com.hackathon.estoque.repository;

import com.hackathon.estoque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
