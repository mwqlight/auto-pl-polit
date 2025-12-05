package com.company.pythonlearning.repository;

import com.company.pythonlearning.entity.CodeExample;
import com.company.pythonlearning.enums.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeExampleRepository extends JpaRepository<CodeExample, Long> {
    
    /**
     * 根据模块ID查找代码示例
     */
    List<CodeExample> findByLearningModuleId(Long moduleId);
    
    /**
     * 根据模块ID和难度级别查找代码示例
     */
    List<CodeExample> findByLearningModuleIdAndDifficultyLevel(Long moduleId, DifficultyLevel difficultyLevel);
    
    /**
     * 根据模块ID和排序索引升序查找代码示例
     */
    List<CodeExample> findByLearningModuleIdOrderBySortOrderAsc(Long moduleId);
    
    /**
     * 根据难度级别查找代码示例
     */
    List<CodeExample> findByDifficultyLevel(DifficultyLevel difficultyLevel);
    
    /**
     * 统计模块下的代码示例数量
     */
    @Query("SELECT COUNT(ce) FROM CodeExample ce WHERE ce.learningModule.id = :moduleId")
    long countByLearningModuleId(@Param("moduleId") Long moduleId);
    
    /**
     * 根据难度级别统计代码示例数量
     */
    @Query("SELECT ce.difficultyLevel, COUNT(ce) FROM CodeExample ce GROUP BY ce.difficultyLevel")
    List<Object[]> countByDifficultyLevel();
    
    /**
     * 查找下一个执行顺序
     */
    @Query("SELECT COALESCE(MAX(ce.sortOrder), 0) + 1 FROM CodeExample ce WHERE ce.learningModule.id = :moduleId")
    int findNextSortOrder(@Param("moduleId") Long moduleId);
    
    /**
     * 根据模块ID列表查找代码示例
     */
    @Query("SELECT ce FROM CodeExample ce WHERE ce.learningModule.id IN :moduleIds ORDER BY ce.learningModule.id, ce.sortOrder")
    List<CodeExample> findByLearningModuleIds(@Param("moduleIds") List<Long> moduleIds);
}