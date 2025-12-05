package com.company.pythonlearning.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 更新学习进度请求DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLearningProgressRequest {
    
    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空")
    private Long lessonId;
    
    /**
     * 模块ID
     */
    @NotNull(message = "模块ID不能为空")
    private Long moduleId;
    
    /**
     * 学习进度百分比（0-100）
     */
    @NotNull(message = "学习进度不能为空")
    @Min(value = 0, message = "学习进度不能小于0")
    @Max(value = 100, message = "学习进度不能大于100")
    private Integer progressPercentage;
    
    /**
     * 学习时长（分钟）
     */
    @Min(value = 0, message = "学习时长不能小于0")
    private Integer studyDuration;
    
    /**
     * 是否完成
     */
    private Boolean completed;
    
    /**
     * 是否收藏
     */
    private Boolean isFavorite;
}