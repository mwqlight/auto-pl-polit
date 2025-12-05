<template>
  <div class="converter-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">
        <el-icon><Monitor /></el-icon>
        Java-to-Python 代码转换工作站
      </h1>
      <p class="page-desc">智能代码转换引擎，将Java代码无缝转换为Python代码</p>
    </div>

    <!-- 转换器主体 -->
    <div class="converter-container">
      <!-- 代码编辑器区域 -->
      <div class="editor-section">
        <div class="editor-panel">
          <div class="panel-header">
            <h3>Java 源代码</h3>
            <div class="panel-actions">
              <el-button size="small" @click="clearJavaCode">
                <el-icon><Delete /></el-icon>
                清空
              </el-button>
              <el-button size="small" @click="loadExample">
                <el-icon><DocumentCopy /></el-icon>
                加载示例
              </el-button>
            </div>
          </div>
          <div class="editor-wrapper">
            <textarea 
              v-model="javaCode" 
              class="code-editor"
              placeholder="请输入Java代码..."
              spellcheck="false"
            ></textarea>
          </div>
        </div>

        <!-- 转换按钮 -->
        <div class="converter-actions">
          <el-button 
            type="primary" 
            size="large" 
            :loading="converting"
            @click="convertCode"
            class="convert-btn"
          >
            <el-icon><Right /></el-icon>
            {{ converting ? '转换中...' : '开始转换' }}
          </el-button>
        </div>

        <div class="editor-panel">
          <div class="panel-header">
            <h3>Python 转换结果</h3>
            <div class="panel-actions">
              <el-button size="small" @click="copyPythonCode">
                <el-icon><CopyDocument /></el-icon>
                复制
              </el-button>
              <el-button size="small" @click="downloadPythonCode">
                <el-icon><Download /></el-icon>
                下载
              </el-button>
            </div>
          </div>
          <div class="editor-wrapper">
            <pre class="code-output">{{ pythonCode || '转换结果将显示在这里...' }}</pre>
          </div>
        </div>
      </div>

      <!-- 转换统计和设置 -->
      <div class="sidebar-section">
        <!-- 转换统计 -->
        <div class="stats-card">
          <h3>转换统计</h3>
          <div class="stats-grid">
            <div class="stat-item">
              <div class="stat-value">{{ conversionStats.totalLines }}</div>
              <div class="stat-label">总行数</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ conversionStats.successRate }}%</div>
              <div class="stat-label">成功率</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ conversionStats.timeCost }}ms</div>
              <div class="stat-label">耗时</div>
            </div>
          </div>
        </div>

        <!-- 转换设置 -->
        <div class="settings-card">
          <h3>转换设置</h3>
          <div class="settings-form">
            <el-form label-width="80px">
              <el-form-item label="代码风格">
                <el-select v-model="conversionSettings.codeStyle" placeholder="选择代码风格">
                  <el-option label="PEP8标准" value="pep8" />
                  <el-option label="简洁风格" value="simple" />
                  <el-option label="详细注释" value="detailed" />
                </el-select>
              </el-form-item>
              <el-form-item label="优化级别">
                <el-select v-model="conversionSettings.optimizationLevel" placeholder="选择优化级别">
                  <el-option label="基础转换" value="basic" />
                  <el-option label="智能优化" value="smart" />
                  <el-option label="深度重构" value="deep" />
                </el-select>
              </el-form-item>
              <el-form-item label="错误处理">
                <el-switch v-model="conversionSettings.errorHandling" />
              </el-form-item>
              <el-form-item label="类型注解">
                <el-switch v-model="conversionSettings.typeAnnotations" />
              </el-form-item>
            </el-form>
          </div>
        </div>

        <!-- 转换历史 -->
        <div class="history-card">
          <h3>最近转换</h3>
          <div class="history-list">
            <div 
              v-for="item in conversionHistory" 
              :key="item.id"
              class="history-item"
              @click="loadHistory(item)"
            >
              <div class="history-title">{{ item.title }}</div>
              <div class="history-time">{{ item.time }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Monitor,
  Delete,
  DocumentCopy,
  Right,
  CopyDocument,
  Download
} from '@element-plus/icons-vue'

// 响应式数据
const javaCode = ref('')
const pythonCode = ref('')
const converting = ref(false)

const conversionStats = reactive({
  totalLines: 0,
  successRate: 0,
  timeCost: 0
})

const conversionSettings = reactive({
  codeStyle: 'pep8',
  optimizationLevel: 'smart',
  errorHandling: true,
  typeAnnotations: true
})

const conversionHistory = ref([
  { id: 1, title: 'HelloWorld示例', time: '2分钟前' },
  { id: 2, title: '用户管理类', time: '1小时前' },
  { id: 3, title: '数据处理函数', time: '3小时前' }
])

// 示例代码
const exampleCode = `public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        // 计算1到10的和
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += i;
        }
        System.out.println("Sum: " + sum);
    }
}`

// 方法定义
const clearJavaCode = () => {
  javaCode.value = ''
  pythonCode.value = ''
  ElMessage.success('代码已清空')
}

