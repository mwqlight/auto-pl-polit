package com.company.pythonlearning.dto.response;

import lombok.Data;

import java.util.List;

/**
 * 代码转换响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class CodeConversionResponse {
    
    /**
     * 转换类型：code（直接代码转换）、file（单文件转换）、directory（多文件转换）
     */
    private String conversionType;
    
    /**
     * 转换后的代码（仅在conversionType为code时使用）
     */
    private String targetCode;
    
    /**
     * 转换后的文件（仅在conversionType为file时使用）
     */
    private TargetFile targetFile;
    
    /**
     * 转换后的目录（仅在conversionType为directory时使用）
     */
    private List<TargetFile> targetDirectory;
    
    private Long conversionTime;
    
    private List<ConversionWarning> warnings;
    
    private List<ConversionSuggestion> suggestions;
    
    private ConversionSummary summary;
    
    @Data
    public static class TargetFile {
        private String fileName;
        private String fileContent;
        private String fileType;
        private String relativePath;
        private List<ConversionWarning> fileWarnings;
        private List<ConversionSuggestion> fileSuggestions;
    }
    
    @Data
    public static class ConversionSummary {
        private Integer totalFiles;
        private Integer convertedFiles;
        private Integer failedFiles;
        private Long totalLines;
        private Long convertedLines;
        private Double successRate;
    }
    
    @Data
    public static class ConversionWarning {
        private String message;
        private Integer lineNumber;
        private String severity;
        private String type;
        private String fileName;
    }
    
    @Data
    public static class ConversionSuggestion {
        private String message;
        private Integer lineNumber;
        private String category;
        private String recommendedChange;
        private String fileName;
    }
}
