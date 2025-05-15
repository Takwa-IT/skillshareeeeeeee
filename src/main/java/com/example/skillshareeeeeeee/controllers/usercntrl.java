package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.UserDto;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.userrep;
import com.example.skillshareeeeeeee.services.usersrvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Constructeur
    public usercntrl(usersrvc userService,userrep userRepository) {
        this.userService = userService;
        this.userRepository=userRepository;
    }

    // Méthode pour mapper UserDto vers usermdl
    private usermdl mapToUserModel(UserDto userDto) {
        usermdl user = new usermdl();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setImage(userDto.getImage()); // Nouveau champ
        return user;
    }

    private UserDto convertToDto(usermdl user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getImage(),      // byte[]
                user.getPassword()
        );
    }

    // Récupérer tous les utilisateurs
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        ApiResponse<List<UserDto>> response = new ApiResponse<>("success",  users);
        return ResponseEntity.ok(response);
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/get/{id}")
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

    // Créer un nouvel utilisateur
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody usermdl user) {
        if (userService.emailExists(user.getEmail())) {
            ApiResponse<UserDto> response = new ApiResponse<>("failure",  null);
            return ResponseEntity.badRequest().body(response);
        }
        usermdl createdUser = userService.createUser(user);
        ApiResponse<UserDto> response = new ApiResponse<>("success",  userService.convertToDto(createdUser));
        return ResponseEntity.ok(response);
    }

    // Mettre à jour un utilisateur
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

    // Supprimer un utilisateur
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
}