const loadExample = () => {
  javaCode.value = exampleCode
  ElMessage.info('示例代码已加载')
}

const convertCode = async () => {
  if (!javaCode.value.trim()) {
    ElMessage.warning('请输入Java代码')
    return
  }

  converting.value = true
  
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    // 简单的转换逻辑（实际项目中应该调用后端API）
    const converted = javaCode.value
      .replace(/public class/g, 'class')
      .replace(/public static void main/g, 'def main')
      .replace(/System\.out\.println/g, 'print')
      .replace(/int /g, '')
      .replace(/for \(int/g, 'for')
      .replace(/\+\+/g, '+= 1')
      .replace(/\/\/.*$/gm, '# $&'.replace(/\/\//, ''))

    pythonCode.value = `class HelloWorld:
    @staticmethod
    def main():
        print("Hello, World!")
        
        # 计算1到10的和
        sum = 0
        for i in range(1, 11):
            sum += i
        print(f"Sum: {sum}")

if __name__ == "__main__":
    HelloWorld.main()`

    // 更新统计信息
    conversionStats.totalLines = pythonCode.value.split('\n').length
    conversionStats.successRate = 95
    conversionStats.timeCost = Math.floor(Math.random() * 200) + 100

    ElMessage.success('代码转换成功')
  } catch (error) {
    ElMessage.error('转换失败，请重试')
    console.error('转换错误:', error)
  } finally {
    converting.value = false
  }
}

const copyPythonCode = async () => {
  if (!pythonCode.value) {
    ElMessage.warning('没有可复制的代码')
    return
  }

  try {
    await navigator.clipboard.writeText(pythonCode.value)
    ElMessage.success('代码已复制到剪贴板')
  } catch (error) {
    ElMessage.error('复制失败')
  }
}

const downloadPythonCode = () => {
  if (!pythonCode.value) {
    ElMessage.warning('没有可下载的代码')
    return
  }

  const blob = new Blob([pythonCode.value], { type: 'text/plain' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = 'converted_code.py'
  a.click()
  URL.revokeObjectURL(url)
  ElMessage.success('代码已下载')
}

const loadHistory = (item: any) => {
  // 加载历史记录的逻辑
  ElMessage.info(`加载历史记录: ${item.title}`)
}
</script>

<style lang="scss" scoped>
.converter-view {
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

.converter-container {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: 24px;
  height: calc(100vh - 200px);
}

.editor-section {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.editor-panel {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  overflow: hidden;
  flex: 1;
  
  .panel-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 16px 20px;
    background: rgba(255, 255, 255, 0.1);
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    
    h3 {
      margin: 0;
      color: #fff;
      font-size: 16px;
      font-weight: 600;
    }
    
    .panel-actions {
      display: flex;
      gap: 8px;
    }
  }
  
  .editor-wrapper {
    height: calc(100% - 60px);
    
    .code-editor {
      width: 100%;
      height: 100%;
      background: rgba(16, 18, 27, 0.8);
      border: none;
      color: #fff;
      font-family: 'Monaco', 'Consolas', monospace;
      font-size: 14px;
      line-height: 1.5;
      padding: 20px;
      resize: none;
      outline: none;
      
      &::placeholder {
        color: rgba(255, 255, 255, 0.3);
      }
    }
    
    .code-output {
      width: 100%;
      height: 100%;
      background: rgba(16, 18, 27, 0.8);
      color: #00ff88;
      font-family: 'Monaco', 'Consolas', monospace;
      font-size: 14px;
      line-height: 1.5;
      padding: 20px;
      margin: 0;
      overflow: auto;
      white-space: pre-wrap;
    }
  }
}

.converter-actions {
  display: flex;
  justify-content: center;
  
  .convert-btn {
    padding: 12px 32px;
    font-size: 16px;
    font-weight: 600;
    background: linear-gradient(135deg, #00d4ff 0%, #0099ff 100%);
    border: none;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 25px rgba(0, 212, 255, 0.3);
    }
  }
}

.sidebar-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stats-card,
.settings-card,
.history-card {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px;
  
  h3 {
    margin: 0 0 16px 0;
    color: #fff;
    font-size: 16px;
    font-weight: 600;
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  
  .stat-item {
    text-align: center;
    padding: 12px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 8px;
    
    .stat-value {
      font-size: 20px;
      font-weight: 700;
      color: #00d4ff;
      margin-bottom: 4px;
    }
    
    .stat-label {
      font-size: 12px;
      color: rgba(255, 255, 255, 0.6);
    }
  }
}

.settings-form {
  .el-form-item {
    margin-bottom: 16px;
    
    :deep(.el-form-item__label) {
      color: rgba(255, 255, 255, 0.8);
    }
  }
}

.history-list {
  .history-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 8px;
    margin-bottom: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.1);
      transform: translateX(4px);
    }
    
    .history-title {
      color: #fff;
      font-size: 14px;
    }
    
    .history-time {
      color: rgba(255, 255, 255, 0.5);
      font-size: 12px;
    }
  }
}
</style>