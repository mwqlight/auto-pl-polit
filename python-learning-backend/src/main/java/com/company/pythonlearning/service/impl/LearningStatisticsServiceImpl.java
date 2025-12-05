package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.dto.response.LearningStatisticsResponse;
import com.company.pythonlearning.entity.LearningStatistics;
import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.repository.LearningStatisticsRepository;
import com.company.pythonlearning.repository.LearningModuleRepository;
import com.company.pythonlearning.repository.LessonRepository;
import com.company.pythonlearning.repository.LearningProgressRepository;
import com.company.pythonlearning.service.LearningStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学习统计服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LearningStatisticsServiceImpl implements LearningStatisticsService {
    
    private final LearningStatisticsRepository learningStatisticsRepository;
    private final LearningModuleRepository learningModuleRepository;
    private final LessonRepository lessonRepository;
    private final LearningProgressRepository learningProgressRepository;
    
    @Override
    public LearningStatisticsResponse getUserLearningStatistics(Long userId) {
        LearningStatisticsResponse response = new LearningStatisticsResponse();
        
        // 获取今日统计
        LocalDate today = LocalDate.now();
        LearningStatistics todayStats = learningStatisticsRepository.findByUserIdAndStatDate(userId, today)
                .orElseGet(() -> createDailyStatistics(userId, today));
        response.setTodayStatistics(convertToDailyStatistics(todayStats));
        
        // 获取累计统计
        response.setTotalStatistics(getTotalStatistics(userId));
        
        // 获取最近7天统计
        LocalDate sevenDaysAgo = today.minusDays(6);
        List<LearningStatistics> weeklyStats = learningStatisticsRepository.getLast7DaysStatistics(userId, sevenDaysAgo, today);
        response.setWeeklyStatistics(weeklyStats.stream()
                .map(this::convertToDailyStatistics)
                .collect(Collectors.toList()));
        
        // 获取模块进度统计
        response.setModuleProgress(getUserModuleProgress(userId));
        
        return response;
    }
    
    @Override
    public List<LearningStatisticsResponse.DailyStatistics> getUserStatisticsByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<LearningStatistics> statsList = learningStatisticsRepository.findByUserIdAndStatDateBetween(userId, startDate, endDate);
        return statsList.stream()
                .map(this::convertToDailyStatistics)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LearningStatisticsResponse.ModuleProgress> getUserModuleProgress(Long userId) {
        List<LearningModule> modules = learningModuleRepository.findAll();
        List<LearningStatisticsResponse.ModuleProgress> moduleProgressList = new ArrayList<>();
        
        for (LearningModule module : modules) {
            LearningStatisticsResponse.ModuleProgress moduleProgress = new LearningStatisticsResponse.ModuleProgress();
            moduleProgress.setModuleId(module.getId());
            moduleProgress.setModuleName(module.getTitle());
            moduleProgress.setDifficulty(module.getDifficultyLevel().toString());
            
            // 获取模块下的课程总数
            long totalLessons = lessonRepository.countByLearningModuleId(module.getId());
            moduleProgress.setTotalLessons((int) totalLessons);
            
            // 获取已完成的课程数
            Double completionRate = learningProgressRepository.getModuleCompletionRate(userId, module.getId());
            long completedLessons = completionRate != null ? (long) (completionRate * totalLessons / 100) : 0;
            moduleProgress.setCompletedLessons((int) completedLessons);
            
            // 计算进度百分比
            int progressPercentage = totalLessons > 0 ? (int) (completedLessons * 100 / totalLessons) : 0;
            moduleProgress.setProgressPercentage(progressPercentage);
            
            moduleProgressList.add(moduleProgress);
        }
        
        return moduleProgressList;
    }
    
    @Override
    @Transactional
    public void updateLearningStatistics(Long userId, Integer studyDuration, Integer completedLessons, Integer completedModules, Integer codePractices, Integer points) {
        LocalDate today = LocalDate.now();
        LearningStatistics statistics = learningStatisticsRepository.findByUserIdAndStatDate(userId, today)
                .orElseGet(() -> createDailyStatistics(userId, today));
        
        // 更新今日统计
        if (studyDuration != null && studyDuration > 0) {
            statistics.setTodayStudyDuration(statistics.getTodayStudyDuration() + studyDuration);
            statistics.setTotalStudyDuration(statistics.getTotalStudyDuration() + studyDuration);
        }
        
        if (completedLessons != null && completedLessons > 0) {
            statistics.setTodayCompletedLessons(statistics.getTodayCompletedLessons() + completedLessons);
            statistics.setTotalCompletedLessons(statistics.getTotalCompletedLessons() + completedLessons);
        }
        
        if (completedModules != null && completedModules > 0) {
            statistics.setTodayCompletedModules(statistics.getTodayCompletedModules() + completedModules);
            statistics.setTotalCompletedModules(statistics.getTotalCompletedModules() + completedModules);
        }
        
        if (codePractices != null && codePractices > 0) {
            statistics.setTodayCodePractices(statistics.getTodayCodePractices() + codePractices);
            statistics.setTotalCodePractices(statistics.getTotalCodePractices() + codePractices);
        }
        
        if (points != null && points > 0) {
            statistics.setPoints(statistics.getPoints() + points);
        }
        
        // 更新连续学习天数
        updateConsecutiveDays(userId, statistics);
        
        learningStatisticsRepository.save(statistics);
        
        log.info("Updated learning statistics for user {}: duration={}, lessons={}, modules={}, practices={}, points={}",
                userId, studyDuration, completedLessons, completedModules, codePractices, points);
    }
    
    @Override
    public StatisticsSummary getStatisticsSummary(Long userId) {
        Integer totalStudyDuration = learningStatisticsRepository.getTotalStudyDuration(userId);
        Integer totalCompletedLessons = learningStatisticsRepository.getTotalCompletedLessons(userId);
        Integer totalCompletedModules = learningStatisticsRepository.getTotalCompletedModules(userId);
        Integer totalCodePractices = learningStatisticsRepository.getTotalCodePractices(userId);
        Integer totalPoints = learningStatisticsRepository.getTotalPoints(userId);
        Integer consecutiveDays = learningStatisticsRepository.getMaxConsecutiveDays(userId);
        Integer totalStudyDays = learningStatisticsRepository.getTotalStudyDays(userId);
        
        return new StatisticsSummary(
                totalStudyDuration != null ? totalStudyDuration : 0,
                totalCompletedLessons != null ? totalCompletedLessons : 0,
                totalCompletedModules != null ? totalCompletedModules : 0,
                totalCodePractices != null ? totalCodePractices : 0,
                totalPoints != null ? totalPoints : 0,
                consecutiveDays != null ? consecutiveDays : 0,
                totalStudyDays != null ? totalStudyDays : 0
        );
    }
    
    /**
     * 创建每日统计记录
     */
    private LearningStatistics createDailyStatistics(Long userId, LocalDate date) {
        LearningStatistics statistics = new LearningStatistics();
        statistics.setUserId(userId);
        statistics.setStatDate(date);
        
        // 初始化累计统计
        StatisticsSummary summary = getStatisticsSummary(userId);
        statistics.setTotalStudyDuration(summary.getTotalStudyDuration());
        statistics.setTotalCompletedLessons(summary.getTotalCompletedLessons());
        statistics.setTotalCompletedModules(summary.getTotalCompletedModules());
        statistics.setTotalCodePractices(summary.getTotalCodePractices());
        statistics.setPoints(summary.getTotalPoints());
        
        return statistics;
    }
    
    /**
     * 转换为每日统计DTO
     */
    private LearningStatisticsResponse.DailyStatistics convertToDailyStatistics(LearningStatistics statistics) {
        return new LearningStatisticsResponse.DailyStatistics(
                statistics.getStatDate(),
                statistics.getTodayStudyDuration(),
                statistics.getTodayCompletedLessons(),
                statistics.getTodayCompletedModules(),
                statistics.getTodayCodePractices(),
                statistics.getPoints()
        );
    }
    
    /**
     * 获取累计统计
     */
    private LearningStatisticsResponse.TotalStatistics getTotalStatistics(Long userId) {
        StatisticsSummary summary = getStatisticsSummary(userId);
        return new LearningStatisticsResponse.TotalStatistics(
                summary.getTotalStudyDuration(),
                summary.getTotalCompletedLessons(),
                summary.getTotalCompletedModules(),
                summary.getTotalCodePractices(),
                summary.getTotalPoints(),
                summary.getConsecutiveDays(),
                summary.getTotalStudyDays()
        );
    }
    
    /**
     * 更新连续学习天数
     */
    private void updateConsecutiveDays(Long userId, LearningStatistics currentStatistics) {
        LocalDate today = currentStatistics.getStatDate();
        LocalDate yesterday = today.minusDays(1);
        
        // 获取昨天的统计
        LearningStatistics yesterdayStats = learningStatisticsRepository.findByUserIdAndStatDate(userId, yesterday).orElse(null);
        
        if (yesterdayStats != null && yesterdayStats.getTodayStudyDuration() > 0) {
            // 昨天有学习记录，连续天数+1
            currentStatistics.setConsecutiveDays(yesterdayStats.getConsecutiveDays() + 1);
        } else {
            // 昨天没有学习记录，连续天数重置为1（今天有学习）
            currentStatistics.setConsecutiveDays(1);
        }
    }
}