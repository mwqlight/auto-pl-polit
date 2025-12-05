package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;
import com.company.pythonlearning.dto.response.ConversionStatsResponse;
import com.company.pythonlearning.service.CodeConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 代码转换服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@Slf4j
public class CodeConverterServiceImpl implements CodeConverterService {
    
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private final ConcurrentHashMap<Long, ConversionHistoryResponse> conversionHistory = new ConcurrentHashMap<>();
    
    @Override
    public CodeConversionResponse convertJavaToPython(CodeConversionRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            CodeConversionResponse response = new CodeConversionResponse();
            response.setConversionType(request.getConversionType());
            
            switch (request.getConversionType()) {
                case "code":
                    convertDirectCode(request, response);
                    break;
                case "file":
                    convertSingleFile(request, response);
                    break;
                case "directory":
                    convertDirectory(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的转换类型: " + request.getConversionType());
            }
            
            long conversionTime = System.currentTimeMillis() - startTime;
            response.setConversionTime(conversionTime);
            
            // 保存转换历史
            saveConversionHistory(request, response);
            
            log.info("Java to Python conversion completed in {}ms", conversionTime);
            return response;
        } catch (Exception e) {
            log.error("Code conversion failed", e);
            throw new RuntimeException("代码转换失败: " + e.getMessage());
        }
    }
    
    private void convertDirectCode(CodeConversionRequest request, CodeConversionResponse response) {
        if (request.getSourceCode() == null || request.getSourceCode().trim().isEmpty()) {
            throw new IllegalArgumentException("源代码不能为空");
        }
        
        String pythonCode = convertJavaCodeToPython(request.getSourceCode(), request.getConversionOptions());
        response.setTargetCode(pythonCode);
        response.setWarnings(new ArrayList<>());
        response.setSuggestions(new ArrayList<>());
        
        // 设置转换摘要
        CodeConversionResponse.ConversionSummary summary = new CodeConversionResponse.ConversionSummary();
        summary.setTotalFiles(1);
        summary.setConvertedFiles(1);
        summary.setFailedFiles(0);
        summary.setTotalLines((long) request.getSourceCode().split("\\n").length);
        summary.setConvertedLines((long) pythonCode.split("\\n").length);
        summary.setSuccessRate(100.0);
        response.setSummary(summary);
    }
    
    private void convertSingleFile(CodeConversionRequest request, CodeConversionResponse response) {
        if (request.getSourceFile() == null) {
            throw new IllegalArgumentException("源文件不能为空");
        }
        
        CodeConversionRequest.SourceFile sourceFile = request.getSourceFile();
        String pythonCode = convertJavaCodeToPython(sourceFile.getFileContent(), request.getConversionOptions());
        
        CodeConversionResponse.TargetFile targetFile = new CodeConversionResponse.TargetFile();
        targetFile.setFileName(sourceFile.getFileName().replace(".java", ".py"));
        targetFile.setFileContent(pythonCode);
        targetFile.setFileType("python");
        targetFile.setRelativePath(sourceFile.getRelativePath());
        targetFile.setFileWarnings(new ArrayList<>());
        targetFile.setFileSuggestions(new ArrayList<>());
        
        response.setTargetFile(targetFile);
        response.setWarnings(new ArrayList<>());
        response.setSuggestions(new ArrayList<>());
        
        // 设置转换摘要
        CodeConversionResponse.ConversionSummary summary = new CodeConversionResponse.ConversionSummary();
        summary.setTotalFiles(1);
        summary.setConvertedFiles(1);
        summary.setFailedFiles(0);
        summary.setTotalLines((long) sourceFile.getFileContent().split("\\n").length);
        summary.setConvertedLines((long) pythonCode.split("\\n").length);
        summary.setSuccessRate(100.0);
        response.setSummary(summary);
    }
    
