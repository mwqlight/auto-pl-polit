package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.entity.LearningModule;
import com.company.pythonlearning.enums.DifficultyLevel;
import com.company.pythonlearning.repository.LearningModuleRepository;
import com.company.pythonlearning.service.LearningModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学习模块服务实现类
 * 
 * @author Company
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LearningModuleServiceImpl implements LearningModuleService {
    
    private final LearningModuleRepository learningModuleRepository;
    
    @Override
    public List<LearningModule> getAllModules() {
        return learningModuleRepository.findAll();
    }
    
    @Override
    public List<LearningModule> getPublishedModules() {
        return learningModuleRepository.findByIsPublishedTrueOrderBySortOrderAsc();
    }
    
    @Override
    public LearningModule getModuleById(Long id) {
        return learningModuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学习模块不存在: " + id));
    }
    
    @Override
    @Transactional
    public LearningModule createModule(LearningModule module) {
        // 设置排序索引
        module.setSortOrder(learningModuleRepository.findNextSortOrder());
        
        // 设置默认值
        if (module.getIsPublished() == null) {
            module.setIsPublished(false);
        }
        
        return learningModuleRepository.save(module);
    }
    
    @Override
    @Transactional
    public LearningModule updateModule(Long id, LearningModule module) {
        LearningModule existingModule = getModuleById(id);
        
        // 更新允许修改的字段
        if (module.getTitle() != null) {
            existingModule.setTitle(module.getTitle());
        }
        if (module.getDescription() != null) {
            existingModule.setDescription(module.getDescription());
        }
        if (module.getDifficultyLevel() != null) {
            existingModule.setDifficultyLevel(module.getDifficultyLevel());
        }
        if (module.getEstimatedDuration() != null) {
            existingModule.setEstimatedDuration(module.getEstimatedDuration());
        }
        if (module.getSortOrder() != null) {
            existingModule.setSortOrder(module.getSortOrder());
        }
        if (module.getThumbnailUrl() != null) {
            existingModule.setThumbnailUrl(module.getThumbnailUrl());
        }
        
        return learningModuleRepository.save(existingModule);
    }
    
    @Override
    @Transactional
    public void deleteModule(Long id) {
        LearningModule module = getModuleById(id);
        learningModuleRepository.delete(module);
        log.info("删除学习模块: {}", module.getTitle());
    }
    
    @Override
    @Transactional
    public LearningModule publishModule(Long id) {
        LearningModule module = getModuleById(id);
        module.setIsPublished(true);
        return learningModuleRepository.save(module);
    }
    
    @Override
    @Transactional
    public LearningModule unpublishModule(Long id) {
        LearningModule module = getModuleById(id);
        module.setIsPublished(false);
        return learningModuleRepository.save(module);
    }
    
    @Override
    public List<LearningModule> getModulesByDifficulty(DifficultyLevel difficultyLevel) {
        return learningModuleRepository.findByDifficultyLevelAndIsPublished(difficultyLevel, true);
    }
    
    @Override
    public List<LearningModule> searchModules(String keyword) {
        return learningModuleRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    @Override
    public Object[] getModuleStatistics() {
        long totalModules = learningModuleRepository.count();
        long publishedModules = learningModuleRepository.countPublishedModules();
        List<Object[]> difficultyStats = learningModuleRepository.countModulesByDifficulty();
        
        return new Object[]{
            totalModules,
            publishedModules,
            difficultyStats
        };
    }
}