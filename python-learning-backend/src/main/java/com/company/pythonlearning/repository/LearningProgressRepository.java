package com.company.pythonlearning.repository;

import com.company.pythonlearning.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * 学习进度数据访问接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {
    
    /**
     * 根据用户ID查找学习进度
     * 
     * @param userId 用户ID
     * @return 学习进度列表
     */
    List<LearningProgress> findByUserId(Long userId);
    
    /**
     * 根据用户ID和模块ID查找学习进度
     * 
     * @param userId   用户ID
     * @param moduleId 模块ID
     * @return 学习进度列表
     */
    List<LearningProgress> findByUserIdAndModuleId(Long userId, Long moduleId);
    
    /**
     * 根据用户ID和课程ID查找学习进度
     * 
     * @param userId   用户ID
     * @param lessonId 课程ID
     * @return 学习进度
     */
    Optional<LearningProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
    
    /**
     * 根据用户ID查找收藏的课程
     * 
     * @param userId 用户ID
     * @return 学习进度列表
     */
    List<LearningProgress> findByUserIdAndIsFavoriteTrue(Long userId);
    
    /**
     * 根据用户ID和状态查找学习进度
     * 
     * @param userId 用户ID
     * @param status 学习状态
     * @return 学习进度列表
     */
    List<LearningProgress> findByUserIdAndStatus(Long userId, String status);
    
    /**
     * 获取用户已完成的课程数量
     * 
     * @param userId 用户ID
     * @return 已完成课程数量
     */
    @Query("SELECT COUNT(*) FROM LearningProgress lp WHERE lp.userId = :userId AND lp.status = 'completed'")
    Long countCompletedLessonsByUserId(@Param("userId") Long userId);
    
    /**
     * 获取用户的总学习时长
     * 
     * @param userId 用户ID
     * @return 总学习时长（分钟）
     */
    @Query("SELECT COALESCE(SUM(lp.studyDuration), 0) FROM LearningProgress lp WHERE lp.userId = :userId")
    Integer getTotalStudyDurationByUserId(@Param("userId") Long userId);
    
    /**
     * 获取用户在特定模块的完成进度
     * 
     * @param userId   用户ID
     * @param moduleId 模块ID
     * @return 完成进度百分比
     */
    @Query("SELECT COALESCE((COUNT(lp) * 100.0) / (SELECT COUNT(l) FROM Lesson l WHERE l.learningModule.id = :moduleId), 0) FROM LearningProgress lp WHERE lp.userId = :userId AND lp.moduleId = :moduleId AND lp.status = 'completed'")
    Double getModuleCompletionRate(@Param("userId") Long userId, @Param("moduleId") Long moduleId);
}