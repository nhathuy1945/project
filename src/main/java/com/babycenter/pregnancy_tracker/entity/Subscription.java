package com.babycenter.pregnancy_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;

    private String p256dh;

    private String auth;

    @Column(name = "user_id")
    private Long userId; // Thêm trường userId

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}