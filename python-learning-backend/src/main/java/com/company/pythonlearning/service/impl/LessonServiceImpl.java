package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.entity.Lesson;
import com.company.pythonlearning.repository.LessonRepository;
import com.company.pythonlearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    
    private final LessonRepository lessonRepository;
    
    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
    
    @Override
    public List<Lesson> getLessonsByModuleId(Long moduleId) {
        return lessonRepository.findByLearningModuleIdOrderBySortOrderAsc(moduleId);
    }
    
    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("课程不存在: " + id));
    }
    
    @Override
    @Transactional
    public Lesson createLesson(Lesson lesson) {
        // 设置排序索引
        lesson.setSortOrder(lessonRepository.findNextSortOrderByModuleId(lesson.getLearningModule().getId()));
        
        // 设置默认值
        if (lesson.getIsFree() == null) {
            lesson.setIsFree(true);
        }
        if (lesson.getDurationMinutes() == null) {
            lesson.setDurationMinutes(15);
        }
        
        return lessonRepository.save(lesson);
    }
    
    @Override
    @Transactional
    public Lesson updateLesson(Long id, Lesson lesson) {
        Lesson existingLesson = getLessonById(id);
        
        // 更新允许修改的字段
        if (lesson.getTitle() != null) {
            existingLesson.setTitle(lesson.getTitle());
        }
        if (lesson.getDescription() != null) {
            existingLesson.setDescription(lesson.getDescription());
        }
        if (lesson.getContent() != null) {
            existingLesson.setContent(lesson.getContent());
        }
        if (lesson.getVideoUrl() != null) {
            existingLesson.setVideoUrl(lesson.getVideoUrl());
        }
        if (lesson.getDurationMinutes() != null) {
            existingLesson.setDurationMinutes(lesson.getDurationMinutes());
        }
        if (lesson.getSortOrder() != null) {
            existingLesson.setSortOrder(lesson.getSortOrder());
        }
        if (lesson.getIsFree() != null) {
            existingLesson.setIsFree(lesson.getIsFree());
        }
        if (lesson.getPrerequisites() != null) {
            existingLesson.setPrerequisites(lesson.getPrerequisites());
        }
        if (lesson.getLearningObjectives() != null) {
            existingLesson.setLearningObjectives(lesson.getLearningObjectives());
        }
        
        return lessonRepository.save(existingLesson);
    }
    
    @Override
    @Transactional
    public void deleteLesson(Long id) {
        Lesson lesson = getLessonById(id);
        lessonRepository.delete(lesson);
        log.info("删除课程: {}", lesson.getTitle());
    }
    
    @Override
    public List<Lesson> getFreeLessons() {
        return lessonRepository.findByIsFreeTrue();
    }
    
    @Override
    public List<Lesson> searchLessons(String keyword) {
        return lessonRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    @Override
    public long countLessonsByModuleId(Long moduleId) {
        return lessonRepository.countByLearningModuleId(moduleId);
    }
    
    @Override
    public Lesson getLessonWithExamples(Long id) {
        // 这里需要实现获取课程及其关联的代码示例
        // 暂时返回课程基本信息
        return getLessonById(id);
    }
    
    @Override
    public Lesson getLessonDetail(Long id) {
        Lesson lesson = getLessonById(id);
        // 这里可以添加获取关联代码示例和练习的逻辑
        return lesson;
    }
    
    @Override
    @Transactional
    public Lesson publishLesson(Long id) {
        Lesson lesson = getLessonById(id);
        lesson.setIsPublished(true);
        return lessonRepository.save(lesson);
    }
    
    @Override
    @Transactional
    public Lesson unpublishLesson(Long id) {
        Lesson lesson = getLessonById(id);
        lesson.setIsPublished(false);
        return lessonRepository.save(lesson);
    }
    
    @Override
    public Object[] getLessonStatistics() {
        long totalLessons = lessonRepository.count();
        long freeLessons = lessonRepository.countByIsFreeTrue();
        long publishedLessons = lessonRepository.countByIsPublishedTrue();
        
        return new Object[]{
            totalLessons,
            freeLessons,
            publishedLessons
        };
    }
}