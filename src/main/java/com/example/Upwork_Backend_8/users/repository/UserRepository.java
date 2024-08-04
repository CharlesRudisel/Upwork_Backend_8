package com.example.Upwork_Backend_8.users.repository;


import com.example.Upwork_Backend_8.users.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username); // Ensure this method returns Optional
}

