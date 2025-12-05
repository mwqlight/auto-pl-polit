<template>
  <div class="learning-center-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">
        <el-icon><Reading /></el-icon>
        Python 核心语法学习中心
      </h1>
      <p class="page-desc">系统化学习Python编程语言，从基础到高级语法</p>
    </div>

    <!-- 学习中心主体 -->
    <div class="learning-container">
      <!-- 左侧导航 -->
      <div class="sidebar-nav">
        <div class="nav-section">
          <h3>基础语法</h3>
          <el-menu 
            :default-active="activeChapter"
            @select="handleChapterSelect"
            background-color="transparent"
            text-color="rgba(255,255,255,0.7)"
            active-text-color="#00d4ff"
          >
            <el-menu-item index="1">
              <el-icon><Document /></el-icon>
              <span>变量和数据类型</span>
            </el-menu-item>
            <el-menu-item index="2">
              <el-icon><Document /></el-icon>
              <span>运算符和表达式</span>
            </el-menu-item>
            <el-menu-item index="3">
              <el-icon><Document /></el-icon>
              <span>控制流程</span>
            </el-menu-item>
            <el-menu-item index="4">
              <el-icon><Document /></el-icon>
              <span>函数定义</span>
            </el-menu-item>
          </el-menu>
        </div>

        <div class="nav-section">
          <h3>高级特性</h3>
          <el-menu 
            :default-active="activeChapter"
            @select="handleChapterSelect"
            background-color="transparent"
            text-color="rgba(255,255,255,0.7)"
            active-text-color="#00d4ff"
          >
            <el-menu-item index="5">
              <el-icon><Document /></el-icon>
              <span>面向对象编程</span>
            </el-menu-item>
            <el-menu-item index="6">
              <el-icon><Document /></el-icon>
              <span>异常处理</span>
            </el-menu-item>
            <el-menu-item index="7">
              <el-icon><Document /></el-icon>
              <span>模块和包</span>
            </el-menu-item>
            <el-menu-item index="8">
              <el-icon><Document /></el-icon>
              <span>装饰器</span>
            </el-menu-item>
          </el-menu>
        </div>

        <div class="progress-section">
          <h3>学习进度</h3>
          <div class="progress-stats">
            <div class="progress-item">
              <div class="progress-label">完成度</div>
              <div class="progress-value">{{ progress.completion }}%</div>
              <el-progress 
                :percentage="progress.completion" 
                :show-text="false"
                color="#00d4ff"
              />
            </div>
            <div class="progress-item">
              <div class="progress-label">已学习</div>
              <div class="progress-value">{{ progress.learned }}/{{ progress.total }}</div>
            </div>
            <div class="progress-item">
              <div class="progress-label">学习时间</div>
              <div class="progress-value">{{ progress.timeSpent }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧内容区 -->
      <div class="content-area">
        <!-- 章节内容 -->
        <div class="chapter-content">
          <div class="chapter-header">
            <h2>{{ currentChapter.title }}</h2>
            <div class="chapter-actions">
              <el-button size="small" @click="toggleCodeExample">
        <el-icon><Code /></el-icon>
        {{ showCodeExample ? '隐藏代码' : '显示代码' }}
      </el-button>
              <el-button size="small" @click="runCodeExample" :loading="runningCode">
                <el-icon><VideoPlay /></el-icon>
                {{ runningCode ? '运行中...' : '运行代码' }}
              </el-button>
              <el-button size="small" @click="markAsComplete">
                <el-icon><Check /></el-icon>
                {{ currentChapter.completed ? '已学习' : '标记完成' }}
              </el-button>
            </div>
          </div>

          <div class="chapter-body">
            <!-- 理论内容 -->
            <div class="theory-section">
              <h3>概念讲解</h3>
              <div class="theory-content" v-html="currentChapter.theory"></div>
            </div>

            <!-- 代码示例 -->
            <div v-if="showCodeExample" class="code-section">
              <h3>代码示例</h3>
              <div class="code-editor">
                <pre class="code-display">{{ currentChapter.codeExample }}</pre>
              </div>
              
              <!-- 运行结果 -->
              <div v-if="codeResult" class="code-result">
                <h4>运行结果</h4>
                <pre class="result-output">{{ codeResult }}</pre>
              </div>
            </div>

            <!-- 对比学习 -->
            <div class="comparison-section">
              <h3>Java vs Python 对比</h3>
              <div class="comparison-grid">
                <div class="comparison-item">
                  <h4>Java 实现</h4>
                  <pre class="comparison-code">{{ currentChapter.javaComparison }}</pre>
                </div>
                <div class="comparison-item">
                  <h4>Python 实现</h4>
                  <pre class="comparison-code">{{ currentChapter.pythonComparison }}</pre>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 导航控制 -->
        <div class="navigation-controls">
          <el-button 
            :disabled="activeChapter <= 1"
            @click="goToPreviousChapter"
          >
            <el-icon><ArrowLeft /></el-icon>
            上一章
          </el-button>
          <el-button 
            type="primary"
            :disabled="activeChapter >= chapters.length"
            @click="goToNextChapter"
          >
            <el-icon><ArrowRight /></el-icon>
            下一章
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Reading,
  Document,
  Code,
  VideoPlay,
  Check,
  ArrowLeft,
  ArrowRight
} from '@element-plus/icons-vue'

