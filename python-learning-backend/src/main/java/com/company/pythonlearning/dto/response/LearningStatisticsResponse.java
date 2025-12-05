package com.company.pythonlearning.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * 学习统计响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningStatisticsResponse {
    
    /**
     * 今日统计
     */
    private DailyStatistics todayStatistics;
    
    /**
     * 累计统计
     */
    private TotalStatistics totalStatistics;
    
    /**
     * 最近7天统计
     */
    private List<DailyStatistics> weeklyStatistics;
    
    /**
     * 模块进度统计
     */
    private List<ModuleProgress> moduleProgress;
    
    /**
     * 每日统计子DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyStatistics {
        
        /**
         * 统计日期
         */
        private LocalDate date;
        
        /**
         * 学习时长（分钟）
         */
        private Integer studyDuration;
        
        /**
         * 完成课程数
         */
        private Integer completedLessons;
        
        /**
         * 完成模块数
         */
        private Integer completedModules;
        
        /**
         * 代码练习次数
         */
        private Integer codePractices;
        
        /**
         * 获得积分
         */
        private Integer points;
    }
    
    /**
     * 累计统计子DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalStatistics {
        
        /**
         * 累计学习时长（分钟）
         */
        private Integer totalStudyDuration;
        
        /**
         * 累计完成课程数
         */
        private Integer totalCompletedLessons;
        
        /**
         * 累计完成模块数
         */
        private Integer totalCompletedModules;
        
        /**
         * 累计代码练习次数
         */
        private Integer totalCodePractices;
        
        /**
         * 总积分
         */
        private Integer totalPoints;
        
        /**
         * 连续学习天数
         */
        private Integer consecutiveDays;
        
        /**
         * 总学习天数
         */
        private Integer totalStudyDays;
    }
    
    /**
     * 模块进度子DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuleProgress {
        
        /**
         * 模块ID
         */
        private Long moduleId;
        
        /**
         * 模块名称
         */
        private String moduleName;
        
        /**
         * 总课程数
         */
        private Integer totalLessons;
        
        /**
         * 已完成课程数
         */
        private Integer completedLessons;
        
        /**
         * 进度百分比
         */
        private Integer progressPercentage;
        
        /**
         * 模块难度
         */
        private String difficulty;
    }
}