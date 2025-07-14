package com.simple_be.demo.repository;

// src/main/java/com/yourcompany/yourapp/repository/AddressRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_be.demo.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}