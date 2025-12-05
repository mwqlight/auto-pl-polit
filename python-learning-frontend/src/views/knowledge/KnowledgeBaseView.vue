<template>
  <div class="knowledge-base-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">
        <el-icon><DataBoard /></el-icon>
        Python 全场景应用知识库
      </h1>
      <p class="page-desc">探索Python在不同领域的实际应用案例和最佳实践</p>
    </div>

    <!-- 知识库主体 -->
    <div class="knowledge-container">
      <!-- 搜索和筛选区域 -->
      <div class="search-section">
        <div class="search-bar">
          <el-input
            v-model="searchQuery"
            placeholder="搜索应用场景、技术栈或关键词..."
            size="large"
            clearable
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" size="large">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
        </div>
        
        <div class="filter-section">
          <el-select v-model="selectedCategory" placeholder="选择应用领域" clearable>
            <el-option label="Web开发" value="web" />
            <el-option label="数据分析" value="data" />
            <el-option label="人工智能" value="ai" />
            <el-option label="自动化脚本" value="automation" />
            <el-option label="网络编程" value="network" />
            <el-option label="游戏开发" value="game" />
          </el-select>
          
          <el-select v-model="selectedDifficulty" placeholder="选择难度级别" clearable>
            <el-option label="入门" value="beginner" />
            <el-option label="中级" value="intermediate" />
            <el-option label="高级" value="advanced" />
          </el-select>
          
          <el-select v-model="selectedTechnology" placeholder="选择技术栈" clearable>
            <el-option label="Django" value="django" />
            <el-option label="Flask" value="flask" />
            <el-option label="Pandas" value="pandas" />
            <el-option label="NumPy" value="numpy" />
            <el-option label="TensorFlow" value="tensorflow" />
            <el-option label="PyTorch" value="pytorch" />
          </el-select>
        </div>
      </div>

      <!-- 知识卡片网格 -->
      <div class="knowledge-grid">
        <div 
          v-for="item in filteredKnowledge" 
          :key="item.id"
          class="knowledge-card"
          @click="openKnowledgeDetail(item)"
        >
          <div class="card-header">
            <div class="category-tag" :class="item.category">
              {{ getCategoryLabel(item.category) }}
            </div>
            <div class="difficulty-badge" :class="item.difficulty">
              {{ getDifficultyLabel(item.difficulty) }}
            </div>
          </div>
          
          <div class="card-content">
            <h3 class="card-title">{{ item.title }}</h3>
            <p class="card-desc">{{ item.description }}</p>
            
            <div class="tech-stack">
              <el-tag 
                v-for="tech in item.technologies" 
                :key="tech"
                size="small"
                type="info"
              >
                {{ tech }}
              </el-tag>
            </div>
          </div>
          
          <div class="card-footer">
            <div class="stats">
              <span class="stat">
                <el-icon><View /></el-icon>
                {{ item.views }}
              </span>
              <span class="stat">
                <el-icon><Star /></el-icon>
                {{ item.rating }}
              </span>
            </div>
            <div class="update-time">
              更新: {{ item.updatedAt }}
            </div>
          </div>
        </div>
      </div>

      <!-- 分页控制 -->
      <div class="pagination-section">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[12, 24, 36, 48]"
          :total="totalItems"
          layout="total, sizes, prev, pager, next, jumper"
          background
        />
      </div>
    </div>

    <!-- 知识详情弹窗 -->
    <el-dialog
      v-model="detailVisible"
      :title="currentDetail?.title"
      width="90%"
      top="5vh"
      custom-class="knowledge-detail-dialog"
    >
      <div v-if="currentDetail" class="detail-content">
        <!-- 详情头部 -->
        <div class="detail-header">
          <div class="detail-meta">
            <div class="meta-tags">
              <el-tag :type="getCategoryType(currentDetail.category)">
                {{ getCategoryLabel(currentDetail.category) }}
              </el-tag>
              <el-tag :type="getDifficultyType(currentDetail.difficulty)">
                {{ getDifficultyLabel(currentDetail.difficulty) }}
              </el-tag>
            </div>
            <div class="meta-stats">
              <span>浏览: {{ currentDetail.views }}</span>
              <span>评分: {{ currentDetail.rating }}/5.0</span>
              <span>更新: {{ currentDetail.updatedAt }}</span>
            </div>
          </div>
        </div>

        <!-- 详情内容 -->
        <div class="detail-body">
          <!-- 应用场景 -->
          <div class="detail-section">
            <h3>应用场景</h3>
            <p>{{ currentDetail.scenario }}</p>
          </div>

          <!-- 技术栈 -->
          <div class="detail-section">
            <h3>技术栈</h3>
            <div class="tech-details">
              <div 
                v-for="tech in currentDetail.technologyDetails" 
                :key="tech.name"
                class="tech-item"
              >
                <strong>{{ tech.name }}:</strong> {{ tech.description }}
              </div>
            </div>
          </div>

          <!-- 代码示例 -->
          <div class="detail-section">
            <h3>代码示例</h3>
            <div class="code-example">
              <pre class="code-display">{{ currentDetail.codeExample }}</pre>
            </div>
          </div>

          <!-- 最佳实践 -->
          <div class="detail-section">
            <h3>最佳实践</h3>
            <ul class="best-practices">
              <li v-for="practice in currentDetail.bestPractices" :key="practice">
                {{ practice }}
              </li>
            </ul>
          </div>

          <!-- 常见问题 -->
          <div class="detail-section">
            <h3>常见问题</h3>
            <div class="faq-list">
              <div 
                v-for="faq in currentDetail.faq" 
                :key="faq.question"
                class="faq-item"
              >
                <div class="faq-question">
                  <strong>Q: {{ faq.question }}</strong>
                </div>
                <div class="faq-answer">
                  A: {{ faq.answer }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="detailVisible = false">关闭</el-button>
        <el-button type="primary" @click="tryExample">尝试示例</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import {
  DataBoard,
  Search,
  View,
  Star
} from '@element-plus/icons-vue'

// 响应式数据
const searchQuery = ref('')
const selectedCategory = ref('')
const selectedDifficulty = ref('')
const selectedTechnology = ref('')
const currentPage = ref(1)
const pageSize = ref(12)
const detailVisible = ref(false)
const currentDetail = ref<any>(null)

// 知识库数据
const knowledgeItems = ref([
  {
    id: 1,
    title: 'Django REST API开发',
    description: '使用Django REST Framework构建高性能的RESTful API服务',
    category: 'web',
    difficulty: 'intermediate',
    technologies: ['Django', 'DRF', 'PostgreSQL', 'Redis'],
    views: 1245,
    rating: 4.8,
    updatedAt: '2024-01-15',
    scenario: '构建企业级后端API服务，支持用户管理、数据接口、权限控制等功能',
    technologyDetails: [
      { name: 'Django', description: '高级Python Web框架，提供完整的MVT架构' },
      { name: 'DRF', description: 'Django REST Framework，强大的API开发工具包' },
      { name: 'PostgreSQL', description: '企业级关系型数据库' },
      { name: 'Redis', description: '内存数据存储，用于缓存和会话管理' }
    ],
    codeExample: `# settings.py配置
REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': [
        'rest_framework.authentication.TokenAuthentication',
    ],
    'DEFAULT_PERMISSION_CLASSES': [
        'rest_framework.permissions.IsAuthenticated',
    ]
}

# views.py示例
from rest_framework import viewsets, permissions
from .models import User
from .serializers import UserSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = [permissions.IsAuthenticated]
    
    def perform_create(self, serializer):
        serializer.save(created_by=self.request.user)`,
    bestPractices: [
      '使用序列化器进行数据验证和转换',
      '实现适当的权限控制和认证机制',
      '使用分页优化大数据集查询',
      '实现API版本控制',
      '添加适当的缓存策略'
    ],
    faq: [
      {
        question: '如何处理复杂的关联查询？',
        answer: '可以使用prefetch_related和select_related优化查询性能'
      },
      {
        question: '如何实现文件上传功能？',
        answer: '使用FileField和ImageField，配合适当的存储后端'
      }
    ]
  },
  {
    id: 2,
    title: 'Pandas数据分析',
    description: '使用Pandas进行数据清洗、转换和分析的完整流程',
    category: 'data',
    difficulty: 'beginner',
    technologies: ['Pandas', 'NumPy', 'Matplotlib', 'Jupyter'],
    views: 2890,
    rating: 4.6,
    updatedAt: '2024-01-10',
    scenario: '处理结构化数据，进行数据清洗、统计分析、可视化展示',
    technologyDetails: [
      { name: 'Pandas', description: '强大的数据处理和分析库' },
      { name: 'NumPy', description: '科学计算基础库' },
      { name: 'Matplotlib', description: '数据可视化库' },
      { name: 'Jupyter', description: '交互式编程环境' }
    ],
    codeExample: `import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# 读取数据
df = pd.read_csv('data.csv')

# 数据清洗
df = df.dropna()  # 删除空值
df = df.drop_duplicates()  # 删除重复值

# 数据分析
print("数据基本信息:")
print(df.info())
print("\n描述性统计:")
print(df.describe())

# 数据可视化
df['sales'].plot(kind='hist', bins=20)
plt.title('销售数据分布')
plt.show()`,
    bestPractices: [
      '在处理大型数据集时使用适当的数据类型',
      '使用向量化操作替代循环',
      '定期保存中间结果',
      '使用分组和聚合进行数据分析'
    ],
    faq: [
      {
        question: '如何处理内存不足的问题？',
        answer: '可以使用chunksize分块读取，或者使用Dask处理超大数据集'
      },
      {
        question: '如何优化Pandas性能？',
        answer: '使用NumPy操作、避免链式赋值、使用适当的数据类型'
      }
    ]
  },
  {
    id: 3,
    title: 'Flask微服务架构',
    description: '基于Flask构建微服务架构的完整解决方案',
    category: 'web',
    difficulty: 'advanced',
    technologies: ['Flask', 'Docker', 'Kubernetes', 'RabbitMQ'],
    views: 876,
    rating: 4.9,
    updatedAt: '2024-01-08',
    scenario: '构建可扩展的微服务系统，支持服务发现、负载均衡、容错处理',
    technologyDetails: [
      { name: 'Flask', description: '轻量级Web框架，适合微服务开发' },
      { name: 'Docker', description: '容器化部署工具' },
      { name: 'Kubernetes', description: '容器编排平台' },
      { name: 'RabbitMQ', description: '消息队列，用于服务间通信' }
    ],
    codeExample: `from flask import Flask, jsonify
import requests

app = Flask(__name__)

# 服务注册
def register_service(service_name, port):
    registry_url = "http://registry:5000/register"
    data = {
        "name": service_name,
        "address": "localhost",
        "port": port
    }
    requests.post(registry_url, json=data)

@app.route('/health')
def health_check():
    return jsonify({"status": "healthy"})

if __name__ == '__main__':
    register_service("user-service", 5001)
    app.run(port=5001)`,
    bestPractices: [
      '每个服务保持单一职责',
      '使用API网关进行路由和认证',
      '实现服务发现和负载均衡',
      '添加适当的监控和日志',
      '使用消息队列进行异步通信'
    ],
    faq: [
      {
        question: '如何处理服务间的数据一致性？',
        answer: '可以使用Saga模式或事件溯源保证最终一致性'
      },
      {
        question: '如何实现服务熔断？',
        answer: '可以使用Hystrix或Resilience4j等熔断器库'
      }
    ]
  }
  // 更多知识项...
])

// 计算属性
const totalItems = computed(() => filteredKnowledge.value.length)

const filteredKnowledge = computed(() => {
  let filtered = knowledgeItems.value
  
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(item => 
      item.title.toLowerCase().includes(query) ||
      item.description.toLowerCase().includes(query) ||
      item.technologies.some(tech => tech.toLowerCase().includes(query))
    )
  }
  
  if (selectedCategory.value) {
    filtered = filtered.filter(item => item.category === selectedCategory.value)
  }
  
  if (selectedDifficulty.value) {
    filtered = filtered.filter(item => item.difficulty === selectedDifficulty.value)
  }
  
  if (selectedTechnology.value) {
    filtered = filtered.filter(item => 
      item.technologies.some(tech => tech.toLowerCase().includes(selectedTechnology.value))
    )
  }
  
  return filtered
})

