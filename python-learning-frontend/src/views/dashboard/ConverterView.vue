<template>
  <div class="converter-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">代码转换器</h1>
      <p class="page-subtitle">将Java代码智能转换为Python代码</p>
    </div>

    <!-- 转换区域 -->
    <div class="converter-area">
      <!-- 左侧：Java代码输入 -->
      <div class="code-input-panel">
        <div class="panel-header">
          <h3>Java代码输入</h3>
          <div class="panel-actions">
            <el-button size="small" @click="clearSourceCode">清空</el-button>
            <el-button size="small" @click="loadExample">示例代码</el-button>
            <el-upload
              action=""
              :show-file-list="false"
              :before-upload="handleFileUpload"
              accept=".java,.txt"
            >
              <el-button size="small">上传文件</el-button>
            </el-upload>
          </div>
        </div>
        
        <div class="code-editor-container">
          <textarea
            v-model="sourceCode"
            placeholder="请输入Java代码..."
            class="code-editor"
            spellcheck="false"
          ></textarea>
          <div class="code-stats">
            <span>行数: {{ sourceLineCount }}</span>
            <span>字符: {{ sourceCharCount }}</span>
          </div>
        </div>
      </div>

      <!-- 中间：转换控制 -->
      <div class="converter-controls">
        <el-button 
          type="primary" 
          :loading="isConverting" 
          :disabled="!sourceCode.trim()"
          @click="handleConvert"
          class="convert-btn"
        >
          <i class="el-icon-right"></i>
          转换
        </el-button>
        
        <div class="conversion-options">
          <h4>转换选项</h4>
          <el-form label-width="100px" size="small">
            <el-form-item label="缩进大小">
              <el-select v-model="conversionOptions.indentSize">
                <el-option label="2空格" :value="2" />
                <el-option label="4空格" :value="4" />
              </el-select>
            </el-form-item>
            
            <el-form-item label="命名规范">
              <el-select v-model="conversionOptions.namingConvention">
                <el-option label="蛇形命名" value="snake_case" />
                <el-option label="驼峰命名" value="camelCase" />
              </el-select>
            </el-form-item>
            
            <el-form-item label="包含注释">
              <el-switch v-model="conversionOptions.includeComments" />
            </el-form-item>
            
            <el-form-item label="异常处理">
              <el-switch v-model="conversionOptions.handleExceptions" />
            </el-form-item>
          </el-form>
        </div>
      </div>

      <!-- 右侧：Python代码输出 -->
      <div class="code-output-panel">
        <div class="panel-header">
          <h3>Python代码输出</h3>
          <div class="panel-actions">
            <el-button 
              size="small" 
              :disabled="!targetCode"
              @click="copyToClipboard"
            >
              复制
            </el-button>
            <el-button 
              size="small" 
              :disabled="!targetCode"
              @click="downloadCode"
            >
              下载
            </el-button>
            <el-button 
              size="small" 
              :disabled="!targetCode"
              @click="clearTargetCode"
            >
              清空
            </el-button>
          </div>
        </div>
        
        <div class="code-editor-container">
          <pre class="code-output">{{ targetCode || '转换后的Python代码将显示在这里...' }}</pre>
          <div class="code-stats">
            <span>行数: {{ targetLineCount }}</span>
            <span>字符: {{ targetCharCount }}</span>
            <span v-if="conversionScore > 0">相似度: {{ (conversionScore * 100).toFixed(1) }}%</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 转换历史 -->
    <div class="conversion-history">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span>转换历史</span>
            <el-button size="small" @click="clearHistory">清空历史</el-button>
          </div>
        </template>
        
        <div class="history-list">
          <div 
            v-for="record in conversionHistory" 
            :key="record.id" 
            class="history-item"
            @click="loadHistoryRecord(record)"
          >
            <div class="history-info">
              <div class="history-title">{{ record.fileName || '未命名文件' }}</div>
              <div class="history-meta">
                <span>{{ formatTime(record.timestamp) }}</span>
                <el-tag :type="getScoreType(record.conversionScore)" size="small">
                  {{ (record.conversionScore * 100).toFixed(1) }}%
                </el-tag>
              </div>
            </div>
            <div class="history-actions">
              <el-button size="mini" type="text" @click.stop="deleteRecord(record.id)">
                删除
              </el-button>
            </div>
          </div>
          
          <div v-if="conversionHistory.length === 0" class="empty-history">
            <i class="el-icon-document"></i>
            <p>暂无转换记录</p>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useConverterStore } from '@/store'
