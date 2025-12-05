package com.company.pythonlearning.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class LoginRequest {
    
    @NotBlank(message = "用户名或邮箱不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
}