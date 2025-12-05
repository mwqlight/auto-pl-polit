package com.company.pythonlearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.company.pythonlearning.enums.DifficultyLevel;

/**
 * 代码示例实体类
 * 
 * @author Company
 * @version 1.0.0
 */
@Entity
@Table(name = "code_examples")
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeExample {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private LearningModule learningModule;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "java_code", columnDefinition = "TEXT", nullable = false)
    private String javaCode;
    
    @Column(name = "python_code", columnDefinition = "TEXT", nullable = false)
    private String pythonCode;
    
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @Column(name = "difficulty_level")
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    
    @Column(name = "execution_order")
    private Integer sortOrder = 0;
}