// 工具函数
const getCategoryLabel = (category: string) => {
  const labels: { [key: string]: string } = {
    web: 'Web开发',
    data: '数据分析',
    ai: '人工智能',
    automation: '自动化脚本',
    network: '网络编程',
    game: '游戏开发'
  }
  return labels[category] || category
}

const getDifficultyLabel = (difficulty: string) => {
  const labels: { [key: string]: string } = {
    beginner: '入门',
    intermediate: '中级',
    advanced: '高级'
  }
  return labels[difficulty] || difficulty
}

const getCategoryType = (category: string) => {
  const types: { [key: string]: string } = {
    web: 'primary',
    data: 'success',
    ai: 'warning',
    automation: 'info',
    network: 'danger',
    game: ''
  }
  return types[category] || ''
}

const getDifficultyType = (difficulty: string) => {
  const types: { [key: string]: string } = {
    beginner: 'success',
    intermediate: 'warning',
    advanced: 'danger'
  }
  return types[difficulty] || ''
}

// 方法定义
const openKnowledgeDetail = (item: any) => {
  currentDetail.value = item
  detailVisible.value = true
  
  // 增加浏览量
  item.views++
}

const tryExample = () => {
  ElMessage.success('示例代码已复制到剪贴板，请在开发环境中尝试')
  detailVisible.value = false
}
</script>

