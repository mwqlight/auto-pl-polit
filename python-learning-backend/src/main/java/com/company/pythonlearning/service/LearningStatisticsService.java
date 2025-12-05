package com.company.pythonlearning.service;

import com.company.pythonlearning.dto.response.LearningStatisticsResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * 学习统计服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LearningStatisticsService {
    
    /**
     * 获取用户的学习统计
     * 
     * @param userId 用户ID
     * @return 学习统计响应
     */
    LearningStatisticsResponse getUserLearningStatistics(Long userId);
    
    /**
     * 获取用户指定日期范围的学习统计
     * 
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 每日统计列表
     */
    List<LearningStatisticsResponse.DailyStatistics> getUserStatisticsByDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 获取用户的模块学习进度统计
     * 
     * @param userId 用户ID
     * @return 模块进度列表
     */
    List<LearningStatisticsResponse.ModuleProgress> getUserModuleProgress(Long userId);
    
    /**
     * 更新用户的学习统计
     * 
     * @param userId 用户ID
     * @param studyDuration 学习时长（分钟）
     * @param completedLessons 完成课程数
     * @param completedModules 完成模块数
     * @param codePractices 代码练习次数
     * @param points 获得积分
     */
    void updateLearningStatistics(Long userId, Integer studyDuration, Integer completedLessons, Integer completedModules, Integer codePractices, Integer points);
    
    /**
     * 获取用户的学习统计摘要
     * 
     * @param userId 用户ID
     * @return 统计摘要
     */
    StatisticsSummary getStatisticsSummary(Long userId);
    
    /**
     * 统计摘要内部类
     */
    class StatisticsSummary {
        private Integer totalStudyDuration;
        private Integer totalCompletedLessons;
        private Integer totalCompletedModules;
        private Integer totalCodePractices;
        private Integer totalPoints;
        private Integer consecutiveDays;
        private Integer totalStudyDays;
        
        // 构造函数、getter和setter
        public StatisticsSummary() {}
        
        public StatisticsSummary(Integer totalStudyDuration, Integer totalCompletedLessons, Integer totalCompletedModules, 
                               Integer totalCodePractices, Integer totalPoints, Integer consecutiveDays, Integer totalStudyDays) {
            this.totalStudyDuration = totalStudyDuration;
            this.totalCompletedLessons = totalCompletedLessons;
            this.totalCompletedModules = totalCompletedModules;
            this.totalCodePractices = totalCodePractices;
            this.totalPoints = totalPoints;
            this.consecutiveDays = consecutiveDays;
            this.totalStudyDays = totalStudyDays;
        }
        
        // Getter和Setter方法
        public Integer getTotalStudyDuration() { return totalStudyDuration; }
        public void setTotalStudyDuration(Integer totalStudyDuration) { this.totalStudyDuration = totalStudyDuration; }
        public Integer getTotalCompletedLessons() { return totalCompletedLessons; }
        public void setTotalCompletedLessons(Integer totalCompletedLessons) { this.totalCompletedLessons = totalCompletedLessons; }
        public Integer getTotalCompletedModules() { return totalCompletedModules; }
        public void setTotalCompletedModules(Integer totalCompletedModules) { this.totalCompletedModules = totalCompletedModules; }
        public Integer getTotalCodePractices() { return totalCodePractices; }
        public void setTotalCodePractices(Integer totalCodePractices) { this.totalCodePractices = totalCodePractices; }
        public Integer getTotalPoints() { return totalPoints; }
        public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
        public Integer getConsecutiveDays() { return consecutiveDays; }
        public void setConsecutiveDays(Integer consecutiveDays) { this.consecutiveDays = consecutiveDays; }
        public Integer getTotalStudyDays() { return totalStudyDays; }
        public void setTotalStudyDays(Integer totalStudyDays) { this.totalStudyDays = totalStudyDays; }
    }
}