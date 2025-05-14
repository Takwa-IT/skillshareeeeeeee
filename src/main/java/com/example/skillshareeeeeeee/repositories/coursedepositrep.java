package com.example.skillshareeeeeeee.repositories;

import com.example.skillshareeeeeeee.models.CourseDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface coursedepositrep extends JpaRepository<CourseDeposit, Integer> {
}
