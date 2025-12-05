package com.company.pythonlearning.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 代码转换请求DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class CodeConversionRequest {
    
    /**
     * 转换类型：code（直接代码转换）、file（单文件转换）、directory（多文件转换）
     */
    @NotBlank(message = "转换类型不能为空")
    private String conversionType = "code";
    
    /**
     * 源代码（仅在conversionType为code时使用）
     */
    private String sourceCode;
    
    /**
     * 源文件（仅在conversionType为file时使用）
     */
    private SourceFile sourceFile;
    
    /**
     * 源目录（仅在conversionType为directory时使用）
     */
    private List<SourceFile> sourceDirectory;
    
    private String sourceLanguage = "java";
    
    private String targetLanguage = "python";
    
    private ConversionOptions conversionOptions;
    
    @Data
    public static class SourceFile {
        @NotBlank(message = "文件名不能为空")
        private String fileName;
        
        @NotBlank(message = "文件内容不能为空")
        private String fileContent;
        
        private String fileType;
        private String relativePath;
    }
    
    @Data
    public static class ConversionOptions {
        private Integer indentSize = 4;
        private String namingConvention = "snake_case";
        private Boolean includeComments = true;
        private Boolean handleExceptions = true;
        private Boolean optimizeImports = true;
        private Boolean preserveStructure = true;
        private Boolean useTypeHints = true;
        private Boolean convertDocstrings = true;
    }
}