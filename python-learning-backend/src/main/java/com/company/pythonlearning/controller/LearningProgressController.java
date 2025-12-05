package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.request.UpdateLearningProgressRequest;
import com.company.pythonlearning.dto.response.LearningProgressResponse;
import com.company.pythonlearning.dto.response.LearningStatisticsResponse;
import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.service.LearningProgressService;
import com.company.pythonlearning.service.LearningStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 学习进度控制器
 * 
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/learning-progress")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "学习进度管理", description = "学习进度相关API接口")
public class LearningProgressController {
    
    private final LearningProgressService learningProgressService;
    private final LearningStatisticsService learningStatisticsService;
    
    @Operation(summary = "获取用户学习进度", description = "根据用户ID获取所有学习进度记录")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<LearningProgressResponse>>> getUserLearningProgress(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        log.info("获取用户学习进度: userId={}", userId);
        List<LearningProgressResponse> progressList = learningProgressService.getUserLearningProgress(userId);
        
        ApiResponse<List<LearningProgressResponse>> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("获取成功");
        response.setData(progressList);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取课程学习进度", description = "根据用户ID和课程ID获取特定课程的学习进度")
    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<ApiResponse<LearningProgressResponse>> getLessonLearningProgress(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "课程ID", required = true) @PathVariable Long lessonId) {
        
        log.info("获取课程学习进度: userId={}, lessonId={}", userId, lessonId);
        LearningProgressResponse progress = learningProgressService.getLessonLearningProgress(userId, lessonId);
        
        ApiResponse<LearningProgressResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("获取成功");
        response.setData(progress);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "更新学习进度", description = "更新用户的学习进度")
    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<LearningProgressResponse>> updateLearningProgress(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "学习进度更新请求", required = true) @Valid @RequestBody UpdateLearningProgressRequest request) {
        
        log.info("更新学习进度: request={}", request);
        LearningProgressResponse progress = learningProgressService.updateLearningProgress(userId, request);
        
        ApiResponse<LearningProgressResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("更新成功");
        response.setData(progress);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "标记课程完成", description = "标记用户完成特定课程")
    @PutMapping("/user/{userId}/lesson/{lessonId}/complete")
    public ResponseEntity<ApiResponse<LearningProgressResponse>> markLessonAsCompleted(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "课程ID", required = true) @PathVariable Long lessonId,
            @Parameter(description = "学习时长(分钟)", required = false) @RequestParam(required = false, defaultValue = "0") Integer studyDuration) {
        
        log.info("标记课程完成: userId={}, lessonId={}, studyDuration={}", userId, lessonId, studyDuration);
        LearningProgressResponse progress = learningProgressService.markLessonAsCompleted(userId, lessonId);
        
        ApiResponse<LearningProgressResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("标记成功");
        response.setData(progress);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "切换课程收藏状态", description = "切换用户对特定课程的收藏状态")
    @PutMapping("/user/{userId}/lesson/{lessonId}/favorite")
    public ResponseEntity<ApiResponse<LearningProgressResponse>> toggleLessonFavorite(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "课程ID", required = true) @PathVariable Long lessonId) {
        
        log.info("切换课程收藏状态: userId={}, lessonId={}", userId, lessonId);
        LearningProgressResponse progress = learningProgressService.toggleFavorite(userId, lessonId, true);
        
        ApiResponse<LearningProgressResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(progress);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取收藏课程", description = "获取用户收藏的所有课程")
    @GetMapping("/user/{userId}/favorites")
    public ResponseEntity<ApiResponse<List<LearningProgressResponse>>> getFavoriteLessons(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        log.info("获取收藏课程: userId={}", userId);
        List<LearningProgressResponse> favoriteLessons = learningProgressService.getUserFavoriteLessons(userId);
        
        ApiResponse<List<LearningProgressResponse>> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("获取成功");
        response.setData(favoriteLessons);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "记录代码练习", description = "记录用户的代码练习次数")
    @PostMapping("/user/{userId}/code-practice")
    public ResponseEntity<ApiResponse<Void>> recordCodePractice(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "课程ID", required = true) @RequestParam Long lessonId,
            @Parameter(description = "练习类型", required = true) @RequestParam String practiceType,
            @Parameter(description = "练习时长（分钟）", required = false) @RequestParam(required = false, defaultValue = "0") Integer duration,
            @Parameter(description = "获得积分", required = false) @RequestParam(required = false, defaultValue = "10") Integer points) {
        
        log.info("记录代码练习: userId={}, lessonId={}, practiceType={}, duration={}, points={}", userId, lessonId, practiceType, duration, points);
        learningProgressService.recordCodePractice(userId, lessonId, practiceType, duration, points);
        
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("记录成功");
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取用户学习统计", description = "获取用户的学习统计信息")
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<ApiResponse<LearningStatisticsResponse>> getUserLearningStatistics(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        log.info("获取用户学习统计: userId={}", userId);
        LearningStatisticsResponse statistics = learningStatisticsService.getUserLearningStatistics(userId);
        
        ApiResponse<LearningStatisticsResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("获取成功");
        response.setData(statistics);
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取模块完成率", description = "获取用户对特定模块的完成率")
    @GetMapping("/user/{userId}/module/{moduleId}/completion-rate")
    public ResponseEntity<ApiResponse<Integer>> getModuleCompletionRate(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "模块ID", required = true) @PathVariable Long moduleId) {
        
        log.info("获取模块完成率: userId={}, moduleId={}", userId, moduleId);
        Integer completionRate = learningProgressService.getModuleCompletionRate(userId, moduleId);
        
        ApiResponse<Integer> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("获取成功");
        response.setData(completionRate);
        
        return ResponseEntity.ok(response);
    }
}