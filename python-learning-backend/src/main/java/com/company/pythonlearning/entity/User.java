package com.company.pythonlearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 200)
    private String avatar;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer experience = 0;

    @Column(nullable = false)
    private Integer totalLearningTime = 0;

    @Column(nullable = false)
    private Integer completedModules = 0;

    @Column(nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        joinDate = LocalDateTime.now();
    }
}