// 响应式数据
const activeChapter = ref('1')
const showCodeExample = ref(true)
const runningCode = ref(false)
const codeResult = ref('')

const progress = reactive({
  completion: 25,
  learned: 2,
  total: 8,
  timeSpent: '2小时15分钟'
})

// 章节数据
const chapters = ref([
  {
    id: '1',
    title: '变量和数据类型',
    completed: true,
    theory: `
      <p>Python是一种动态类型语言，变量不需要显式声明类型。Python会自动推断变量的类型。</p>
      <h4>基本数据类型：</h4>
      <ul>
        <li><strong>整数(int)</strong>：如 42, -10, 0</li>
        <li><strong>浮点数(float)</strong>：如 3.14, -0.001, 2.0</li>
        <li><strong>字符串(str)</strong>：如 "hello", 'world', """多行字符串"""</li>
        <li><strong>布尔值(bool)</strong>：True 或 False</li>
        <li><strong>None</strong>：表示空值</li>
      </ul>
    `,
    codeExample: `# 变量定义和数据类型
# 整数
age = 25
count = -10

# 浮点数
price = 19.99
temperature = -5.5

# 字符串
name = "Alice"
message = 'Hello, World!'

# 布尔值
is_active = True
is_completed = False

# 类型检查
print(type(age))     # <class 'int'>
print(type(price))   # <class 'float'>
print(type(name))    # <class 'str'>
print(type(is_active)) # <class 'bool'>`,
    javaComparison: `// Java 变量定义
public class Variables {
    public static void main(String[] args) {
        // 必须声明类型
        int age = 25;
        double price = 19.99;
        String name = "Alice";
        boolean isActive = true;
        
        // 类型检查
        System.out.println(((Object)age).getClass().getName());
        System.out.println(((Object)price).getClass().getName());
        System.out.println(name.getClass().getName());
        System.out.println(((Object)isActive).getClass().getName());
    }
}`,
    pythonComparison: `# Python 变量定义
# 无需声明类型，自动推断
age = 25
price = 19.99
name = "Alice"
is_active = True

# 类型检查
print(type(age))
print(type(price))
print(type(name))
print(type(is_active))`
  },
  {
    id: '2',
    title: '运算符和表达式',
    completed: true,
    theory: `
      <p>Python支持丰富的运算符，包括算术运算符、比较运算符、逻辑运算符等。</p>
      <h4>主要运算符：</h4>
      <ul>
        <li><strong>算术运算符</strong>：+ - * / % ** //</li>
        <li><strong>比较运算符</strong>：== != > < >= <=</li>
        <li><strong>逻辑运算符</strong>：and or not</li>
        <li><strong>赋值运算符</strong>：= += -= *= /=</li>
      </ul>
    `,
    codeExample: `# 算术运算符
x = 10
y = 3

print(x + y)   # 13
print(x - y)   # 7
print(x * y)   # 30
print(x / y)   # 3.333...
print(x % y)   # 1
print(x ** y)  # 1000
print(x // y)  # 3

# 比较运算符
print(x == y)  # False
print(x != y)  # True
print(x > y)   # True

# 逻辑运算符
a = True
b = False
print(a and b) # False
print(a or b)  # True
print(not a)   # False`,
    javaComparison: `// Java 运算符
public class Operators {
    public static void main(String[] args) {
        int x = 10;
        int y = 3;
        
        // 算术运算符
        System.out.println(x + y);  // 13
        System.out.println(x - y);  // 7
        System.out.println(x * y);  // 30
        System.out.println(x / y);  // 3 (整数除法)
        System.out.println(x % y);  // 1
        
        // 比较运算符
        System.out.println(x == y); // false
        System.out.println(x != y); // true
        
        // 逻辑运算符
        boolean a = true;
        boolean b = false;
        System.out.println(a && b); // false
        System.out.println(a || b); // true
        System.out.println(!a);     // false
    }
}`,
    pythonComparison: `# Python 运算符
x = 10
y = 3

# 算术运算符
print(x + y)   # 13
print(x - y)   # 7
print(x * y)   # 30
print(x / y)   # 3.333... (浮点数除法)
print(x % y)   # 1
print(x ** y)  # 1000 (幂运算)
print(x // y)  # 3 (整数除法)

# 比较运算符
print(x == y)  # False
print(x != y)  # True

# 逻辑运算符
a = True
b = False
print(a and b) # False
print(a or b)  # True
print(not a)   # False`
  }
  // 其他章节数据...
])

