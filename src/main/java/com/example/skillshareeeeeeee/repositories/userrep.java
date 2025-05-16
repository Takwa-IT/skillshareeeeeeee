package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.usermdl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userrep extends JpaRepository<usermdl, Integer> {
    Optional<usermdl> findByEmail(String email);

    boolean existsByEmail(String email);

    }