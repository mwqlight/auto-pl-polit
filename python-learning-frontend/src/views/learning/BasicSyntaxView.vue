<template>
  <div class="basic-syntax-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">Python基础语法</h2>
      <p class="module-description">学习Python编程的基础语法和核心概念</p>
    </div>

    <!-- 课程列表 -->
    <div class="lessons-container">
      <el-card 
        v-for="lesson in lessons" 
        :key="lesson.id"
        class="lesson-card"
        shadow="hover"
        :class="{ 'completed': lesson.completed, 'active': lesson.id === activeLesson }"
        @click="selectLesson(lesson.id)"
      >
        <div class="lesson-header">
          <div class="lesson-icon">
            <i :class="lesson.icon"></i>
          </div>
          <div class="lesson-info">
            <h3 class="lesson-title">{{ lesson.title }}</h3>
            <p class="lesson-desc">{{ lesson.description }}</p>
          </div>
          <div class="lesson-status">
            <el-tag 
              v-if="lesson.completed" 
              type="success" 
              size="small"
            >
              已完成
            </el-tag>
            <el-tag 
              v-else-if="lesson.id === activeLesson" 
              type="primary" 
              size="small"
            >
              学习中
            </el-tag>
            <el-tag v-else type="info" size="small">
              未开始
            </el-tag>
          </div>
        </div>
        
        <div class="lesson-progress">
          <el-progress 
            :percentage="lesson.progress" 
            :show-text="false"
            :stroke-width="4"
          />
        </div>
      </el-card>
    </div>

    <!-- 课程内容 -->
    <div v-if="currentLesson" class="lesson-content">
      <el-card shadow="never">
        <template #header>
          <div class="content-header">
            <h3>{{ currentLesson.title }}</h3>
            <div class="content-actions">
              <el-button 
                type="primary" 
                size="small"
                @click="markAsCompleted"
                :disabled="currentLesson.completed"
              >
                {{ currentLesson.completed ? '已完成' : '标记完成' }}
              </el-button>
            </div>
          </div>
        </template>

        <!-- 理论内容 -->
        <div class="theory-section">
          <h4>理论讲解</h4>
          <div class="theory-content" v-html="currentLesson.theory"></div>
        </div>

        <!-- 代码示例 -->
        <div class="code-section">
          <h4>代码示例</h4>
          <div class="code-example">
            <pre><code class="language-python">{{ currentLesson.codeExample }}</code></pre>
            <div class="code-actions">
              <el-button 
                type="primary" 
                size="small"
                @click="runCode"
              >
                运行代码
              </el-button>
              <el-button size="small" @click="copyCode">
                复制代码
              </el-button>
            </div>
          </div>
        </div>

        <!-- 练习题目 -->
        <div class="exercise-section">
          <h4>练习题目</h4>
          <div class="exercise-content">
            <p>{{ currentLesson.exercise }}</p>
            <div class="exercise-actions">
              <el-button type="success" size="small" @click="showSolution">
                查看答案
              </el-button>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { learningApi } from '@/api/modules/learning'
import type { Lesson } from '@/types/api'

interface Lesson {
  id: string
  title: string
  description: string
  icon: string
  completed: boolean
  progress: number
  theory: string
  codeExample: string
  exercise: string
  solution: string
}

// 响应式数据
const activeLesson = ref<number | null>(null)
const lessons = ref<Lesson[]>([])
const loading = ref(false)

