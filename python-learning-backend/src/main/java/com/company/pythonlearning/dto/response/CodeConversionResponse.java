package com.company.pythonlearning.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码转换响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeConversionResponse {
    
    private boolean success;
    
    private String convertedCode;
    
    private double conversionScore;
    
    private List<String> warnings;
    
    private List<String> errors;
    
    private long executionTime;
    
    private String sourceLanguage;
    
    private String targetLanguage;
}