    private void convertDirectory(CodeConversionRequest request, CodeConversionResponse response) {
        if (request.getSourceDirectory() == null || request.getSourceDirectory().isEmpty()) {
            throw new IllegalArgumentException("源目录不能为空");
        }
        
        List<CodeConversionResponse.TargetFile> targetFiles = new ArrayList<>();
        List<CodeConversionResponse.ConversionWarning> allWarnings = new ArrayList<>();
        List<CodeConversionResponse.ConversionSuggestion> allSuggestions = new ArrayList<>();
        
        int totalFiles = request.getSourceDirectory().size();
        int convertedFiles = 0;
        int failedFiles = 0;
        long totalLines = 0;
        long convertedLines = 0;
        
        for (CodeConversionRequest.SourceFile sourceFile : request.getSourceDirectory()) {
            try {
                if (sourceFile.getFileName().endsWith(".java")) {
                    String pythonCode = convertJavaCodeToPython(sourceFile.getFileContent(), request.getConversionOptions());
                    
                    CodeConversionResponse.TargetFile targetFile = new CodeConversionResponse.TargetFile();
                    targetFile.setFileName(sourceFile.getFileName().replace(".java", ".py"));
                    targetFile.setFileContent(pythonCode);
                    targetFile.setFileType("python");
                    targetFile.setRelativePath(sourceFile.getRelativePath());
                    targetFile.setFileWarnings(new ArrayList<>());
                    targetFile.setFileSuggestions(new ArrayList<>());
                    
                    targetFiles.add(targetFile);
                    convertedFiles++;
                    convertedLines += pythonCode.split("\\n").length;
                } else {
                    // 非Java文件直接复制
                    CodeConversionResponse.TargetFile targetFile = new CodeConversionResponse.TargetFile();
                    targetFile.setFileName(sourceFile.getFileName());
                    targetFile.setFileContent(sourceFile.getFileContent());
                    targetFile.setFileType(sourceFile.getFileType());
                    targetFile.setRelativePath(sourceFile.getRelativePath());
                    targetFile.setFileWarnings(new ArrayList<>());
                    targetFile.setFileSuggestions(new ArrayList<>());
                    
                    targetFiles.add(targetFile);
                    convertedFiles++;
                    convertedLines += sourceFile.getFileContent().split("\\n").length;
                }
            } catch (Exception e) {
                failedFiles++;
                CodeConversionResponse.ConversionWarning warning = new CodeConversionResponse.ConversionWarning();
                warning.setMessage("文件转换失败: " + e.getMessage());
                warning.setFileName(sourceFile.getFileName());
                warning.setSeverity("error");
                warning.setType("conversion_error");
                allWarnings.add(warning);
                
                log.error("Failed to convert file: {}", sourceFile.getFileName(), e);
            }
            
            totalLines += sourceFile.getFileContent().split("\\n").length;
        }
        
        response.setTargetDirectory(targetFiles);
        response.setWarnings(allWarnings);
        response.setSuggestions(allSuggestions);
        
        // 设置转换摘要
        CodeConversionResponse.ConversionSummary summary = new CodeConversionResponse.ConversionSummary();
        summary.setTotalFiles(totalFiles);
        summary.setConvertedFiles(convertedFiles);
        summary.setFailedFiles(failedFiles);
        summary.setTotalLines(totalLines);
        summary.setConvertedLines(convertedLines);
        summary.setSuccessRate(totalFiles > 0 ? (double) convertedFiles / totalFiles * 100 : 0.0);
        response.setSummary(summary);
    }
    
    @Override
    public List<ConversionHistoryResponse> getConversionHistory(int page, int size) {
        List<ConversionHistoryResponse> historyList = new ArrayList<>(conversionHistory.values());
        historyList.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = (page - 1) * size;
        int end = Math.min(start + size, historyList.size());
        
        return historyList.subList(start, end);
    }
    
    @Override
    public ConversionHistoryResponse getConversionDetail(Long id) {
        return conversionHistory.get(id);
    }
    
    @Override
    public void deleteConversionHistory(Long id) {
        conversionHistory.remove(id);
    }
    
    @Override
    public void clearConversionHistory() {
        conversionHistory.clear();
    }
    
    @Override
    public ConversionStatsResponse getConversionStats() {
        ConversionStatsResponse stats = new ConversionStatsResponse();
        stats.setTotalConversions((long) conversionHistory.size());
        stats.setSuccessfulConversions((long) conversionHistory.size());
        
        if (!conversionHistory.isEmpty()) {
            double avgTime = conversionHistory.values().stream()
                .mapToLong(ConversionHistoryResponse::getConversionTime)
                .average()
                .orElse(0.0);
            stats.setAverageConversionTime(avgTime);
        }
        
        stats.setMostCommonSourceLanguage("java");
        stats.setMostCommonTargetLanguage("python");
        stats.setTotalCodeLinesConverted(0L);
        stats.setActiveUsers(1);
        stats.setThisWeekConversions((long) conversionHistory.size());
        stats.setThisMonthConversions((long) conversionHistory.size());
        
        return stats;
    }
    
    @Override
    public List<CodeConversionResponse.ConversionWarning> validateJavaCode(String code) {
        List<CodeConversionResponse.ConversionWarning> warnings = new ArrayList<>();
        
        // 简单的Java代码验证
        if (code.contains("public static void main")) {
            CodeConversionResponse.ConversionWarning warning = new CodeConversionResponse.ConversionWarning();
            warning.setMessage("检测到main方法，转换后可能需要手动调整入口点");
            warning.setLineNumber(findLineNumber(code, "public static void main"));
            warning.setSeverity("INFO");
            warning.setType("ENTRY_POINT");
            warnings.add(warning);
        }
        
        return warnings;
    }
    