<style lang="scss" scoped>
.knowledge-base-view {
  padding: 24px;
  height: 100%;
  overflow-y: auto;
}

.page-header {
  margin-bottom: 32px;
  
  .page-title {
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 28px;
    font-weight: 700;
    color: #fff;
    margin: 0 0 8px 0;
    
    .el-icon {
      color: #00d4ff;
    }
  }
  
  .page-desc {
    color: rgba(255, 255, 255, 0.7);
    font-size: 16px;
    margin: 0;
  }
}

.knowledge-container {
  height: calc(100vh - 200px);
  display: flex;
  flex-direction: column;
}

.search-section {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  
  .search-bar {
    display: flex;
    gap: 12px;
    margin-bottom: 16px;
    
    .el-input {
      flex: 1;
    }
  }
  
  .filter-section {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
    
    .el-select {
      min-width: 150px;
    }
  }
}

.knowledge-grid {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
  margin-bottom: 24px;
  overflow-y: auto;
}

.knowledge-card {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
    border-color: rgba(0, 212, 255, 0.3);
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
    
    .category-tag {
      padding: 4px 12px;
      border-radius: 16px;
      font-size: 12px;
      font-weight: 600;
      
      &.web { background: rgba(64, 158, 255, 0.2); color: #409eff; }
      &.data { background: rgba(103, 194, 58, 0.2); color: #67c23a; }
      &.ai { background: rgba(230, 162, 60, 0.2); color: #e6a23c; }
      &.automation { background: rgba(144, 147, 153, 0.2); color: #909399; }
      &.network { background: rgba(245, 108, 108, 0.2); color: #f56c6c; }
      &.game { background: rgba(150, 84, 230, 0.2); color: #9654e6; }
    }
    
    .difficulty-badge {
      padding: 2px 8px;
      border-radius: 12px;
      font-size: 10px;
      font-weight: 600;
      
      &.beginner { background: rgba(103, 194, 58, 0.3); color: #67c23a; }
      &.intermediate { background: rgba(230, 162, 60, 0.3); color: #e6a23c; }
      &.advanced { background: rgba(245, 108, 108, 0.3); color: #f56c6c; }
    }
  }
  
  .card-content {
    margin-bottom: 16px;
    
    .card-title {
      color: #fff;
      font-size: 18px;
      font-weight: 600;
      margin: 0 0 8px 0;
      line-height: 1.4;
    }
    
    .card-desc {
      color: rgba(255, 255, 255, 0.7);
      font-size: 14px;
      line-height: 1.5;
      margin: 0 0 12px 0;
    }
    
    .tech-stack {
      display: flex;
      flex-wrap: wrap;
      gap: 6px;
    }
  }
  
  .card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    .stats {
      display: flex;
      gap: 12px;
      
      .stat {
        display: flex;
        align-items: center;
        gap: 4px;
        color: rgba(255, 255, 255, 0.6);
        font-size: 12px;
      }
    }
    
    .update-time {
      color: rgba(255, 255, 255, 0.5);
      font-size: 12px;
    }
  }
}

.pagination-section {
  display: flex;
  justify-content: center;
  padding: 16px 0;
}

// 详情弹窗样式
:deep(.knowledge-detail-dialog) {
  .el-dialog {
    background: rgba(16, 18, 27, 0.95);
    border-radius: 16px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    
    .el-dialog__header {
      background: rgba(255, 255, 255, 0.05);
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      margin: 0;
      padding: 20px;
      
      .el-dialog__title {
        color: #fff;
        font-size: 20px;
        font-weight: 600;
      }
    }
    
    .el-dialog__body {
      padding: 0;
      max-height: 70vh;
      overflow-y: auto;
    }
    
    .el-dialog__footer {
      background: rgba(255, 255, 255, 0.05);
      border-top: 1px solid rgba(255, 255, 255, 0.1);
      padding: 20px;
    }
  }
}

.detail-content {
  padding: 24px;
  
  .detail-header {
    margin-bottom: 24px;
    
    .detail-meta {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .meta-tags {
        display: flex;
        gap: 8px;
      }
      
      .meta-stats {
        display: flex;
        gap: 16px;
        color: rgba(255, 255, 255, 0.6);
        font-size: 14px;
      }
    }
  }
  
  .detail-body {
    .detail-section {
      margin-bottom: 24px;
      
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 12px 0;
        padding-bottom: 8px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      }
      
      p, .tech-details, .best-practices, .faq-list {
        color: rgba(255, 255, 255, 0.8);
        line-height: 1.6;
      }
      
      .tech-item {
        margin-bottom: 8px;
      }
      
      .best-practices {
        list-style: disc;
        padding-left: 20px;
        
        li {
          margin-bottom: 4px;
        }
      }
      
      .faq-item {
        margin-bottom: 16px;
        padding: 12px;
        background: rgba(255, 255, 255, 0.05);
        border-radius: 8px;
        
        .faq-question {
          color: #fff;
          margin-bottom: 8px;
        }
        
        .faq-answer {
          color: rgba(255, 255, 255, 0.7);
        }
      }
      
      .code-example {
        background: rgba(16, 18, 27, 0.8);
        border-radius: 8px;
        padding: 16px;
        
        .code-display {
          color: #00ff88;
          font-family: 'Monaco', 'Consolas', monospace;
          font-size: 14px;
          line-height: 1.5;
          margin: 0;
          white-space: pre-wrap;
          max-height: 300px;
          overflow-y: auto;
        }
      }
    }
  }
}
</style>