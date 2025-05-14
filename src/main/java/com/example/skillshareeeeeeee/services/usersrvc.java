package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.UserDto;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class usersrvc {

    private final userrep userRepository;
    private final courserep courseRepository; // Ajout du repository des cours
    private final PasswordEncoder passwordEncoder;

    public usersrvc(userrep userRepository, courserep courseRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Créer un utilisateur
    public usermdl createUser(usermdl user) {
        if (user.getCourses() != null) {
            user.getCourses().forEach(course -> course.setOwner(user));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Récupérer tous les utilisateurs (version DTO)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Récupérer un utilisateur par ID
    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    // Mettre à jour un utilisateur
    public Optional<UserDto> updateUser(Integer id, usermdl userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(userDetails.getEmail());
                    user.setUsername(userDetails.getUsername());
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    user.setFilepath(userDetails.getFilepath());

                    // Mise à jour des cours si nécessaire
                    if (userDetails.getCourses() != null) {
                        userDetails.getCourses().forEach(course -> course.setOwner(user));
                        user.setCourses(userDetails.getCourses());
                    }

                    return convertToDto(userRepository.save(user));
                });
    }

    // Supprimer un utilisateur + gérer les relations avec les cours
    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        usermdl user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Détacher tous les cours liés à cet utilisateur
        if (user.getCourses() != null && !user.getCourses().isEmpty()) {
            user.getCourses().forEach(course -> course.setOwner(null));
        }

        // Sauvegarder les modifications avant suppression
        userRepository.save(user);

        // Enfin, supprimer l'utilisateur
        userRepository.deleteById(id);

        return true;
    }

    // Conversion vers DTO
    public UserDto convertToDto(usermdl user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFilepath(),
                user.getPassword()
        );
    }

    // Trouver par email
    public Optional<usermdl> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Vérifier si email existe
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Trouver par ID ou lancer une exception
    public usermdl findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}