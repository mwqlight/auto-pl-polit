package com.company.pythonlearning.service;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;

import java.util.List;

/**
 * 代码转换器服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface CodeConverterService {
    
    /**
     * 转换代码
     * 
     * @param request 代码转换请求
     * @return 转换结果
     */
    CodeConversionResponse convertCode(CodeConversionRequest request);
    
    /**
     * 获取转换历史记录
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 转换历史记录列表
     */
    List<ConversionHistoryResponse> getConversionHistory(Long userId, Integer limit);
    
    /**
     * 清除转换历史记录
     * 
     * @param userId 用户ID
     */
    void clearConversionHistory(Long userId);
    
    /**
     * 获取转换统计信息
     * 
     * @param userId 用户ID
     * @return 统计信息
     */
    ConversionStats getConversionStats(Long userId);
    
    /**
     * 转换统计信息
     */
    interface ConversionStats {
        long getTotalConversions();
        long getSuccessfulConversions();
        double getSuccessRate();
        long getAverageExecutionTime();
    }
}