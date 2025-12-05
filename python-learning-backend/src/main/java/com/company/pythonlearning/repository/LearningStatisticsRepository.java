package com.company.pythonlearning.repository;

import com.company.pythonlearning.entity.LearningStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 学习统计数据访问接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LearningStatisticsRepository extends JpaRepository<LearningStatistics, Long> {
    
    /**
     * 根据用户ID和统计日期查找学习统计
     * 
     * @param userId 用户ID
     * @param statDate 统计日期
     * @return 学习统计
     */
    Optional<LearningStatistics> findByUserIdAndStatDate(Long userId, LocalDate statDate);
    
    /**
     * 根据用户ID查找指定日期范围内的学习统计
     * 
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 学习统计列表
     */
    List<LearningStatistics> findByUserIdAndStatDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据用户ID查找所有学习统计
     * 
     * @param userId 用户ID
     * @return 学习统计列表
     */
    List<LearningStatistics> findByUserIdOrderByStatDateDesc(Long userId);
    
    /**
     * 获取用户的总学习时长
     * 
     * @param userId 用户ID
     * @return 总学习时长（分钟）
     */
    @Query("SELECT COALESCE(SUM(ls.totalStudyDuration), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getTotalStudyDuration(@Param("userId") Long userId);
    
    /**
     * 获取用户的总完成课程数
     * 
     * @param userId 用户ID
     * @return 总完成课程数
     */
    @Query("SELECT COALESCE(SUM(ls.totalCompletedLessons), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getTotalCompletedLessons(@Param("userId") Long userId);
    
    /**
     * 获取用户的总完成模块数
     * 
     * @param userId 用户ID
     * @return 总完成模块数
     */
    @Query("SELECT COALESCE(SUM(ls.totalCompletedModules), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getTotalCompletedModules(@Param("userId") Long userId);
    
    /**
     * 获取用户的总代码练习次数
     * 
     * @param userId 用户ID
     * @return 总代码练习次数
     */
    @Query("SELECT COALESCE(SUM(ls.totalCodePractices), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getTotalCodePractices(@Param("userId") Long userId);
    
    /**
     * 获取用户的总积分
     * 
     * @param userId 用户ID
     * @return 总积分
     */
    @Query("SELECT COALESCE(SUM(ls.points), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getTotalPoints(@Param("userId") Long userId);
    
    /**
     * 获取用户的总学习天数
     * 
     * @param userId 用户ID
     * @return 总学习天数
     */
    @Query("SELECT COUNT(DISTINCT ls.statDate) FROM LearningStatistics ls WHERE ls.userId = :userId AND ls.todayStudyDuration > 0")
    Integer getTotalStudyDays(@Param("userId") Long userId);
    
    /**
     * 获取用户的连续学习天数
     * 
     * @param userId 用户ID
     * @return 连续学习天数
     */
    @Query("SELECT COALESCE(MAX(ls.consecutiveDays), 0) FROM LearningStatistics ls WHERE ls.userId = :userId")
    Integer getMaxConsecutiveDays(@Param("userId") Long userId);
    
    /**
     * 获取用户最近7天的学习统计
     * 
     * @param userId 用户ID
     * @param endDate 结束日期
     * @return 最近7天的学习统计
     */
    @Query("SELECT ls FROM LearningStatistics ls WHERE ls.userId = :userId AND ls.statDate BETWEEN :startDate AND :endDate ORDER BY ls.statDate ASC")
    List<LearningStatistics> getLast7DaysStatistics(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}