package com.example.Upwork_Backend_8.writesuccess.entity;

import com.example.Upwork_Backend_8.users.entity.UserInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "writer_successes")
public class WriterSuccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @Column(name = "metrics")
    private String metrics;

    @Column(name = "comments")
    private String comments;

    // Getters and Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}