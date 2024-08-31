package com.example.Upwork_Backend_8.clientbackground.repository;

import com.example.Upwork_Backend_8.clientbackground.entity.ClientBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientBackgroundRepository extends JpaRepository<ClientBackground, Long> {
}
