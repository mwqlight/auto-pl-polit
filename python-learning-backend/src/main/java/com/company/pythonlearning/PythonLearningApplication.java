package com.company.pythonlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Python语言学习驾驶舱系统 - 后端启动类
 * 
 * @author Company
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
public class PythonLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(PythonLearningApplication.class, args);
        
        System.out.println("""            
            ===========================================
            Python语言学习驾驶舱系统 - 后端服务已启动
            ===========================================
            
            应用名称: python-learning-backend
            启动端口: 8081
            数据库: MySQL (localhost:3306)
            缓存: Redis (localhost:6379)
            
            API文档地址: http://localhost:8081/swagger-ui.html
            
            ===========================================
            "");
    }
}