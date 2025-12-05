package com.company.pythonlearning.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 代码转换响应DTO
 *
 * @author Company
 * @version 1.0.0
 */
@Data
public class CodeConversionResponse {

    /**
     * 转换是否成功
     */
    private Boolean success;

    /**
     * 转换后的代码（当转换类型为code时使用）
     */
    private String convertedCode;

    /**
     * 转换后的文件路径（当转换类型为file时使用）
     */
    private String convertedFilePath;

    /**
     * 转换后的目录路径（当转换类型为directory时使用）
     */
    private String convertedDirectoryPath;

    /**
     * 转换的文件统计信息（当转换类型为directory时使用）
     * key: 文件名, value: 转换状态（success/failed）
     */
    private Map<String, String> fileConversionStats;

    /**
     * 转换得分（0-100）
     */
    private Integer conversionScore;

    /**
     * 转换警告信息列表
     */
    private List<String> warnings;

    /**
     * 转换错误信息列表
     */
    private List<String> errors;

    /**
     * 转换执行时间（毫秒）
     */
    private Long executionTime;
}