import { formatTime, copyToClipboard as copyText, downloadFile } from '@/utils'
import { ElMessage } from 'element-plus'

const converterStore = useConverterStore()

// 响应式数据
const sourceCode = ref('')
const targetCode = ref('')
const isConverting = ref(false)
const conversionScore = ref(0)

// 计算属性
const sourceLineCount = computed(() => {
  return sourceCode.value ? sourceCode.value.split('\n').length : 0
})

const sourceCharCount = computed(() => {
  return sourceCode.value.length
})

const targetLineCount = computed(() => {
  return targetCode.value ? targetCode.value.split('\n').length : 0
})

const targetCharCount = computed(() => {
  return targetCode.value.length
})

const conversionHistory = computed(() => {
  return converterStore.conversionHistory.slice(0, 5) // 只显示最近5条
})

const conversionOptions = computed({
  get: () => converterStore.conversionOptions,
  set: (value) => Object.assign(converterStore.conversionOptions, value)
})

// 方法定义
const handleConvert = async () => {
  if (!sourceCode.value.trim()) {
    ElMessage.warning('请输入Java代码')
    return
  }

  isConverting.value = true
  
  try {
    await converterStore.convertCode(sourceCode.value)
    targetCode.value = converterStore.targetCode
    conversionScore.value = 0.85 // 模拟转换分数
    
    ElMessage.success('代码转换成功！')
  } catch (error) {
    console.error('转换失败:', error)
    ElMessage.error('代码转换失败，请重试')
  } finally {
    isConverting.value = false
  }
}

const clearSourceCode = () => {
  sourceCode.value = ''
  ElMessage.info('已清空输入代码')
}

const clearTargetCode = () => {
  targetCode.value = ''
  conversionScore.value = 0
  ElMessage.info('已清空输出代码')
}

const loadExample = () => {
  sourceCode.value = `public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        System.out.println("结果: " + result);
    }
}`
  ElMessage.info('已加载示例代码')
}

const handleFileUpload = (file: File) => {
  const reader = new FileReader()
  reader.onload = (e) => {
    sourceCode.value = e.target?.result as string
    ElMessage.success(`已加载文件: ${file.name}`)
  }
  reader.readAsText(file)
  return false // 阻止默认上传行为
}

const copyToClipboard = async () => {
  if (!targetCode.value) {
    ElMessage.warning('没有可复制的代码')
    return
  }
  
  const success = await copyText(targetCode.value)
  if (success) {
    ElMessage.success('代码已复制到剪贴板')
  } else {
    ElMessage.error('复制失败')
  }
}

const downloadCode = () => {
  if (!targetCode.value) {
    ElMessage.warning('没有可下载的代码')
    return
  }
  
  downloadFile(targetCode.value, 'converted_code.py', 'text/x-python')
  ElMessage.success('代码已下载')
}

const loadHistoryRecord = (record: any) => {
  sourceCode.value = record.sourceCode
  targetCode.value = record.targetCode
  conversionScore.value = record.conversionScore
  ElMessage.info('已加载历史记录')
}

const deleteRecord = (id: number) => {
  // 这里应该调用API删除记录
  ElMessage.info('删除功能开发中...')
}

const clearHistory = () => {
  converterStore.clearHistory()
  ElMessage.info('已清空转换历史')
}

const getScoreType = (score: number) => {
  if (score >= 0.9) return 'success'
  if (score >= 0.7) return 'warning'
  return 'danger'
}

// 初始化
onMounted(() => {
  // 可以在这里加载用户的历史记录
})
</script>

