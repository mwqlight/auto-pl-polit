package com.company.pythonlearning.service;

import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.enums.DifficultyLevel;

import java.util.List;

/**
 * 学习模块服务接口
 * 
 * @author Company
 * @version 1.0.0
 */
public interface LearningModuleService {
    
    /**
     * 获取所有学习模块
     */
    List<LearningModule> getAllModules();
    
    /**
     * 获取已发布的学习模块
     */
    List<LearningModule> getPublishedModules();
    
    /**
     * 根据ID获取学习模块
     */
    LearningModule getModuleById(Long id);
    
    /**
     * 创建学习模块
     */
    LearningModule createModule(LearningModule module);
    
    /**
     * 更新学习模块
     */
    LearningModule updateModule(Long id, LearningModule module);
    
    /**
     * 删除学习模块
     */
    void deleteModule(Long id);
    
    /**
     * 发布学习模块
     */
    LearningModule publishModule(Long id);
    
    /**
     * 取消发布学习模块
     */
    LearningModule unpublishModule(Long id);
    
    /**
     * 根据难度级别获取模块
     */
    List<LearningModule> getModulesByDifficulty(DifficultyLevel difficultyLevel);
    
    /**
     * 搜索学习模块
     */
    List<LearningModule> searchModules(String keyword);
    
    /**
     * 统计模块数据
     */
    Object[] getModuleStatistics();
}