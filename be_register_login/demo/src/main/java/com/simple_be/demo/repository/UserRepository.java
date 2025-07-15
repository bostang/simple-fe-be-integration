package com.simple_be.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_be.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA secara otomatis akan mengimplementasikan metode ini
    Optional<User> findByUsername(String username);
}