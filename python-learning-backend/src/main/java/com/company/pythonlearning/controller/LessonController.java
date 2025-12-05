package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.entity.Lesson;
import com.company.pythonlearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程控制器
 * 
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    
    private final LessonService lessonService;
    
    /**
     * 获取所有课程
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Lesson>>> getAllLessons() {
        try {
            List<Lesson> lessons = lessonService.getAllLessons();
            return ResponseEntity.ok(ApiResponse.success("获取成功", lessons));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 根据模块ID获取课程
     */
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<ApiResponse<List<Lesson>>> getLessonsByModuleId(@PathVariable Long moduleId) {
        try {
            List<Lesson> lessons = lessonService.getLessonsByModuleId(moduleId);
            return ResponseEntity.ok(ApiResponse.success("获取成功", lessons));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 根据ID获取课程
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Lesson>> getLessonById(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.getLessonById(id);
            return ResponseEntity.ok(ApiResponse.success("获取成功", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 创建课程（仅管理员）
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Lesson>> createLesson(@RequestBody Lesson lesson) {
        try {
            Lesson createdLesson = lessonService.createLesson(lesson);
            return ResponseEntity.ok(ApiResponse.success("创建成功", createdLesson));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 更新课程（仅管理员）
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Lesson>> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        try {
            Lesson updatedLesson = lessonService.updateLesson(id, lesson);
            return ResponseEntity.ok(ApiResponse.success("更新成功", updatedLesson));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 删除课程（仅管理员）
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok(ApiResponse.success("删除成功", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 获取免费课程
     */
    @GetMapping("/free")
    public ResponseEntity<ApiResponse<List<Lesson>>> getFreeLessons() {
        try {
            List<Lesson> lessons = lessonService.getFreeLessons();
            return ResponseEntity.ok(ApiResponse.success("获取成功", lessons));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 搜索课程
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Lesson>>> searchLessons(@RequestParam String keyword) {
        try {
            List<Lesson> lessons = lessonService.searchLessons(keyword);
            return ResponseEntity.ok(ApiResponse.success("搜索成功", lessons));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 获取课程统计信息（仅管理员）
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object[]>> getLessonStatistics() {
        try {
            Object[] statistics = lessonService.getLessonStatistics();
            return ResponseEntity.ok(ApiResponse.success("获取成功", statistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 获取课程详情（包含代码示例）
     */
    @GetMapping("/{id}/detail")
    public ResponseEntity<ApiResponse<Lesson>> getLessonDetail(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.getLessonDetail(id);
            return ResponseEntity.ok(ApiResponse.success("获取成功", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 发布课程（仅管理员）
     */
    @PostMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Lesson>> publishLesson(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.publishLesson(id);
            return ResponseEntity.ok(ApiResponse.success("发布成功", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 取消发布课程（仅管理员）
     */
    @PostMapping("/{id}/unpublish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Lesson>> unpublishLesson(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.unpublishLesson(id);
            return ResponseEntity.ok(ApiResponse.success("取消发布成功", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}