<style scoped lang="scss">
.converter-container {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
  
  .page-title {
    font-size: 28px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin-bottom: 8px;
    background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
  
  .page-subtitle {
    font-size: 16px;
    color: var(--el-text-color-secondary);
    margin: 0;
  }
}

.converter-area {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 20px;
  margin-bottom: 30px;
}

.code-input-panel,
.code-output-panel {
  background: var(--el-bg-color);
  border-radius: 12px;
  border: 1px solid var(--el-border-color-light);
  overflow: hidden;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background: var(--el-fill-color-light);
  border-bottom: 1px solid var(--el-border-color-light);
  
  h3 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
    color: var(--el-text-color-primary);
  }
  
  .panel-actions {
    display: flex;
    gap: 8px;
  }
}

.code-editor-container {
  position: relative;
  height: 400px;
}

.code-editor {
  width: 100%;
  height: 100%;
  border: none;
  padding: 20px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  line-height: 1.5;
  background: var(--el-bg-color);
  color: var(--el-text-color-primary);
  resize: none;
  outline: none;
  
  &::placeholder {
    color: var(--el-text-color-placeholder);
  }
}

.code-output {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 20px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  line-height: 1.5;
  background: var(--el-bg-color);
  color: var(--el-text-color-primary);
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow-y: auto;
  
  &:empty::before {
    content: '转换后的Python代码将显示在这里...';
    color: var(--el-text-color-placeholder);
  }
}

.code-stats {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 8px 20px;
  background: var(--el-fill-color-light);
  border-top: 1px solid var(--el-border-color-light);
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.converter-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 30px;
  padding: 0 20px;
}

.convert-btn {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  
  i {
    font-size: 24px;
  }
  
  &:hover:not(:disabled) {
    transform: scale(1.05);
    box-shadow: 0 8px 25px rgba(var(--el-color-primary-rgb), 0.3);
  }
  
  &:active {
    transform: scale(1);
  }
}

.conversion-options {
  width: 100%;
  
  h4 {
    margin: 0 0 15px 0;
    font-size: 14px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    text-align: center;
  }
  
  .el-form-item {
    margin-bottom: 12px;
  }
}

.conversion-history {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}

.history-list {
  max-height: 300px;
  overflow-y: auto;
}

.history-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid var(--el-border-color-light);
  cursor: pointer;
  transition: background-color 0.2s;
  
  &:last-child {
    border-bottom: none;
  }
  
  &:hover {
    background: var(--el-fill-color-light);
  }
  
  .history-info {
    flex: 1;
    
    .history-title {
      font-size: 14px;
      font-weight: 500;
      color: var(--el-text-color-primary);
      margin-bottom: 4px;
    }
    
    .history-meta {
      display: flex;
      align-items: center;
      gap: 10px;
      font-size: 12px;
      color: var(--el-text-color-secondary);
    }
  }
  
  .history-actions {
    opacity: 0;
    transition: opacity 0.2s;
  }
  
  &:hover .history-actions {
    opacity: 1;
  }
}

.empty-history {
  text-align: center;
  padding: 40px 20px;
  color: var(--el-text-color-secondary);
  
  i {
    font-size: 48px;
    margin-bottom: 10px;
    opacity: 0.5;
  }
  
  p {
    margin: 0;
    font-size: 14px;
  }
}

@media (max-width: 1024px) {
  .converter-area {
    grid-template-columns: 1fr;
    grid-template-rows: auto auto auto;
  }
  
  .converter-controls {
    flex-direction: row;
    justify-content: space-around;
    padding: 20px;
  }
  
  .convert-btn {
    width: 80px;
    height: 80px;
    font-size: 14px;
    
    i {
      font-size: 20px;
    }
  }
}

@media (max-width: 768px) {
  .converter-container {
    padding: 10px;
  }
  
  .converter-area {
    gap: 15px;
  }
  
  .converter-controls {
    flex-direction: column;
    gap: 20px;
  }
  
  .code-editor-container {
    height: 300px;
  }
}
</style>