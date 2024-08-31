package com.example.Upwork_Backend_8.grades.repository;

import com.example.Upwork_Backend_8.grades.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