    @Override
    public List<CodeConversionResponse.ConversionWarning> validatePythonCode(String code) {
        List<CodeConversionResponse.ConversionWarning> warnings = new ArrayList<>();
        
        // 简单的Python代码验证
        if (code.contains("print(")) {
            CodeConversionResponse.ConversionWarning warning = new CodeConversionResponse.ConversionWarning();
            warning.setMessage("检测到print语句，在Python 3中是函数形式");
            warning.setLineNumber(findLineNumber(code, "print("));
            warning.setSeverity("INFO");
            warning.setType("PRINT_STATEMENT");
            warnings.add(warning);
        }
        
        return warnings;
    }
    
    private String convertJavaCodeToPython(String javaCode, CodeConversionRequest.ConversionOptions options) {
        if (javaCode == null || javaCode.trim().isEmpty()) {
            return "";
        }
        
        StringBuilder pythonCode = new StringBuilder();
        String[] lines = javaCode.split("\\n");
        int indentLevel = 0;
        boolean inComment = false;
        
        // 添加转换头部信息
        if (options != null && options.getIncludeComments()) {
            pythonCode.append("# Converted from Java code\n");
            pythonCode.append("# Conversion options: ").append(options.toString()).append("\n\n");
        }
        
        for (String line : lines) {
            String trimmedLine = line.trim();
            
            // 处理多行注释
            if (trimmedLine.contains("/*")) {
                inComment = true;
            }
            
            if (inComment) {
                if (options != null && options.getIncludeComments()) {
                    pythonCode.append("    ".repeat(indentLevel)).append("# ").append(trimmedLine).append("\n");
                }
                if (trimmedLine.contains("*/")) {
                    inComment = false;
                }
                continue;
            }
            
            // 处理单行注释
            if (trimmedLine.startsWith("//")) {
                if (options != null && options.getIncludeComments()) {
                    pythonCode.append("    ".repeat(indentLevel)).append(trimmedLine.replace("//", "# ")).append("\n");
                }
                continue;
            }
            
            // 处理空行
            if (trimmedLine.isEmpty()) {
                pythonCode.append("\n");
                continue;
            }
            
            // 处理大括号
            if (trimmedLine.endsWith("{")) {
                // 处理类定义
                if (trimmedLine.startsWith("class ") || trimmedLine.startsWith("public class ") || 
                    trimmedLine.startsWith("private class ") || trimmedLine.startsWith("protected class ")) {
                    String className = extractClassName(trimmedLine);
                    pythonCode.append("    ".repeat(indentLevel)).append("class ").append(className).append(":\n");
                }
                // 处理方法定义
                else if (trimmedLine.contains("(")) {
                    String methodName = extractMethodName(trimmedLine);
                    pythonCode.append("    ".repeat(indentLevel)).append("def ").append(methodName).append(":\n");
                }
                // 处理其他块
                else {
                    pythonCode.append("    ".repeat(indentLevel)).append("# Block start\n");
                }
                indentLevel++;
            }
            else if (trimmedLine.equals("}")) {
                indentLevel--;
                if (indentLevel < 0) indentLevel = 0;
            }
            // 处理方法调用
            else if (trimmedLine.endsWith(";")) {
                String pythonStatement = convertJavaStatementToPython(trimmedLine, options);
                pythonCode.append("    ".repeat(indentLevel)).append(pythonStatement).append("\n");
            }
            // 处理其他语句
            else {
                String pythonStatement = convertJavaStatementToPython(trimmedLine, options);
                pythonCode.append("    ".repeat(indentLevel)).append(pythonStatement).append("\n");
            }
        }
        
        return pythonCode.toString();
    }
    
