package com.company.pythonlearning.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 代码转换请求DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class CodeConversionRequest {
    
    /**
     * 转换类型：code（直接代码转换）、file（单文件转换）、directory（多文件转换即目录）
     */
    private String conversionType = "code";
    
    /**
     * 源代码（当转换类型为code时使用）
     */
    @NotBlank(message = "源代码不能为空")
    private String sourceCode;
    
    /**
     * 源文件路径（当转换类型为file时使用）
     */
    private String sourceFilePath;
    
    /**
     * 源目录路径（当转换类型为directory时使用）
     */
    private String sourceDirectoryPath;
    
    private String sourceLanguage = "java";
    
    private String targetLanguage = "python";
    
    private ConversionOptions conversionOptions;
    
    @Data
    public static class ConversionOptions {
        private Integer indentSize = 4;
        private String namingConvention = "snake_case";
        private Boolean includeComments = true;
        private Boolean handleExceptions = true;
        private Boolean optimizeImports = true;
    }
}