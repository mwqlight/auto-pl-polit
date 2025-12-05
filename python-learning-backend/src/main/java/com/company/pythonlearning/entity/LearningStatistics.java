package com.company.pythonlearning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学习统计实体类
 * 
 * @author Company
 * @version 1.0.0
 */
@Entity
@Table(name = "learning_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningStatistics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 统计日期
     */
    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;
    
    /**
     * 今日学习时长（分钟）
     */
    @Column(name = "today_study_duration")
    private Integer todayStudyDuration;
    
    /**
     * 今日完成课程数
     */
    @Column(name = "today_completed_lessons")
    private Integer todayCompletedLessons;
    
    /**
     * 今日完成模块数
     */
    @Column(name = "today_completed_modules")
    private Integer todayCompletedModules;
    
    /**
     * 今日代码练习次数
     */
    @Column(name = "today_code_practices")
    private Integer todayCodePractices;
    
    /**
     * 累计学习时长（分钟）
     */
    @Column(name = "total_study_duration")
    private Integer totalStudyDuration;
    
    /**
     * 累计完成课程数
     */
    @Column(name = "total_completed_lessons")
    private Integer totalCompletedLessons;
    
    /**
     * 累计完成模块数
     */
    @Column(name = "total_completed_modules")
    private Integer totalCompletedModules;
    
    /**
     * 累计代码练习次数
     */
    @Column(name = "total_code_practices")
    private Integer totalCodePractices;
    
    /**
     * 连续学习天数
     */
    @Column(name = "consecutive_days")
    private Integer consecutiveDays;
    
    /**
     * 学习积分
     */
    @Column(name = "points")
    private Integer points;
    
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
     * 复合主键约束：用户ID + 统计日期
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningStatistics that = (LearningStatistics) o;
        return userId.equals(that.userId) && statDate.equals(that.statDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(userId, statDate);
    }
    
    /**
     * 预持久化回调
     */
    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
        if (todayStudyDuration == null) todayStudyDuration = 0;
        if (todayCompletedLessons == null) todayCompletedLessons = 0;
        if (todayCompletedModules == null) todayCompletedModules = 0;
        if (todayCodePractices == null) todayCodePractices = 0;
        if (totalStudyDuration == null) totalStudyDuration = 0;
        if (totalCompletedLessons == null) totalCompletedLessons = 0;
        if (totalCompletedModules == null) totalCompletedModules = 0;
        if (totalCodePractices == null) totalCodePractices = 0;
        if (consecutiveDays == null) consecutiveDays = 0;
        if (points == null) points = 0;
    }
    
    /**
     * 预更新回调
     */
    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}