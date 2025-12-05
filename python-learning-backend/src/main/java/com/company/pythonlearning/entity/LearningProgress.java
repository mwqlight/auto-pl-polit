package com.company.pythonlearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 学习进度实体类
 * 
 * @author Company
 * @version 1.0.0
 */
@Entity
@Table(name = "learning_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 课程ID
     */
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;
    
    /**
     * 模块ID
     */
    @Column(name = "module_id", nullable = false)
    private Long moduleId;
    
    /**
     * 学习状态：未开始、进行中、已完成
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    /**
     * 学习进度百分比（0-100）
     */
    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage;
    
    /**
     * 已学习时长（分钟）
     */
    @Column(name = "study_duration")
    private Integer studyDuration;
    
    /**
     * 最后学习时间
     */
    @Column(name = "last_study_time")
    private LocalDateTime lastStudyTime;
    
    /**
     * 完成时间
     */
    @Column(name = "completion_time")
    private LocalDateTime completionTime;
    
    /**
     * 是否收藏
     */
    @Column(name = "is_favorite")
    private Boolean isFavorite;
    
    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
    
    /**
     * 课程关联
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", insertable = false, updatable = false)
    private Lesson lesson;
    
    /**
     * 模块关联
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", insertable = false, updatable = false)
    private LearningModule learningModule;
    
    /**
     * 学习进度状态枚举
     */
    public enum Status {
        NOT_STARTED("not_started"),
        IN_PROGRESS("in_progress"),
        COMPLETED("completed");
        
        private final String value;
        
        Status(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    /**
     * 预持久化回调
     */
    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
        if (progressPercentage == null) {
            progressPercentage = 0;
        }
        if (status == null) {
            status = Status.NOT_STARTED.getValue();
        }
        if (isFavorite == null) {
            isFavorite = false;
        }
    }
    
    /**
     * 预更新回调
     */
    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}