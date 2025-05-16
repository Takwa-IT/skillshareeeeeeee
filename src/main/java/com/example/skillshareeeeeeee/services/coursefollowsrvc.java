package com.example.skillshareeeeeeee.services;

import com.example.skillshareeeeeeee.dto.CourseFollowDTO;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.models.CourseFollow;
import com.example.skillshareeeeeeee.models.coursemdl;
import com.example.skillshareeeeeeee.models.usermdl;
import com.example.skillshareeeeeeee.repositories.coursefollowrep;
import com.example.skillshareeeeeeee.repositories.courserep;
import com.example.skillshareeeeeeee.repositories.userrep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class coursefollowsrvc {

    private final coursefollowrep followRepository;
    private final userrep userRepository;
    private final courserep courseRepository;

    public coursefollowsrvc(coursefollowrep followRepository, userrep userRepository, courserep courseRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    private CourseFollowDTO convertToDto(CourseFollow follow) {
            return new CourseFollowDTO(
                    follow.getId(),
                    follow.getDate(),
                    follow.getNumber(),
                    follow.getProgressReport(),
                    follow.getUser().getId(),
                    follow.getCourse().getId()
            );
        }

    public List<ApiResponse<CourseFollowDTO>> getAllFollows() {
        return followRepository.findAll().stream()
                .map(follow -> new ApiResponse<>("SUCCESS", convertToDto(follow)))
                .collect(Collectors.toList());
    }

    public ApiResponse<CourseFollowDTO> getFollowById(Integer id) {
        return followRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ApiResponse<>("SUCCESS", dto))
                .orElse(new ApiResponse<>("FAILURE", null));
    }

    public ApiResponse<CourseFollowDTO> createFollow(CourseFollowDTO dto) {
        try {
            usermdl user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            coursemdl course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            CourseFollow follow = new CourseFollow();
            follow.setDate(dto.getDate());
            follow.setNumber(dto.getNumber());
            follow.setProgressReport(dto.getProgressReport());
            follow.setUser(user);
            follow.setCourse(course);

            CourseFollow saved = followRepository.save(follow);
            return new ApiResponse<>("SUCCESS", convertToDto(saved));

        } catch (Exception e) {
            return new ApiResponse<>("FAILURE", null);
        }
    }

    public ApiResponse<CourseFollowDTO> updateFollow(Integer id, CourseFollowDTO dto) {
        return followRepository.findById(id).map(existing -> {
            if (dto.getDate() != null) existing.setDate(dto.getDate());
            if (dto.getNumber() != null) existing.setNumber(dto.getNumber());
            if (dto.getProgressReport() != null && !dto.getProgressReport().isEmpty()) {
                existing.setProgressReport(dto.getProgressReport());
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

            CourseFollow updated = followRepository.save(existing);
            return new ApiResponse<>("SUCCESS", convertToDto(updated));

        }).orElse(new ApiResponse<>("FAILURE", null));
    }

    public ApiResponse<Void> deleteFollow(Integer id) {
        if (!followRepository.existsById(id)) {
            return new ApiResponse<>("FAILURE", null);
        }

        try {
            followRepository.deleteById(id);
            return new ApiResponse<>("SUCCESS", null);
        } catch (Exception e) {
            return new ApiResponse<>("FAILURE", null);
        }
    }
}