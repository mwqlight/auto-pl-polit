package com.company.pythonlearning.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 转换历史记录响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class ConversionHistoryResponse {
    
    private Long id;
    
    private String sourceCode;
    
    private String targetCode;
    
    private String sourceLanguage;
    
    private String targetLanguage;
    
    private Long conversionTime;
    
    private Integer sourceCodeLength;
    
    private Integer targetCodeLength;
    
    private List<CodeConversionResponse.ConversionWarning> warnings;
    
    private List<CodeConversionResponse.ConversionSuggestion> suggestions;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
