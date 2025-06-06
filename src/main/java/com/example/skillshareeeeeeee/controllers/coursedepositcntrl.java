package com.example.skillshareeeeeeee.controllers;

import com.example.skillshareeeeeeee.dto.CourseDepositDTO;
import com.example.skillshareeeeeeee.models.ApiResponse;
import com.example.skillshareeeeeeee.services.coursedepositsrvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-deposits")
public class coursedepositcntrl {

    private final coursedepositsrvc depositService;

    public coursedepositcntrl(coursedepositsrvc depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseDepositDTO>> create(@RequestBody CourseDepositDTO dto) {
        ApiResponse<CourseDepositDTO> response = depositService.createDeposit(dto);
        int status = (response.getData() == null) ? 400 : 201;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ApiResponse<CourseDepositDTO>>> getAll() {
        List<ApiResponse<CourseDepositDTO>> deposits = depositService.getAllDeposits();
        return ResponseEntity.ok(deposits);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<CourseDepositDTO>> getById(@PathVariable Integer id) {
        ApiResponse<CourseDepositDTO> response = depositService.getDepositById(id);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CourseDepositDTO>> update(@PathVariable Integer id, @RequestBody CourseDepositDTO dto) {
        ApiResponse<CourseDepositDTO> response = depositService.updateDeposit(id, dto);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        ApiResponse<Void> response = depositService.deleteDeposit(id);
        int status = "SUCCESS".equals(response.getStatus()) ? 200 : 404;
        return ResponseEntity.status(status).body(response);
    }
}