package com.company.pythonlearning.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户更新请求DTO
 * 
 * @author Company
 * @version 1.0.0
 */
@Data
public class UserUpdateRequest {
    
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;
    
    private String fullName;
    
    private String phone;
    
    private String avatar;
    
    private String bio;
}