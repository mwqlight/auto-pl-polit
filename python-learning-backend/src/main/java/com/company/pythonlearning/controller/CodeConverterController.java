package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.request.CodeConversionRequest;
import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.dto.response.CodeConversionResponse;
import com.company.pythonlearning.service.CodeConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 代码转换控制器
 *
 * @author Company
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/converter")
@RequiredArgsConstructor
@Tag(name = "代码转换", description = "Java到Python代码转换相关API")
public class CodeConverterController {

    private final CodeConverterService codeConverterService;

    /**
     * 将Java代码转换为Python代码
     *
     * @param request 代码转换请求
     * @return 代码转换响应
     */
    @PostMapping("/java-to-python")
    @Operation(summary = "Java代码转换为Python代码", description = "将输入的Java代码转换为Python代码")
    public ApiResponse<CodeConversionResponse> convertJavaToPython(@Valid @RequestBody CodeConversionRequest request) {
        CodeConversionResponse response = codeConverterService.convertJavaToPython(request);
        return ApiResponse.success(response);
    }

    /**
     * 验证Java代码语法
     *
     * @param code Java代码
     * @return 验证结果
     */
    @PostMapping("/validate/java")
    @Operation(summary = "验证Java代码语法", description = "验证输入的Java代码是否符合语法规范")
    public ApiResponse<String> validateJavaCode(@Parameter(description = "Java代码") @RequestBody String code) {
        String result = codeConverterService.validateJavaCode(code);
        return ApiResponse.success(result);
    }

    /**
     * 验证Python代码语法
     *
     * @param code Python代码
     * @return 验证结果
     */
    @PostMapping("/validate/python")
    @Operation(summary = "验证Python代码语法", description = "验证输入的Python代码是否符合语法规范")
    public ApiResponse<String> validatePythonCode(@Parameter(description = "Python代码") @RequestBody String code) {
        String result = codeConverterService.validatePythonCode(code);
        return ApiResponse.success(result);
    }
}