// 计算属性
const currentChapter = computed(() => {
  return chapters.value.find(chapter => chapter.id === activeChapter.value) || chapters.value[0]
})

// 方法定义
const handleChapterSelect = (index: string) => {
  activeChapter.value = index
  codeResult.value = ''
}

const toggleCodeExample = () => {
  showCodeExample.value = !showCodeExample.value
}

const runCodeExample = async () => {
  runningCode.value = true
  
  try {
    // 模拟代码执行
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // 根据当前章节生成不同的运行结果
    switch (activeChapter.value) {
      case '1':
        codeResult.value = `<class 'int'>\n<class 'float'>\n<class 'str'>\n<class 'bool'>`
        break
      case '2':
        codeResult.value = `13\n7\n30\n3.3333333333333335\n1\n1000\n3\nFalse\nTrue\nTrue\nFalse\nTrue\nFalse`
        break
      default:
        codeResult.value = '代码执行成功！'
    }
    
    ElMessage.success('代码执行完成')
  } catch (error) {
    ElMessage.error('代码执行失败')
    console.error('执行错误:', error)
  } finally {
    runningCode.value = false
  }
}

const markAsComplete = () => {
  const chapter = currentChapter.value
  chapter.completed = !chapter.completed
  
  if (chapter.completed) {
    ElMessage.success('章节标记为已完成')
    // 更新进度
    progress.learned = chapters.value.filter(c => c.completed).length
    progress.completion = Math.round((progress.learned / progress.total) * 100)
  } else {
    ElMessage.info('章节标记为未完成')
  }
}

const goToPreviousChapter = () => {
  const current = parseInt(activeChapter.value)
  if (current > 1) {
    activeChapter.value = (current - 1).toString()
    codeResult.value = ''
  }
}