// 加载课程数据
const loadLessons = async () => {
  loading.value = true
  try {
    const response = await learningApi.getAllLessons(1)
    if (response.code === 200 && response.data) {
      lessons.value = response.data
      if (lessons.value.length > 0) {
        activeLesson.value = lessons.value[0].id
      }
    } else {
      ElMessage.error('获取课程列表失败')
    }
  } catch (error) {
    console.error('加载课程失败:', error)
    ElMessage.error('加载课程失败')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取课程
onMounted(() => {
  loadLessons()
})
    id: 'operators',
    title: '运算符',
    description: '学习Python中的算术、比较和逻辑运算符',
    icon: 'el-icon-c-scale-to-original',
    completed: true,
    progress: 100,
    theory: `
      <p>Python支持多种运算符：</p>
      <p><strong>算术运算符：</strong> +, -, *, /, %, **, //</p>
      <p><strong>比较运算符：</strong> ==, !=, >, <, >=, <=</p>
      <p><strong>逻辑运算符：</strong> and, or, not</p>
      <p><strong>赋值运算符：</strong> =, +=, -=, *=, /=</p>
    `,
    codeExample: `# 算术运算符示例
x = 10
y = 3

print(f"x + y = {x + y}")  # 加法
print(f"x - y = {x - y}")  # 减法
print(f"x * y = {x * y}")  # 乘法
print(f"x / y = {x / y}")  # 除法
print(f"x % y = {x % y}")  # 取模
print(f"x ** y = {x ** y}")  # 幂运算
print(f"x // y = {x // y}")  # 整除

# 比较运算符
print(f"x == y: {x == y}")
print(f"x != y: {x != y}")
print(f"x > y: {x > y}")

# 逻辑运算符
a = True
b = False
print(f"a and b: {a and b}")
print(f"a or b: {a or b}")
print(f"not a: {not a}")`,
    exercise: '编写一个程序，计算圆的面积和周长，给定半径为5。',
    solution: `import math

radius = 5
area = math.pi * radius ** 2
circumference = 2 * math.pi * radius

print(f"圆的面积: {area:.2f}")
print(f"圆的周长: {circumference:.2f}")`
  },
  {
    id: 'control-flow',
    title: '控制流程',
    description: '学习条件语句和循环语句',
    icon: 'el-icon-s-operation',
    completed: false,
    progress: 60,
    theory: `
      <p>Python中的控制流程包括条件语句和循环语句：</p>
      <p><strong>条件语句：</strong></p>
      <ul>
        <li><code>if</code> - 如果条件为真执行</li>
        <li><code>elif</code> - 否则如果条件为真执行</li>
        <li><code>else</code> - 否则执行</li>
      </ul>
      <p><strong>循环语句：</strong></p>
      <ul>
        <li><code>for</code> - 遍历序列</li>
        <li><code>while</code> - 当条件为真时循环</li>
      </ul>
    `,
    codeExample: `# 条件语句示例
score = 85

if score >= 90:
    grade = "A"
elif score >= 80:
    grade = "B"
elif score >= 70:
    grade = "C"
else:
    grade = "D"

print(f"分数: {score}, 等级: {grade}")

# for循环示例
fruits = ["苹果", "香蕉", "橙子"]
for fruit in fruits:
    print(f"我喜欢吃{fruit}")

# while循环示例
count = 1
while count <= 5:
    print(f"计数: {count}")
    count += 1`,
    exercise: '编写一个程序，找出1到100之间所有能被3和5同时整除的数。',
    solution: `for i in range(1, 101):
    if i % 3 == 0 and i % 5 == 0:
        print(i)`
  },
  {
    id: 'functions',
    title: '函数',
    description: '学习如何定义和调用函数',
    icon: 'el-icon-s-marketing',
    completed: false,
    progress: 0,
    theory: `
      <p>函数是组织代码的重要方式，可以重复使用代码块。</p>
      <p><strong>函数定义语法：</strong></p>
      <pre><code>def function_name(parameters):
    """函数文档字符串"""
    # 函数体
    return result</code></pre>
      <p><strong>函数参数类型：</strong></p>
      <ul>
        <li>位置参数</li>
        <li>默认参数</li>
        <li>关键字参数</li>
        <li>可变参数</li>
      </ul>
    `,
    codeExample: `# 函数定义示例
def greet(name, greeting="Hello"):
    """打招呼函数"""
    return f"{greeting}, {name}!"

# 调用函数
print(greet("张三"))
print(greet("李四", "Hi"))

# 计算阶乘的函数
def factorial(n):
    """计算n的阶乘"""
    if n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)

print(f"5的阶乘是: {factorial(5)}")`,
    exercise: '编写一个函数，接收两个数字参数，返回它们的和、差、积、商。',
    solution: `def calculate(a, b):
    """计算两个数的四则运算"""
    return {
        'sum': a + b,
        'difference': a - b,
        'product': a * b,
        'quotient': a / b if b != 0 else 'undefined'
    }

result = calculate(10, 2)
print(result)`
  }
])

