package com.company.pythonlearning.repository;

import com.company.pythonlearning.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    /**
     * 根据模块ID查找课程
     */
    List<Lesson> findByLearningModuleId(Long moduleId);
    
    /**
     * 根据模块ID和排序索引升序查找课程
     */
    List<Lesson> findByLearningModuleIdOrderBySortOrderAsc(Long moduleId);
    
    /**
     * 根据模块ID和是否免费查找课程
     */
    List<Lesson> findByLearningModuleIdAndIsFree(Long moduleId, Boolean isFree);
    
    /**
     * 根据标题模糊搜索课程
     */
    List<Lesson> findByTitleContainingIgnoreCase(String title);
    
    /**
     * 统计模块下的课程数量
     */
    @Query("SELECT COUNT(l) FROM Lesson l WHERE l.learningModule.id = :moduleId")
    long countByLearningModuleId(@Param("moduleId") Long moduleId);
    
    /**
     * 查找模块下的下一个排序索引
     */
    @Query("SELECT COALESCE(MAX(l.sortOrder), 0) + 1 FROM Lesson l WHERE l.learningModule.id = :moduleId")
    int findNextSortOrderByModuleId(@Param("moduleId") Long moduleId);
    
    /**
     * 根据模块ID列表查找课程
     */
    @Query("SELECT l FROM Lesson l WHERE l.learningModule.id IN :moduleIds ORDER BY l.learningModule.id, l.sortOrder")
    List<Lesson> findByLearningModuleIds(@Param("moduleIds") List<Long> moduleIds);
    
    /**
     * 查找免费课程
     */
    List<Lesson> findByIsFreeTrue();
    
    /**
     * 统计免费课程数量
     */
    @Query("SELECT COUNT(l) FROM Lesson l WHERE l.isFree = true")
    long countFreeLessons();
    
    /**
     * 统计免费课程数量（使用Spring Data JPA命名约定）
     */
    long countByIsFreeTrue();
    
    /**
     * 统计已发布课程数量
     */
    long countByIsPublishedTrue();
}