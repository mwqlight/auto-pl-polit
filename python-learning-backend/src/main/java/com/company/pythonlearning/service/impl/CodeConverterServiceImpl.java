package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;
import com.company.pythonlearning.service.CodeConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 代码转换器服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@Slf4j
public class CodeConverterServiceImpl implements CodeConverterService {
    
    // 简单的内存存储转换历史记录
    private final ConcurrentMap<Long, List<ConversionHistory>> userConversionHistory = new ConcurrentHashMap<>();
    
    @Override
    public CodeConversionResponse convertCode(CodeConversionRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("开始代码转换: 源语言={}, 目标语言={}", request.getSourceLanguage(), request.getTargetLanguage());
        
        try {
            String sourceCode = request.getSourceCode();
            String targetCode = convertJavaToPython(sourceCode, request.getConversionOptions());
            
            long executionTime = System.currentTimeMillis() - startTime;
            double conversionScore = calculateConversionScore(sourceCode, targetCode);
            
            // 保存转换历史
            saveConversionHistory(1L, sourceCode, targetCode, "java", "python", executionTime, conversionScore);
            
            return CodeConversionResponse.builder()
                    .success(true)
                    .convertedCode(targetCode)
                    .conversionScore(conversionScore)
                    .warnings(new ArrayList<>())
                    .errors(new ArrayList<>())
                    .executionTime(executionTime)
                    .sourceLanguage(request.getSourceLanguage())
                    .targetLanguage(request.getTargetLanguage())
                    .build();
                    
        } catch (Exception e) {
            log.error("代码转换失败", e);
            long executionTime = System.currentTimeMillis() - startTime;
            
            return CodeConversionResponse.builder()
                    .success(false)
                    .convertedCode("")
                    .conversionScore(0.0)
                    .warnings(new ArrayList<>())
                    .errors(List.of("转换失败: " + e.getMessage()))
                    .executionTime(executionTime)
                    .sourceLanguage(request.getSourceLanguage())
                    .targetLanguage(request.getTargetLanguage())
                    .build();
        }
    }
    
    @Override
    public List<ConversionHistoryResponse> getConversionHistory(Long userId, Integer limit) {
        List<ConversionHistory> historyList = userConversionHistory.getOrDefault(userId, new ArrayList<>());
        List<ConversionHistoryResponse> result = new ArrayList<>();
        
        int start = Math.max(0, historyList.size() - limit);
        for (int i = start; i < historyList.size(); i++) {
            ConversionHistory history = historyList.get(i);
            result.add(ConversionHistoryResponse.builder()
                    .id(history.getId())
                    .sourceCodePreview(previewCode(history.getSourceCode()))
                    .targetCodePreview(previewCode(history.getTargetCode()))
                    .sourceLanguage(history.getSourceLanguage())
                    .targetLanguage(history.getTargetLanguage())
                    .timestamp(history.getTimestamp())
                    .conversionScore(history.getConversionScore())
                    .executionTime(history.getExecutionTime())
                    .build());
        }
        
        return result;
    }
    
    @Override
    public void clearConversionHistory(Long userId) {
        userConversionHistory.remove(userId);
        log.info("清除用户转换历史: userId={}", userId);
    }
    
    @Override
    public ConversionStats getConversionStats(Long userId) {
        List<ConversionHistory> historyList = userConversionHistory.getOrDefault(userId, new ArrayList<>());
        
        long totalConversions = historyList.size();
        long successfulConversions = historyList.stream()
                .filter(h -> h.getConversionScore() > 0.5)
                .count();
        double successRate = totalConversions > 0 ? (double) successfulConversions / totalConversions * 100 : 0;
        long averageExecutionTime = totalConversions > 0 ? 
                historyList.stream().mapToLong(ConversionHistory::getExecutionTime).sum() / totalConversions : 0;
        
        return new ConversionStats() {
            @Override
            public long getTotalConversions() {
                return totalConversions;
            }
            
            @Override
            public long getSuccessfulConversions() {
                return successfulConversions;
            }
            
            @Override
            public double getSuccessRate() {
                return successRate;
            }
            
            @Override
            public long getAverageExecutionTime() {
                return averageExecutionTime;
            }
        };
    }
    
