package com.simple_be.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_be.demo.model.Guardian;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}