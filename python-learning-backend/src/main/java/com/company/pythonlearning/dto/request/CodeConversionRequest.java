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
    
    @NotBlank(message = "源代码不能为空")
    private String sourceCode;
    
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