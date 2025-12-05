package com.company.pythonlearning.service;

import com.company.pythonlearning.dto.request.UpdateLearningProgressRequest;
import com.company.pythonlearning.dto.response.LearningProgressResponse;
import com.company.pythonlearning.entity.LearningProgress;
import java.util.List;

/**
 * 学习进度服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LearningProgressService {
    
    /**
     * 获取用户的学习进度
     * 
     * @param userId 用户ID
     * @return 学习进度列表
     */
    List<LearningProgressResponse> getUserLearningProgress(Long userId);
    
    /**
     * 获取用户在特定模块的学习进度
     * 
     * @param userId   用户ID
     * @param moduleId 模块ID
     * @return 学习进度列表
     */
    List<LearningProgressResponse> getUserProgressByModule(Long userId, Long moduleId);
    
    /**
     * 获取用户对特定课程的学习进度
     * 
     * @param userId   用户ID
     * @param lessonId 课程ID
     * @return 学习进度
     */
    LearningProgressResponse getLessonLearningProgress(Long userId, Long lessonId);
    
    /**
     * 获取用户对特定模块的完成率
     * 
     * @param userId   用户ID
     * @param moduleId 模块ID
     * @return 完成率（0-100）
     */
    Integer getModuleCompletionRate(Long userId, Long moduleId);
    
    /**
     * 更新学习进度
     * 
     * @param userId 用户ID
     * @param request 更新请求
     * @return 更新后的学习进度
     */
    LearningProgressResponse updateLearningProgress(Long userId, UpdateLearningProgressRequest request);
    
    /**
     * 标记课程为已完成
     * 
     * @param userId   用户ID
     * @param lessonId 课程ID
     * @return 学习进度
     */
    LearningProgressResponse markLessonAsCompleted(Long userId, Long lessonId);
    
    /**
     * 标记课程为收藏/取消收藏
     * 
     * @param userId   用户ID
     * @param lessonId 课程ID
     * @param favorite 是否收藏
     * @return 学习进度
     */
    LearningProgressResponse toggleFavorite(Long userId, Long lessonId, Boolean favorite);
    
    /**
     * 获取用户的收藏课程
     * 
     * @param userId 用户ID
     * @return 收藏课程列表
     */
    List<LearningProgressResponse> getUserFavoriteLessons(Long userId);
    
    /**
     * 获取用户的学习统计
     * 
     * @param userId 用户ID
     * @return 学习统计
     */
    LearningStatisticsService.StatisticsSummary getUserLearningStatistics(Long userId);
    
    /**
     * 记录代码练习
     * 
     * @param userId 用户ID
     * @param lessonId 课程ID
     * @param practiceType 练习类型
     * @param duration 练习时长（分钟）
     * @param points 获得积分
     */
    void recordCodePractice(Long userId, Long lessonId, String practiceType, Integer duration, Integer points);
}