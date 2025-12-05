package com.company.pythonlearning.enums;

/**
 * 难度级别枚举
 * 
 * @author Company
 * @version 1.0.0
 */
public enum DifficultyLevel {
    BEGINNER("初级"),
    INTERMEDIATE("中级"),
    ADVANCED("高级"),
    EXPERT("专家级");
    
    private final String description;
    
    DifficultyLevel(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}