package com.company.pythonlearning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 转换历史记录响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionHistoryResponse {
    
    private Long id;
    
    private String sourceCodePreview;
    
    private String targetCodePreview;
    
    private String sourceLanguage;
    
    private String targetLanguage;
    
    private LocalDateTime timestamp;
    
    private double conversionScore;
    
    private long executionTime;
}