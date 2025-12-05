package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.dto.request.UpdateLearningProgressRequest;
import com.company.pythonlearning.dto.response.LearningProgressResponse;
import com.company.pythonlearning.entity.LearningProgress;
import com.company.pythonlearning.entity.Lesson;
import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.repository.LearningProgressRepository;
import com.company.pythonlearning.repository.LessonRepository;
import com.company.pythonlearning.repository.LearningModuleRepository;
import com.company.pythonlearning.service.LearningProgressService;
import com.company.pythonlearning.service.LearningStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学习进度服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LearningProgressServiceImpl implements LearningProgressService {
    
    private final LearningProgressRepository learningProgressRepository;
    private final LessonRepository lessonRepository;
    private final LearningModuleRepository learningModuleRepository;
    private final LearningStatisticsService learningStatisticsService;
    
    @Override
    public List<LearningProgressResponse> getUserLearningProgress(Long userId) {
        List<LearningProgress> progressList = learningProgressRepository.findByUserId(userId);
        return progressList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LearningProgressResponse> getUserProgressByModule(Long userId, Long moduleId) {
        List<LearningProgress> progressList = learningProgressRepository.findByUserIdAndModuleId(userId, moduleId);
        return progressList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public LearningProgressResponse getLessonLearningProgress(Long userId, Long lessonId) {
        LearningProgress progress = learningProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> createInitialProgress(userId, lessonId));
        return convertToResponse(progress);
    }
    
    @Override
    public Integer getModuleCompletionRate(Long userId, Long moduleId) {
        // TODO: 实现模块完成率计算逻辑
        return 0;
    }
    
    @Override
    @Transactional
    public LearningProgressResponse updateLearningProgress(Long userId, UpdateLearningProgressRequest request) {
        LearningProgress progress = learningProgressRepository.findByUserIdAndLessonId(userId, request.getLessonId())
                .orElseGet(() -> createInitialProgress(userId, request.getLessonId()));
        
        // 更新学习进度
        progress.setProgressPercentage(request.getProgressPercentage());
        
        // 更新学习状态
        if (request.getProgressPercentage() >= 100) {
            progress.setStatus(LearningProgress.Status.COMPLETED.getValue());
            if (progress.getCompletionTime() == null) {
                progress.setCompletionTime(LocalDateTime.now());
            }
        } else if (request.getProgressPercentage() > 0) {
            progress.setStatus(LearningProgress.Status.IN_PROGRESS.getValue());
        }
        
        // 更新学习时长
        if (request.getStudyDuration() != null) {
            Integer currentDuration = progress.getStudyDuration() != null ? progress.getStudyDuration() : 0;
            progress.setStudyDuration(currentDuration + request.getStudyDuration());
        }
        
        progress.setLastStudyTime(LocalDateTime.now());
        learningProgressRepository.save(progress);
        
        // 更新学习统计
        int studyDuration = request.getStudyDuration() != null ? request.getStudyDuration() : 0;
        int completedLessons = (request.getProgressPercentage() >= 100) ? 1 : 0;
        learningStatisticsService.updateLearningStatistics(userId, studyDuration, completedLessons, 0, 0, calculatePoints(progress));
        
        return convertToResponse(progress);
    }
    
    @Override
    @Transactional
    public LearningProgressResponse markLessonAsCompleted(Long userId, Long lessonId) {
        LearningProgress progress = learningProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> createInitialProgress(userId, lessonId));
        
        progress.setProgressPercentage(100);
        progress.setStatus(LearningProgress.Status.COMPLETED.getValue());
        progress.setCompletionTime(LocalDateTime.now());
        progress.setLastStudyTime(LocalDateTime.now());
        
        // 如果没有学习时长，设置默认值
        if (progress.getStudyDuration() == null || progress.getStudyDuration() == 0) {
            Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
            if (lesson != null && lesson.getDurationMinutes() != null) {
                progress.setStudyDuration(lesson.getDurationMinutes());
            } else {
                progress.setStudyDuration(30); // 默认30分钟
            }
        }
        
        learningProgressRepository.save(progress);
        
        // 更新学习统计
        learningStatisticsService.updateLearningStatistics(userId, progress.getStudyDuration(), 1, 0, 0, calculatePoints(progress));
        
        return convertToResponse(progress);
    }
    
    @Override
    @Transactional
    public LearningProgressResponse toggleFavorite(Long userId, Long lessonId, Boolean favorite) {
        LearningProgress progress = learningProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> createInitialProgress(userId, lessonId));
        
        progress.setIsFavorite(favorite);
        progress.setLastStudyTime(LocalDateTime.now());
        
        learningProgressRepository.save(progress);
        
        return convertToResponse(progress);
    }
    
    @Override
    public List<LearningProgressResponse> getUserFavoriteLessons(Long userId) {
        List<LearningProgress> progressList = learningProgressRepository.findByUserIdAndIsFavoriteTrue(userId);
        return progressList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public LearningStatisticsService.StatisticsSummary getUserLearningStatistics(Long userId) {
        return learningStatisticsService.getStatisticsSummary(userId);
    }
    
    @Override
    @Transactional
    public void recordCodePractice(Long userId, Long lessonId, String practiceType, Integer duration, Integer points) {
        LearningProgress progress = learningProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> createInitialProgress(userId, lessonId));
        
        // 更新学习时长
        if (duration != null && duration > 0) {
            Integer currentDuration = progress.getStudyDuration() != null ? progress.getStudyDuration() : 0;
            progress.setStudyDuration(currentDuration + duration);
        }
        
        progress.setLastStudyTime(LocalDateTime.now());
        learningProgressRepository.save(progress);
        
        // 更新学习统计
        learningStatisticsService.updateLearningStatistics(userId, duration, 0, 0, 1, points);
        
        log.info("Recorded code practice for user {}: lesson={}, type={}, duration={}min, points={}", 
                userId, lessonId, practiceType, duration, points);
    }
    
    /**
     * 创建初始学习进度
     */
    private LearningProgress createInitialProgress(Long userId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("课程不存在: " + lessonId));
        
        LearningProgress progress = new LearningProgress();
        progress.setUserId(userId);
        progress.setLessonId(lessonId);
        progress.setModuleId(lesson.getLearningModule().getId());
        progress.setStatus(LearningProgress.Status.NOT_STARTED.getValue());
        progress.setProgressPercentage(0);
        progress.setStudyDuration(0);
        progress.setLastStudyTime(LocalDateTime.now());
        progress.setIsFavorite(false);
        
        return learningProgressRepository.save(progress);
    }
    
    /**
     * 转换为响应对象
     */
    private LearningProgressResponse convertToResponse(LearningProgress progress) {
        LearningProgressResponse response = new LearningProgressResponse();
        response.setId(progress.getId());
        response.setLessonId(progress.getLessonId());
        response.setModuleId(progress.getModuleId());
        response.setStatus(progress.getStatus());
        response.setProgressPercentage(progress.getProgressPercentage());
        response.setStudyDuration(progress.getStudyDuration());
        response.setLastStudyTime(progress.getLastStudyTime());
        response.setCompletionTime(progress.getCompletionTime());
        response.setIsFavorite(progress.getIsFavorite());
        
        // 设置课程信息
        if (progress.getLesson() != null) {
            response.setLessonName(progress.getLesson().getTitle());
            response.setDifficulty(progress.getLesson().getDifficultyLevel().toString());
            response.setLessonDuration(progress.getLesson().getDurationMinutes());
        }
        
        // 设置模块信息
        if (progress.getLearningModule() != null) {
            response.setModuleName(progress.getLearningModule().getTitle());
        }
        
        return response;
    }
    
    /**
     * 计算获得的积分
     */
    private Integer calculatePoints(LearningProgress progress) {
        int points = 0;
        
        // 完成课程获得积分
        if (LearningProgress.Status.COMPLETED.getValue().equals(progress.getStatus())) {
            points += 100; // 基础积分
            
            // 根据难度额外加分
            Lesson lesson = lessonRepository.findById(progress.getLessonId()).orElse(null);
            if (lesson != null) {
                switch (lesson.getDifficultyLevel().toString()) {
                    case "beginner":
                        points += 20;
                        break;
                    case "intermediate":
                        points += 50;
                        break;
                    case "advanced":
                        points += 100;
                        break;
                }
            }
        }
        
        return points;
    }
}