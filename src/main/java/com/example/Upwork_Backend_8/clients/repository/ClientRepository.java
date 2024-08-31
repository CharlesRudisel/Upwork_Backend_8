package com.example.Upwork_Backend_8.clients.repository;

import com.example.Upwork_Backend_8.clients.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}