    /**
     * 将Java代码转换为Python代码
     */
    private String convertJavaToPython(String javaCode, CodeConversionRequest.ConversionOptions options) {
        // 简单的Java到Python转换逻辑（实际项目中可以使用更复杂的解析器）
        StringBuilder pythonCode = new StringBuilder();
        
        // 处理类定义
        String[] lines = javaCode.split("\\n");
        for (String line : lines) {
            String processedLine = line;
            
            // 移除public、private、protected等修饰符
            processedLine = processedLine.replaceAll("\\bpublic\\s+|\\bprivate\\s+|\\bprotected\\s+|\\bstatic\\s+|\\bfinal\\s+|\\bvoid\\s+", "");
            
            // 处理类定义
            if (processedLine.startsWith("class ")) {
                processedLine = processedLine.replace("{", ":");
            }
            
            // 处理方法定义
            if (processedLine.matches(".*\\(.*\\)\\s*\\{")) {
                processedLine = processedLine.replace("{", ":");
                // 添加self参数
                int parenIndex = processedLine.indexOf("(");
                if (parenIndex != -1 && !processedLine.contains("self")) {
                    processedLine = processedLine.substring(0, parenIndex + 1) + "self, " + processedLine.substring(parenIndex + 1);
                }
            }
            
            // 处理System.out.println
            processedLine = processedLine.replaceAll("System\\.out\\.println\\(", "print(");
            
            // 处理变量类型声明
            processedLine = processedLine.replaceAll("\\bint\\s+|\\bstring\\s+|\\bboolean\\s+|\\bdouble\\s+|\\bfloat\\s+|\\blong\\s+", "");
            
            // 处理for循环
            processedLine = processedLine.replaceAll("for\\s*\\(int\\s+([a-zA-Z0-9_]+)\\s*=\\s*([0-9]+)\\s*;\\s*\\1\\s*<\\s*([0-9]+)\\s*;\\s*\\1\\+\\+", "for \$1 in range(\$2, \$3)");
            
            // 处理数组声明
            processedLine = processedLine.replaceAll("\\[\\]", "[]");
            
            // 处理注释
            if (options.getIncludeComments() != null && !options.getIncludeComments()) {
                processedLine = processedLine.replaceAll("//.*$", "").trim();
            }
            
            // 处理大括号
            if (processedLine.trim().equals("}")) {
                // 移除闭合大括号，Python使用缩进
                continue;
            }
            
            if (!processedLine.trim().isEmpty()) {
                pythonCode.append(processedLine).append("\n");
            }
        }
        
        return pythonCode.toString();
    }
    
    /**
     * 计算转换分数
     */
    private double calculateConversionScore(String sourceCode, String targetCode) {
        if (sourceCode.isEmpty() || targetCode.isEmpty()) {
            return 0.0;
        }
        
        // 简单的相似度计算（实际项目中可以使用更复杂的算法）
        int sourceLines = sourceCode.split("\\n").length;
        int targetLines = targetCode.split("\\n").length;
        
        return Math.min(1.0, (double) targetLines / sourceLines * 0.8 + 0.2);
    }
    
    /**
     * 保存转换历史
     */
    private void saveConversionHistory(Long userId, String sourceCode, String targetCode, 
                                      String sourceLanguage, String targetLanguage, 
                                      long executionTime, double conversionScore) {
        List<ConversionHistory> historyList = userConversionHistory.computeIfAbsent(userId, k -> new ArrayList<>());
        
        ConversionHistory history = ConversionHistory.builder()
                .id(System.currentTimeMillis())
                .sourceCode(sourceCode)
                .targetCode(targetCode)
                .sourceLanguage(sourceLanguage)
                .targetLanguage(targetLanguage)
                .timestamp(LocalDateTime.now())
                .executionTime(executionTime)
                .conversionScore(conversionScore)
                .build();
        
        historyList.add(history);
        
        // 只保留最近100条记录
        if (historyList.size() > 100) {
            historyList.remove(0);
        }
    }
    
    /**
     * 生成代码预览
     */
    private String previewCode(String code) {
        if (code.isEmpty()) {
            return "";
        }
        
        String preview = code.replaceAll("\\s+", " ").trim();
        if (preview.length() > 100) {
            return preview.substring(0, 100) + "...";
        }
        return preview;
    }
    
    /**
     * 转换历史记录实体
     */
    @lombok.Data
    @lombok.Builder
    private static class ConversionHistory {
        private Long id;
        private String sourceCode;
        private String targetCode;
        private String sourceLanguage;
        private String targetLanguage;
        private LocalDateTime timestamp;
        private long executionTime;
        private double conversionScore;
    }
}