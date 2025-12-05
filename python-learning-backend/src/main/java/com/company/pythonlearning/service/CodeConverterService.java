package com.company.pythonlearning.service;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.CodeConversionResponse;

/**
 * 代码转换服务接口
 *
 * @author Company
 * @version 1.0.0
 */
public interface CodeConverterService {

    /**
     * 将Java代码转换为Python代码（支持代码、文件和目录三种转换类型）
     *
     * @param request 代码转换请求
     * @return 代码转换响应
     */
    CodeConversionResponse convertJavaToPython(CodeConversionRequest request);

    /**
     * 直接转换Java代码为Python代码
     *
     * @param javaCode Java代码
     * @return 转换后的Python代码
     */
    String convertJavaCodeToPython(String javaCode);

    /**
     * 转换Java文件为Python文件
     *
     * @param javaFilePath Java文件路径
     * @return 转换后的Python文件路径
     * @throws java.io.IOException 如果文件操作失败
     */
    String convertJavaFileToPython(String javaFilePath) throws java.io.IOException;

    /**
     * 转换Java目录下的所有文件为Python文件
     *
     * @param javaDirectoryPath Java目录路径
     * @return 转换后的Python目录路径
     * @throws java.io.IOException 如果文件操作失败
     */
    String convertJavaDirectoryToPython(String javaDirectoryPath) throws java.io.IOException;

    /**
     * 验证Java代码语法
     *
     * @param code Java代码
     * @return 验证结果，包含是否有效和错误信息
     */
    String validateJavaCode(String code);

    /**
     * 验证Python代码语法
     *
     * @param code Python代码
     * @return 验证结果，包含是否有效和错误信息
     */
    String validatePythonCode(String code);
}