    private String extractClassName(String line) {
        String[] parts = line.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("class")) {
                return parts[i + 1].replace("{", "");
            }
        }
        return "UnknownClass";
    }
    
    private String extractMethodName(String line) {
        int startIndex = line.indexOf("(");
        int endIndex = line.lastIndexOf("(");
        
        // 找到方法名的起始位置
        int methodStart = line.lastIndexOf(" ", startIndex) + 1;
        
        // 提取方法名
        String methodName = line.substring(methodStart, endIndex);
        
        // 转换为snake_case命名规范
        return toSnakeCase(methodName);
    }
    
    private String toSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }
        
        StringBuilder snakeCase = new StringBuilder();
        snakeCase.append(Character.toLowerCase(camelCase.charAt(0)));
        
        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                snakeCase.append('_').append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        
        return snakeCase.toString();
    }
    
    private String convertJavaStatementToPython(String javaStatement, CodeConversionRequest.ConversionOptions options) {
        String statement = javaStatement.replace(";", "");
        
        // 转换System.out.println
        if (statement.startsWith("System.out.println")) {
            int start = statement.indexOf("(") + 1;
            int end = statement.lastIndexOf(")");
            String content = statement.substring(start, end);
            return "print(" + content + ")";
        }
        
        // 转换变量声明
        if (statement.contains("=")) {
            String[] parts = statement.split("=");
            String leftPart = parts[0].trim();
            String rightPart = parts[1].trim();
            
            // 移除类型声明
            String variableName = leftPart;
            if (leftPart.contains(" ")) {
                variableName = leftPart.substring(leftPart.lastIndexOf(" ") + 1);
            }
            
            // 转换命名规范
            if (options != null && "snake_case".equals(options.getNamingConvention())) {
                variableName = toSnakeCase(variableName);
            }
            
            return variableName + " = " + rightPart;
        }
        
        // 转换return语句
        if (statement.startsWith("return")) {
            return statement;
        }
        
        // 转换if语句
        if (statement.startsWith("if (")) {
            return statement.replace("if (", "if ").replace(")", ":");
        }
        
        // 转换for语句
        if (statement.startsWith("for (")) {
            return convertForLoop(statement);
        }
        
        // 转换while语句
        if (statement.startsWith("while (")) {
            return statement.replace("while (", "while ").replace(")", ":");
        }
        
        // 默认返回原语句
        return statement;
    }
    
    private String convertForLoop(String javaFor) {
        // 处理for (int i = 0; i < 10; i++) 格式
        if (javaFor.contains("int ") && javaFor.contains("=")) {
            int start = javaFor.indexOf("(") + 1;
            int end = javaFor.indexOf(")");
            String forContent = javaFor.substring(start, end);
            
            String[] parts = forContent.split("; ");
            if (parts.length == 3) {
                String initialization = parts[0];
                String condition = parts[1];
                String increment = parts[2];
                
                // 提取变量名
                String variable = initialization.substring(initialization.indexOf(" ") + 1, initialization.indexOf("="));
                
                // 转换为Python的for循环
                return "for " + variable + " in range(" + extractRange(condition) + "):";
            }
        }
        
        // 处理for (Object obj : collection) 格式
        if (javaFor.contains(":")) {
            int start = javaFor.indexOf("(") + 1;
            int end = javaFor.indexOf(")");
            String forContent = javaFor.substring(start, end);
            
            String[] parts = forContent.split(":");
            if (parts.length == 2) {
                String variablePart = parts[0].trim();
                String collectionPart = parts[1].trim();
                
                // 提取变量名
                String variable = variablePart.substring(variablePart.lastIndexOf(" ") + 1);
                
                return "for " + variable + " in " + collectionPart + ":";
            }
        }
        
        return "# For loop conversion not supported: " + javaFor;
    }
    
    private String extractRange(String condition) {
        // 处理 i < 10 格式
        if (condition.contains("<")) {
            String[] parts = condition.split("<");
            return parts[1].trim();
        }
        // 处理 i <= 10 格式
        else if (condition.contains("<=")) {
            String[] parts = condition.split("<=");
            return String.valueOf(Integer.parseInt(parts[1].trim()) + 1);
        }
        // 处理 i > 0 格式
        else if (condition.contains(">")) {
            String[] parts = condition.split(">");
            return "0, " + parts[1].trim() + ", -1";
        }
        
        return "10";
    }
    
    private void saveConversionHistory(CodeConversionRequest request, CodeConversionResponse response) {
        ConversionHistoryResponse history = new ConversionHistoryResponse();
        history.setId(ID_GENERATOR.getAndIncrement());
        history.setSourceCode(request.getSourceCode());
        history.setTargetCode(response.getTargetCode());
        history.setSourceLanguage(request.getSourceLanguage());
        history.setTargetLanguage(request.getTargetLanguage());
        history.setConversionTime(response.getConversionTime());
        history.setSourceCodeLength(request.getSourceCode().length());
        history.setTargetCodeLength(response.getTargetCode().length());
        history.setWarnings(response.getWarnings());
        history.setSuggestions(response.getSuggestions());
        history.setCreatedAt(LocalDateTime.now());
        history.setUpdatedAt(LocalDateTime.now());
        
        conversionHistory.put(history.getId(), history);
    }
    
    private int findLineNumber(String code, String searchString) {
        String[] lines = code.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(searchString)) {
                return i + 1;
            }
        }
        return -1;
    }
}
