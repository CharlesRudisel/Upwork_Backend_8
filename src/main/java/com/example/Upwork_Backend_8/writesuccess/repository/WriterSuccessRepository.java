package com.example.Upwork_Backend_8.writesuccess.repository;

import com.example.Upwork_Backend_8.writesuccess.entity.WriterSuccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterSuccessRepository extends JpaRepository<WriterSuccess, Long> {
}

