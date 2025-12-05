package com.company.pythonlearning.service;

import com.company.pythonlearning.entity.Lesson;

import java.util.List;

/**
 * 课程服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LessonService {
    
    /**
     * 获取所有课程
     */
    List<Lesson> getAllLessons();
    
    /**
     * 根据模块ID获取课程
     */
    List<Lesson> getLessonsByModuleId(Long moduleId);
    
    /**
     * 根据ID获取课程
     */
    Lesson getLessonById(Long id);
    
    /**
     * 创建课程
     */
    Lesson createLesson(Lesson lesson);
    
    /**
     * 更新课程
     */
    Lesson updateLesson(Long id, Lesson lesson);
    
    /**
     * 删除课程
     */
    void deleteLesson(Long id);
    
    /**
     * 获取免费课程
     */
    List<Lesson> getFreeLessons();
    
    /**
     * 搜索课程
     */
    List<Lesson> searchLessons(String keyword);
    
    /**
     * 统计模块下的课程数量
     */
    long countLessonsByModuleId(Long moduleId);
    
    /**
     * 获取课程详情（包含关联的代码示例）
     */
    Lesson getLessonWithExamples(Long id);
    
    /**
     * 获取课程详情（包含关联的代码示例和练习）
     */
    Lesson getLessonDetail(Long id);
    
    /**
     * 发布课程
     */
    Lesson publishLesson(Long id);
    
    /**
     * 取消发布课程
     */
    Lesson unpublishLesson(Long id);
    
    /**
     * 获取课程统计信息
     */
    Object[] getLessonStatistics();
}