package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;
import com.company.pythonlearning.service.CodeConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码转换器控制器
 * 
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/converter")
@RequiredArgsConstructor
@Tag(name = "代码转换器", description = "Java到Python代码转换相关接口")
public class CodeConverterController {
    
    private final CodeConverterService codeConverterService;
    
    @PostMapping("/convert")
    @Operation(summary = "转换代码", description = "将Java代码转换为Python代码")
    public ResponseEntity<ApiResponse<CodeConversionResponse>> convertCode(
            @Valid @RequestBody CodeConversionRequest request) {
        try {
            CodeConversionResponse response = codeConverterService.convertCode(request);
            return ResponseEntity.ok(ApiResponse.success("转换成功", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("转换失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/history")
    @Operation(summary = "获取转换历史", description = "获取用户的代码转换历史记录")
    public ResponseEntity<ApiResponse<List<ConversionHistoryResponse>>> getConversionHistory(
            @RequestParam(defaultValue = "1") Long userId,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<ConversionHistoryResponse> history = codeConverterService.getConversionHistory(userId, limit);
            return ResponseEntity.ok(ApiResponse.success("获取成功", history));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("获取失败: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/history")
    @Operation(summary = "清除转换历史", description = "清除用户的代码转换历史记录")
    public ResponseEntity<ApiResponse<Void>> clearConversionHistory(
            @RequestParam(defaultValue = "1") Long userId) {
        try {
            codeConverterService.clearConversionHistory(userId);
            return ResponseEntity.ok(ApiResponse.success("清除成功", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("清除失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/stats")
    @Operation(summary = "获取转换统计", description = "获取用户的代码转换统计信息")
    public ResponseEntity<ApiResponse<CodeConverterService.ConversionStats>> getConversionStats(
            @RequestParam(defaultValue = "1") Long userId) {
        try {
            CodeConverterService.ConversionStats stats = codeConverterService.getConversionStats(userId);
            return ResponseEntity.ok(ApiResponse.success("获取成功", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("获取失败: " + e.getMessage()));
        }
    }
}