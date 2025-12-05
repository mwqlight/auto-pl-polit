package com.company.pythonlearning.dto.response;

import lombok.Data;

/**
 * 转换统计信息响应DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class ConversionStatsResponse {
    
    private Long totalConversions;
    
    private Long successfulConversions;
    
    private Double averageConversionTime;
    
    private String mostCommonSourceLanguage;
    
    private String mostCommonTargetLanguage;
    
    private Long totalCodeLinesConverted;
    
    private Integer activeUsers;
    
    private Long thisWeekConversions;
    
    private Long thisMonthConversions;
}
