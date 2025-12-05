package com.company.pythonlearning.service;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.dto.response.ConversionHistoryResponse;
import com.company.pythonlearning.dto.response.ConversionStatsResponse;

import java.util.List;

/**
 * 代码转换服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface CodeConverterService {
    
    /**
     * 转换Java代码为Python代码
     * 
     * @param request 代码转换请求
     * @return 代码转换响应
     */
    CodeConversionResponse convertJavaToPython(CodeConversionRequest request);
    
    /**
     * 获取转换历史记录
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 转换历史记录列表
     */
    List<ConversionHistoryResponse> getConversionHistory(int page, int size);
    
    /**
     * 获取转换详情
     * 
     * @param id 转换记录ID
     * @return 转换详情
     */
    ConversionHistoryResponse getConversionDetail(Long id);
    
    /**
     * 删除转换历史记录
     * 
     * @param id 转换记录ID
     */
    void deleteConversionHistory(Long id);
    
    /**
     * 清空转换历史记录
     */
    void clearConversionHistory();
    
    /**
     * 获取转换统计信息
     * 
     * @return 转换统计信息
     */
    ConversionStatsResponse getConversionStats();
    
    /**
     * 验证Java代码
     * 
     * @param code Java代码
     * @return 验证结果
     */
    List<CodeConversionResponse.ConversionWarning> validateJavaCode(String code);
    
    /**
     * 验证Python代码
     * 
     * @param code Python代码
     * @return 验证结果
     */
    List<CodeConversionResponse.ConversionWarning> validatePythonCode(String code);
}
