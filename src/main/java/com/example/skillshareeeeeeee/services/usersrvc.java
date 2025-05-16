package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.UserDto;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class usersrvc {

    private final userrep userRepository;
    private final courserep courseRepository;
    private final PasswordEncoder passwordEncoder;

    public usersrvc(userrep userRepository, courserep courseRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public usermdl createUser(usermdl user) {
        if (user.getCourses() != null) {
            user.getCourses().forEach(course -> course.setOwner(user));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<UserDto> updateUser(Integer id, usermdl userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(userDetails.getEmail());
                    user.setUsername(userDetails.getUsername());
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    user.setImage(userDetails.getImage());

                    if (userDetails.getCourses() != null) {
                        userDetails.getCourses().forEach(course -> course.setOwner(user));
                        user.setCourses(userDetails.getCourses());
                    }

                    return convertToDto(userRepository.save(user));
                });
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }

        usermdl user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));

        if (user.getCourses() != null && !user.getCourses().isEmpty()) {
            user.getCourses().forEach(course -> course.setOwner(null));
        }

        if (user.getComments() != null && !user.getComments().isEmpty()) {
            user.getComments().forEach(comment -> comment.setUser(null));
        }

        userRepository.save(user);

        userRepository.deleteById(id);
        return true;
    }
    public UserDto convertToDto(usermdl user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getImage(),
                user.getPassword()
        );
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}