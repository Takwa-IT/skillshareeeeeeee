package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.LoginRequest;
import com.example.skillshareeeeeeee.dto.UserDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.userrep;
import com.example.skillshareeeeeeee.services.usersrvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class usercntrl {

    private final usersrvc userService;
    private final userrep userRepository;

    private final PasswordEncoder passwordEncoder;

    public usercntrl(usersrvc userService, userrep userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDto convertToDto(usermdl user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getImage(),
                user.getPassword()
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        ApiResponse<List<UserDto>> response = new ApiResponse<>("success",  users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Integer id) {
        Optional<UserDto> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            ApiResponse<UserDto> response = new ApiResponse<>("success", userOptional.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserDto> response = new ApiResponse<>("failure", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody usermdl user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null));
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usermdl createdUser = userService.createUser(user);
        return ResponseEntity.ok(new ApiResponse<>("success", userService.convertToDto(createdUser)));
    }

    @PutMapping("/UpdateById/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable Integer id,
            @RequestBody usermdl userDetails) {
        Optional<UserDto> updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser.isPresent()) {
            ApiResponse<UserDto> response = new ApiResponse<>("success",  updatedUser.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserDto> response = new ApiResponse<>("failure", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            ApiResponse<Void> response = new ApiResponse<>("SUCCESS", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> errorResponse = new ApiResponse<>("FAILURE", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<ApiResponse<UserDto>> uploadImage(@PathVariable Integer id,
                                                            @RequestParam("image") MultipartFile imageFile) throws IOException {

        Optional<usermdl> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            usermdl user = userOptional.get();
            user.setImage(imageFile.getBytes());
            userRepository.save(user);

            ApiResponse<UserDto> response = new ApiResponse<>("success", convertToDto(user));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("failure", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody LoginRequest request) {
        Optional<usermdl> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            usermdl user = userOptional.get();

            System.out.println("Input Password: " + request.getPassword());
            System.out.println("Stored Hash: " + user.getPassword());
            System.out.println("PasswordEncoder class: " + passwordEncoder.getClass().getName());

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                UserDto dto = convertToDto(user);
                return ResponseEntity.ok(new ApiResponse<>("SUCCESS", dto));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("FAILURE", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("FAILURE", null));
        }
    }
}
