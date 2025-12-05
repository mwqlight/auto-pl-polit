package com.company.pythonlearning.controller;

import com.company.pythonlearning.dto.request.LoginRequest;
import com.company.pythonlearning.dto.request.RegisterRequest;
import com.company.pythonlearning.dto.response.ApiResponse;
import com.company.pythonlearning.entity.User;
import com.company.pythonlearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            // 生成简单的token（实际项目中应该使用JWT）
            String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
            
            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("user", user);
            
            return ResponseEntity.ok(new ApiResponse<>(200, "登录成功", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(401, e.getMessage(), null));
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
            // 生成简单的token（实际项目中应该使用JWT）
            String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
            
            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("user", user);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(201, "注册成功", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout() {
        // 这里应该处理token失效逻辑
        return ResponseEntity.ok(new ApiResponse<>(200, "登出成功", null));
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Object>> refreshToken() {
        // 这里应该处理Token刷新逻辑
        return ResponseEntity.ok(new ApiResponse<>(200, "Token刷新成功", null));
    }
}