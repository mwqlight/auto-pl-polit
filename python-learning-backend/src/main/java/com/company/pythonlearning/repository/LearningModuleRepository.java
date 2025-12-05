package com.company.pythonlearning.repository;

import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.enums.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningModuleRepository extends JpaRepository<LearningModule, Long> {
    
    /**
     * 查找已发布的学习模块
     */
    List<LearningModule> findByIsPublishedTrue();
    
    /**
     * 根据难度级别查找模块
     */
    List<LearningModule> findByDifficultyLevel(DifficultyLevel difficultyLevel);
    
    /**
     * 根据难度级别和发布状态查找模块
     */
    List<LearningModule> findByDifficultyLevelAndIsPublished(DifficultyLevel difficultyLevel, Boolean isPublished);
    
    /**
     * 根据排序索引升序查找模块
     */
    List<LearningModule> findByIsPublishedTrueOrderBySortOrderAsc();
    
    /**
     * 根据标题模糊搜索模块
     */
    List<LearningModule> findByTitleContainingIgnoreCase(String title);
    
    /**
     * 统计已发布模块数量
     */
    @Query("SELECT COUNT(m) FROM LearningModule m WHERE m.isPublished = true")
    long countPublishedModules();
    
    /**
     * 查找下一个排序索引
     */
    @Query("SELECT COALESCE(MAX(m.sortOrder), 0) + 1 FROM LearningModule m")
    int findNextSortOrder();
    
    /**
     * 根据难度级别统计模块数量
     */
    @Query("SELECT m.difficultyLevel, COUNT(m) FROM LearningModule m WHERE m.isPublished = true GROUP BY m.difficultyLevel")
    List<Object[]> countModulesByDifficulty();
}