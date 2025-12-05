package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;
import com.company.pythonlearning.dto.response.ConversionStatsResponse;
import com.company.pythonlearning.service.CodeConverterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码转换控制器
 * 
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/converter")
@RequiredArgsConstructor
@Slf4j
public class CodeConverterController {
    
    private final CodeConverterService codeConverterService;
    
    /**
     * 将Java代码转换为Python代码
     * 
     * @param request 代码转换请求
     * @return 转换结果
     */
    @PostMapping("/java-to-python")
    public ResponseEntity<ApiResponse<CodeConversionResponse>> convertJavaToPython(
            @Valid @RequestBody CodeConversionRequest request) {
        log.info("收到Java转Python代码转换请求");
        try {
            CodeConversionResponse response = codeConverterService.convertJavaToPython(request);
            return ResponseEntity.ok(ApiResponse.success("代码转换成功", response));
        } catch (Exception e) {
            log.error("代码转换失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("代码转换失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取转换历史记录
     * 
     * @param page 页码（默认1）
     * @param size 每页大小（默认10）
     * @return 转换历史记录列表
     */
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ConversionHistoryResponse>>> getConversionHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("获取转换历史记录，页码: {}, 每页大小: {}", page, size);
        try {
            List<ConversionHistoryResponse> history = codeConverterService.getConversionHistory(page, size);
            return ResponseEntity.ok(ApiResponse.success("获取历史记录成功", history));
        } catch (Exception e) {
            log.error("获取转换历史记录失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("获取历史记录失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取转换详情
     * 
     * @param id 转换记录ID
     * @return 转换详情
     */
    @GetMapping("/history/{id}")
    public ResponseEntity<ApiResponse<ConversionHistoryResponse>> getConversionDetail(
            @PathVariable Long id) {
        log.info("获取转换详情，ID: {}", id);
        try {
            ConversionHistoryResponse detail = codeConverterService.getConversionDetail(id);
            if (detail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("转换记录不存在"));
            }
            return ResponseEntity.ok(ApiResponse.success("获取转换详情成功", detail));
        } catch (Exception e) {
            log.error("获取转换详情失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("获取转换详情失败: " + e.getMessage()));
        }
    }
    
    /**
     * 删除转换历史记录
     * 
     * @param id 转换记录ID
     * @return 删除结果
     */
    @DeleteMapping("/history/{id}")
    public ResponseEntity<ApiResponse<String>> deleteConversionHistory(
            @PathVariable Long id) {
        log.info("删除转换历史记录，ID: {}", id);
        try {
            codeConverterService.deleteConversionHistory(id);
            return ResponseEntity.ok(ApiResponse.success("删除记录成功"));
        } catch (Exception e) {
            log.error("删除转换历史记录失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("删除记录失败: " + e.getMessage()));
        }
    }
    
    /**
     * 清空转换历史记录
     * 
     * @return 清空结果
     */
    @DeleteMapping("/history")
    public ResponseEntity<ApiResponse<String>> clearConversionHistory() {
        log.info("清空转换历史记录");
        try {
            codeConverterService.clearConversionHistory();
            return ResponseEntity.ok(ApiResponse.success("清空历史记录成功"));
        } catch (Exception e) {
            log.error("清空转换历史记录失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("清空历史记录失败: " + e.getMessage()));
        }
    }
    
    /**
     * 获取转换统计信息
     * 
     * @return 转换统计信息
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ConversionStatsResponse>> getConversionStats() {
        log.info("获取转换统计信息");
        try {
            ConversionStatsResponse stats = codeConverterService.getConversionStats();
            return ResponseEntity.ok(ApiResponse.success("获取统计信息成功", stats));
        } catch (Exception e) {
            log.error("获取转换统计信息失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("获取统计信息失败: " + e.getMessage()));
        }
    }
    
    /**
     * 验证Java代码
     * 
     * @param code Java代码
     * @return 验证结果
     */
    @PostMapping("/validate/java")
    public ResponseEntity<ApiResponse<List<CodeConversionResponse.ConversionWarning>>> validateJavaCode(
            @RequestBody String code) {
        log.info("验证Java代码");
        try {
            List<CodeConversionResponse.ConversionWarning> warnings = codeConverterService.validateJavaCode(code);
            return ResponseEntity.ok(ApiResponse.success("代码验证成功", warnings));
        } catch (Exception e) {
            log.error("Java代码验证失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("代码验证失败: " + e.getMessage()));
        }
    }
    
    /**
     * 验证Python代码
     * 
     * @param code Python代码
     * @return 验证结果
     */
    @PostMapping("/validate/python")
    public ResponseEntity<ApiResponse<List<CodeConversionResponse.ConversionWarning>>> validatePythonCode(
            @RequestBody String code) {
        log.info("验证Python代码");
        try {
            List<CodeConversionResponse.ConversionWarning> warnings = codeConverterService.validatePythonCode(code);
            return ResponseEntity.ok(ApiResponse.success("代码验证成功", warnings));
        } catch (Exception e) {
            log.error("Python代码验证失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("代码验证失败: " + e.getMessage()));
        }
    }
}
