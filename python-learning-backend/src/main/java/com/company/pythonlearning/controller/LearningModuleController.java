package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.enums.DifficultyLevel;
import com.company.pythonlearning.service.LearningModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学习模块控制器
 * 
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/learning-modules")
@RequiredArgsConstructor
@Slf4j
public class LearningModuleController {
    
    private final LearningModuleService learningModuleService;
    
    /**
     * 获取所有学习模块
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LearningModule>>> getAllModules() {
        try {
            List<LearningModule> modules = learningModuleService.getAllModules();
            return ResponseEntity.ok(ApiResponse.success("获取成功", modules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 获取已发布的学习模块
     */
    @GetMapping("/published")
    public ResponseEntity<ApiResponse<List<LearningModule>>> getPublishedModules() {
        try {
            List<LearningModule> modules = learningModuleService.getPublishedModules();
            return ResponseEntity.ok(ApiResponse.success("获取成功", modules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 根据ID获取学习模块
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LearningModule>> getModuleById(@PathVariable Long id) {
        try {
            LearningModule module = learningModuleService.getModuleById(id);
            return ResponseEntity.ok(ApiResponse.success("获取成功", module));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 创建学习模块（仅管理员）
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LearningModule>> createModule(@RequestBody LearningModule module) {
        try {
            LearningModule createdModule = learningModuleService.createModule(module);
            return ResponseEntity.ok(ApiResponse.success("创建成功", createdModule));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 更新学习模块（仅管理员）
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LearningModule>> updateModule(@PathVariable Long id, @RequestBody LearningModule module) {
        try {
            LearningModule updatedModule = learningModuleService.updateModule(id, module);
            return ResponseEntity.ok(ApiResponse.success("更新成功", updatedModule));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 删除学习模块（仅管理员）
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable Long id) {
        try {
            learningModuleService.deleteModule(id);
            return ResponseEntity.ok(ApiResponse.success("删除成功", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 发布学习模块（仅管理员）
     */
    @PostMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LearningModule>> publishModule(@PathVariable Long id) {
        try {
            LearningModule module = learningModuleService.publishModule(id);
            return ResponseEntity.ok(ApiResponse.success("发布成功", module));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 取消发布学习模块（仅管理员）
     */
    @PostMapping("/{id}/unpublish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LearningModule>> unpublishModule(@PathVariable Long id) {
        try {
            LearningModule module = learningModuleService.unpublishModule(id);
            return ResponseEntity.ok(ApiResponse.success("取消发布成功", module));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 根据难度级别获取学习模块
     */
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<List<LearningModule>>> getModulesByDifficulty(@PathVariable String difficulty) {
        try {
            DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(difficulty.toUpperCase());
            List<LearningModule> modules = learningModuleService.getModulesByDifficulty(difficultyLevel);
            return ResponseEntity.ok(ApiResponse.success("获取成功", modules));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("无效的难度级别: " + difficulty));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 搜索学习模块
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<LearningModule>>> searchModules(@RequestParam String keyword) {
        try {
            List<LearningModule> modules = learningModuleService.searchModules(keyword);
            return ResponseEntity.ok(ApiResponse.success("搜索成功", modules));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 获取学习模块统计信息（仅管理员）
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object[]>> getModuleStatistics() {
        try {
            Object[] statistics = learningModuleService.getModuleStatistics();
            return ResponseEntity.ok(ApiResponse.success("获取成功", statistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}