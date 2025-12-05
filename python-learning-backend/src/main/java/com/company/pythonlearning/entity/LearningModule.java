package com.company.pythonlearning.entity;

import com.company.pythonlearning.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 学习模块实体类
 * 
 * @author Company
 * @version 1.0.0
 */
@Entity
@Table(name = "learning_modules")
@Data
@EqualsAndHashCode(callSuper = false)
public class LearningModule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "module_type")
    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;
    
    @Column(name = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    
    @Column(name = "estimated_duration")
    private Integer estimatedDuration;
    
    @Column(name = "prerequisites")
    private String prerequisites;
    
    @Column(name = "learning_objectives", columnDefinition = "TEXT")
    private String learningObjectives;
    
    @Column(name = "icon_url")
    private String iconUrl;
    
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_published")
    private Boolean isPublished = false;
    
    @Column(name = "sort_order")
    private Integer sortOrder;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "learningModule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CodeExample> codeExamples = new ArrayList<>();
    
    public enum ModuleType {
        BASIC_SYNTAX, OOP, FUNCTIONAL_PROGRAMMING, 
        EXCEPTION_HANDLING, MODULES_PACKAGES, ADVANCED_FEATURES
    }
}