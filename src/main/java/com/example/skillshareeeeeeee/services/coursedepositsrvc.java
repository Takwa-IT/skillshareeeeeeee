package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CourseDepositDTO;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.CourseDeposit;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.coursedepositrep;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class coursedepositsrvc {

    private final coursedepositrep depositRepository;
    private final userrep userRepository;
    private final courserep courseRepository;

    public coursedepositsrvc(coursedepositrep depositRepository,
                             userrep userRepository,
                             courserep courseRepository) {
        this.depositRepository = depositRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<ApiResponse<CourseDepositDTO>> getAllDeposits() {
        return depositRepository.findAll().stream()
                .map(deposit -> new ApiResponse<>("SUCCESS", convertToDto(deposit)))
                .collect(Collectors.toList());
    }

    public ApiResponse<CourseDepositDTO> getDepositById(Integer id) {
        return depositRepository.findById(id)
                .map(deposit -> new ApiResponse<>("SUCCESS", convertToDto(deposit)))
                .orElse(new ApiResponse<>("FAILURE", null));
    }

    public ApiResponse<CourseDepositDTO> createDeposit(CourseDepositDTO dto) {
        try {
            usermdl user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            coursemdl course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            CourseDeposit deposit = new CourseDeposit();
            deposit.setDate(dto.getDate()); // ✔️ Pas besoin de new Date(...)
            deposit.setNumber(dto.getNumber());
            deposit.setUser(user);
            deposit.setCourse(course);

            CourseDeposit saved = depositRepository.save(deposit);
            return new ApiResponse<>("SUCCESS", convertToDto(saved));

        } catch (Exception e) {
            e.printStackTrace(); // 🔍 Utile pour le débogage
            return new ApiResponse<>("FAILURE", null);
        }
    }

    public ApiResponse<CourseDepositDTO> updateDeposit(Integer id, CourseDepositDTO dto) {
        Optional<CourseDeposit> existingOpt = depositRepository.findById(id);

        if (existingOpt.isEmpty()) {
            return new ApiResponse<>("FAILURE", null);
        }

        try {
            CourseDeposit existing = existingOpt.get();

            if (dto.getDate() != null) {
                existing.setDate(dto.getDate());
            }

            if (dto.getNumber() != null) {
                existing.setNumber(dto.getNumber());
            }

            if (dto.getUserId() != null) {
                usermdl user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                existing.setUser(user);
            }

            if (dto.getCourseId() != null) {
                coursemdl course = courseRepository.findById(dto.getCourseId())
                        .orElseThrow(() -> new RuntimeException("Course not found"));
                existing.setCourse(course);
            }

            CourseDeposit updated = depositRepository.save(existing);
            return new ApiResponse<>("SUCCESS", convertToDto(updated));

        } catch (Exception e) {
            return new ApiResponse<>("FAILURE", null);
        }
    }

    public ApiResponse<Void> deleteDeposit(Integer id) {
        if (!depositRepository.existsById(id)) {
            return new ApiResponse<>("FAILURE", null);
        }

        try {
            depositRepository.deleteById(id);
            return new ApiResponse<>("SUCCESS", null);
        } catch (Exception e) {
            return new ApiResponse<>("FAILURE", null);
        }
    }

    private CourseDepositDTO convertToDto(CourseDeposit deposit) {
        return new CourseDepositDTO(
                deposit.getId(),
                deposit.getDate(),
                deposit.getNumber(),
                deposit.getUser().getId(),
                deposit.getCourse().getId()
        );
    }
}