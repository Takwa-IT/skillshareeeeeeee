package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.usermdl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userrep extends JpaRepository<usermdl, Integer> {
    // Trouver un utilisateur par email
    Optional<usermdl> findByEmail(String email);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Trouver un utilisateur par username
    Optional<usermdl> findByUsername(String username);

    // Trouver les utilisateurs par filepath (si nécessaire)
    Optional<usermdl> findByFilepath(String filepath);
}