// 计算属性
const currentLesson = computed(() => {
  return lessons.value.find(lesson => lesson.id === activeLesson.value)
})

// 选择课程
const selectLesson = (lessonId: number) => {
  activeLesson.value = lessonId
}

// 标记为完成
const markAsCompleted = async () => {
  if (currentLesson.value) {
    try {
      await ElMessageBox.confirm(
        '确定要标记此课程为已完成吗？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
      await learningApi.updateProgress(currentLesson.value.id, 100)
      currentLesson.value.completed = true
      currentLesson.value.progress = 100
      ElMessage.success('课程已标记为已完成')
    } catch (error: any) {
      if (error !== 'cancel') {
        console.error('标记课程完成失败:', error)
        ElMessage.error('标记课程完成失败')
      }
    }
  }
}

// 运行代码
const runCode = () => {
  if (currentLesson.value) {
    // 这里可以调用后端API运行代码
    ElMessage.success('代码运行成功！')
  }
}

// 复制代码
const copyCode = () => {
  if (currentLesson.value) {
    navigator.clipboard.writeText(currentLesson.value.codeExample || '')
    ElMessage.success('代码已复制到剪贴板')
  }
}

// 显示答案
const showSolution = () => {
  if (currentLesson.value) {
    ElMessage.info('答案已显示在练习区域')
  }
}
</script>

<style scoped lang="scss">
.basic-syntax-container {
  padding: 20px;
}

.module-header {
  text-align: center;
  margin-bottom: 30px;
  
  .module-title {
    font-size: 24px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin-bottom: 8px;
  }
  
  .module-description {
    font-size: 16px;
    color: var(--el-text-color-secondary);
    margin: 0;
  }
}

.lessons-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.lesson-card {
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  &.active {
    border-color: var(--el-color-primary);
  }
  
  &.completed {
    opacity: 0.8;
  }
  
  .lesson-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    
    .lesson-icon {
      width: 40px;
      height: 40px;
      background: var(--el-color-primary-light-9);
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 15px;
      
      i {
        font-size: 20px;
        color: var(--el-color-primary);
      }
    }
    
    .lesson-info {
      flex: 1;
      
      .lesson-title {
        font-size: 16px;
        font-weight: 600;
        margin: 0 0 4px 0;
        color: var(--el-text-color-primary);
      }
      
      .lesson-desc {
        font-size: 14px;
        color: var(--el-text-color-secondary);
        margin: 0;
      }
    }
  }
  
  .lesson-progress {
    margin-top: 10px;
  }
}

.lesson-content {
  .content-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    h3 {
      margin: 0;
      font-size: 18px;
      font-weight: 600;
    }
  }
  
  .theory-section, .code-section, .exercise-section {
    margin-bottom: 30px;
    
    h4 {
      font-size: 16px;
      font-weight: 600;
      margin-bottom: 15px;
      color: var(--el-text-color-primary);
      border-left: 4px solid var(--el-color-primary);
      padding-left: 10px;
    }
  }
  
  .theory-content {
    line-height: 1.6;
    
    ul {
      margin: 10px 0;
      padding-left: 20px;
    }
    
    li {
      margin: 5px 0;
    }
    
    code {
      background: var(--el-fill-color-light);
      padding: 2px 6px;
      border-radius: 3px;
      font-family: 'Courier New', monospace;
    }
  }
  
  .code-example {
    pre {
      background: #f6f8fa;
      border-radius: 6px;
      padding: 16px;
      overflow-x: auto;
      margin-bottom: 15px;
      
      code {
        font-family: 'Courier New', monospace;
        font-size: 14px;
        line-height: 1.4;
        color: #24292e;
      }
    }
    
    .code-actions {
      display: flex;
      gap: 10px;
    }
  }
  
  .exercise-content {
    p {
      line-height: 1.6;
      margin-bottom: 15px;
    }
  }
}

@media (max-width: 768px) {
  .lessons-container {
    grid-template-columns: 1fr;
  }
  
  .lesson-card {
    .lesson-header {
      .lesson-icon {
        width: 30px;
        height: 30px;
        margin-right: 10px;
        
        i {
          font-size: 16px;
        }
      }
    }
  }
}
</style>