package com.example.Upwork_Backend_8.clientbackground.entity;

import com.example.Upwork_Backend_8.clients.entity.Client;
import jakarta.persistence.*;

@Entity
@Table(name = "client_backgrounds")
public class ClientBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "details")
    private String details;

    // Getters and Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