const goToNextChapter = () => {
  const current = parseInt(activeChapter.value)
  if (current < chapters.value.length) {
    activeChapter.value = (current + 1).toString()
    codeResult.value = ''
  }
}
</script>

<style lang="scss" scoped>
.learning-center-view {
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

.learning-container {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 24px;
  height: calc(100vh - 200px);
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 20px;
  
  .nav-section {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
    padding: 16px;
    
    h3 {
      color: #fff;
      font-size: 16px;
      font-weight: 600;
      margin: 0 0 12px 0;
    }
    
    .el-menu {
      border: none;
      
      .el-menu-item {
        height: 40px;
        line-height: 40px;
        margin-bottom: 4px;
        border-radius: 6px;
        
        &.is-active {
          background: rgba(0, 212, 255, 0.2);
        }
        
        &:hover {
          background: rgba(255, 255, 255, 0.1);
        }
      }
    }
  }
  
  .progress-section {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
    padding: 16px;
    
    h3 {
      color: #fff;
      font-size: 16px;
      font-weight: 600;
      margin: 0 0 16px 0;
    }
    
    .progress-stats {
      .progress-item {
        margin-bottom: 16px;
        
        .progress-label {
          color: rgba(255, 255, 255, 0.7);
          font-size: 12px;
          margin-bottom: 4px;
        }
        
        .progress-value {
          color: #00d4ff;
          font-size: 18px;
          font-weight: 600;
          margin-bottom: 8px;
        }
      }
    }
  }
}

.content-area {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.chapter-content {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 24px;
  flex: 1;
  overflow-y: auto;
  
  .chapter-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 24px;
    
    h2 {
      color: #fff;
      font-size: 24px;
      font-weight: 700;
      margin: 0;
    }
    
    .chapter-actions {
      display: flex;
      gap: 8px;
    }
  }
  
  .chapter-body {
    .theory-section {
      margin-bottom: 24px;
      
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .theory-content {
        color: rgba(255, 255, 255, 0.8);
        line-height: 1.6;
        
        h4 {
          color: #fff;
          margin: 16px 0 8px 0;
        }
        
        ul {
          margin: 8px 0;
          padding-left: 20px;
          
          li {
            margin-bottom: 4px;
          }
        }
      }
    }
    
    .code-section {
      margin-bottom: 24px;
      
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .code-editor {
        background: rgba(16, 18, 27, 0.8);
        border-radius: 8px;
        padding: 16px;
        margin-bottom: 16px;
        
        .code-display {
          color: #00ff88;
          font-family: 'Monaco', 'Consolas', monospace;
          font-size: 14px;
          line-height: 1.5;
          margin: 0;
          white-space: pre-wrap;
        }
      }
      
      .code-result {
        background: rgba(16, 18, 27, 0.8);
        border-radius: 8px;
        padding: 16px;
        
        h4 {
          color: #fff;
          font-size: 14px;
          font-weight: 600;
          margin: 0 0 8px 0;
        }
        
        .result-output {
          color: #ffd700;
          font-family: 'Monaco', 'Consolas', monospace;
          font-size: 14px;
          line-height: 1.5;
          margin: 0;
          white-space: pre-wrap;
        }
      }
    }
    
    .comparison-section {
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .comparison-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 16px;
        
        .comparison-item {
          h4 {
            color: #fff;
            font-size: 14px;
            font-weight: 600;
            margin: 0 0 8px 0;
          }
          
          .comparison-code {
            background: rgba(16, 18, 27, 0.8);
            border-radius: 8px;
            padding: 16px;
            color: #ff6b6b;
            font-family: 'Monaco', 'Consolas', monospace;
            font-size: 12px;
            line-height: 1.4;
            margin: 0;
            white-space: pre-wrap;
            max-height: 200px;
            overflow-y: auto;
          }
        }
      }
    }
  }
}

.navigation-controls {
  display: flex;
  justify-content: space-between;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 16px 24px;
  
  .el-button {
    padding: 10px 20px;
    
    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}
</style>