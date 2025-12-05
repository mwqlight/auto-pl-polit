package com.company.pythonlearning.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * 学习进度响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningProgressResponse {
    
    /**
     * 进度ID
     */
    private Long id;
    
    /**
     * 课程ID
     */
    private Long lessonId;
    
    /**
     * 课程名称
     */
    private String lessonName;
    
    /**
     * 模块ID
     */
    private Long moduleId;
    
    /**
     * 模块名称
     */
    private String moduleName;
    
    /**
     * 学习状态
     */
    private String status;
    
    /**
     * 学习进度百分比（0-100）
     */
    private Integer progressPercentage;
    
    /**
     * 已学习时长（分钟）
     */
    private Integer studyDuration;
    
    /**
     * 最后学习时间
     */
    private LocalDateTime lastStudyTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime completionTime;
    
    /**
     * 是否收藏
     */
    private Boolean isFavorite;
    
    /**
     * 课程难度
     */
    private String difficulty;
    
    /**
     * 课程时长（分钟）
     */
    private Integer lessonDuration;
}