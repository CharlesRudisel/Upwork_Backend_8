package com.example.Upwork_Backend_8.users.repository;

import com.example.Upwork_Backend_8.users.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    // Change this method name to match the field name in UserInfo
    Optional<UserInfo> findById(Long id);


}
