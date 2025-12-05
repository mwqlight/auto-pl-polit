<template>
  <div class="functional-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">函数式编程</h2>
      <p class="module-description">学习Python中的高阶函数、lambda表达式、函数式编程概念</p>
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
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'

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
const activeLesson = ref('lambda')
const lessons = ref<Lesson[]>([
  {
    id: 'lambda',
    title: 'Lambda表达式',
    description: '学习匿名函数和Lambda表达式的使用',
    icon: 'el-icon-s-operation',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>Lambda表达式</strong>是一种创建匿名函数的简洁方式。</p>
      <p><strong>语法：</strong></p>
      <pre><code>lambda arguments: expression</code></pre>
      <p><strong>特点：</strong></p>
      <ul>
        <li>只能包含一个表达式</li>
        <li>不能包含复杂的语句（如if、for等）</li>
        <li>自动返回表达式的结果</li>
        <li>适合简单的函数逻辑</li>
      </ul>
      <p><strong>使用场景：</strong></p>
      <ul>
        <li>作为高阶函数的参数</li>
        <li>简单的数据处理</li>
        <li>临时函数定义</li>
      </ul>
    `,
    codeExample: `# Lambda表达式基础
# 定义一个简单的lambda函数
add = lambda x, y: x + y
print(f"3 + 5 = {add(3, 5)}")

# 直接调用lambda
result = (lambda x: x ** 2)(4)
print(f"4的平方: {result}")

# 使用lambda作为参数
numbers = [1, 2, 3, 4, 5, 6]

# 使用lambda过滤偶数
even_numbers = list(filter(lambda x: x % 2 == 0, numbers))
print(f"偶数: {even_numbers}")

# 使用lambda对每个元素平方
squared_numbers = list(map(lambda x: x ** 2, numbers))
print(f"平方: {squared_numbers}")

# 使用lambda排序
students = [
    {"name": "张三", "score": 85},
    {"name": "李四", "score": 92},
    {"name": "王五", "score": 78}
]

# 按分数降序排序
students_sorted = sorted(students, key=lambda s: s['score'], reverse=True)
print("按分数排序:")
for student in students_sorted:
    print(f"  {student['name']}: {student['score']}")

# 复杂的lambda示例
# 计算字符串中元音字母的数量
text = "Hello, World! How are you?"
vowel_count = sum(map(lambda c: 1 if c.lower() in 'aeiou' else 0, text))
print(f"文本中的元音字母数量: {vowel_count}")

# 使用lambda实现简单的计算器
calculator = {
    'add': lambda x, y: x + y,
    'subtract': lambda x, y: x - y,
    'multiply': lambda x, y: x * y,
    'divide': lambda x, y: x / y if y != 0 else 'Error'
}

print(f"10 + 5 = {calculator['add'](10, 5)}")
print(f"10 - 5 = {calculator['subtract'](10, 5)}")
print(f"10 * 5 = {calculator['multiply'](10, 5)}")
print(f"10 / 5 = {calculator['divide'](10, 5)}")`,
    exercise: '使用lambda表达式，对一个数字列表进行以下操作：1. 过滤出大于10的数字 2. 对每个数字乘以2 3. 计算所有数字的和',
    solution: `numbers = [5, 12, 8, 15, 3, 20, 7]

# 过滤大于10的数字
filtered = list(filter(lambda x: x > 10, numbers))
print(f"大于10的数字: {filtered}")

# 每个数字乘以2
doubled = list(map(lambda x: x * 2, numbers))
print(f"乘以2后的数字: {doubled}")

# 计算所有数字的和
total = sum(numbers)
print(f"所有数字的和: {total}")

# 一行代码完成所有操作
result = sum(map(lambda x: x * 2, filter(lambda x: x > 10, numbers)))
print(f"大于10的数字乘以2后的和: {result}")`
  },
  {
    id: 'higher-order',
    title: '高阶函数',
    description: '学习map、filter、reduce等高阶函数',
    icon: 'el-icon-s-marketing',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>高阶函数（Higher-order Function）</strong>是指能够接受函数作为参数或返回函数的函数。</p>
      <p><strong>常用的高阶函数：</strong></p>
      <ul>
        <li><code>map(func, iterable)</code>：对可迭代对象的每个元素应用函数</li>
        <li><code>filter(func, iterable)</code>：过滤可迭代对象中满足条件的元素</li>
        <li><code>reduce(func, iterable)</code>：对可迭代对象进行累积计算</li>
        <li><code>sorted(iterable, key=func)</code>：根据函数返回的值进行排序</li>
      </ul>
      <p><strong>函数式编程优势：</strong></p>
      <ul>
        <li>代码更简洁</li>
        <li>易于并行处理</li>
        <li>减少副作用</li>
        <li>提高代码可读性</li>
      </ul>
    `,
    codeExample: `from functools import reduce

# map函数示例
numbers = [1, 2, 3, 4, 5]

# 使用map计算平方
squared = list(map(lambda x: x ** 2, numbers))
print(f"平方: {squared}")

# 使用map转换数据类型
strings = ['1', '2', '3', '4', '5']
integers = list(map(int, strings))
print(f"转换为整数: {integers}")

# filter函数示例
# 过滤偶数
even_numbers = list(filter(lambda x: x % 2 == 0, numbers))
print(f"偶数: {even_numbers}")

# 过滤长度大于3的字符串
words = ['cat', 'window', 'defenestrate', 'dog']
long_words = list(filter(lambda word: len(word) > 3, words))
print(f"长度大于3的单词: {long_words}")

# reduce函数示例
# 计算乘积
product = reduce(lambda x, y: x * y, numbers)
print(f"1-5的乘积: {product}")

# 找出最大值
max_number = reduce(lambda x, y: x if x > y else y, numbers)
print(f"最大值: {max_number}")

# 字符串连接
words = ['Hello', 'World', 'Python']
concatenated = reduce(lambda x, y: x + ' ' + y, words)
print(f"连接后的字符串: {concatenated}")

# sorted函数示例
# 按字符串长度排序
words = ['apple', 'banana', 'cherry', 'date']
sorted_by_length = sorted(words, key=lambda x: len(x))
print(f"按长度排序: {sorted_by_length}")

# 按最后一个字符排序
sorted_by_last_char = sorted(words, key=lambda x: x[-1])
print(f"按最后一个字符排序: {sorted_by_last_char}")

# 复杂示例：处理学生数据
students = [
    {"name": "张三", "age": 20, "score": 85},
    {"name": "李四", "age": 22, "score": 92},
    {"name": "王五", "age": 19, "score": 78},
    {"name": "赵六", "age": 21, "score": 88}
]

# 过滤年龄大于20的学生
adult_students = list(filter(lambda s: s['age'] > 20, students))
print("年龄大于20的学生:")
for student in adult_students:
    print(f"  {student['name']}: {student['age']}岁")

# 提取所有学生的分数
scores = list(map(lambda s: s['score'], students))
print(f"所有学生的分数: {scores}")

# 计算平均分
average_score = reduce(lambda x, y: x + y, scores) / len(scores)
print(f"平均分: {average_score:.2f}")

# 按分数降序排序
sorted_students = sorted(students, key=lambda s: s['score'], reverse=True)
print("按分数降序排序:")
for student in sorted_students:
    print(f"  {student['name']}: {student['score']}分")`,
    exercise: '使用高阶函数处理一个员工列表：1. 过滤出薪资大于5000的员工 2. 提取员工姓名 3. 计算平均薪资 4. 按薪资降序排序',
    solution: `from functools import reduce

employees = [
    {"name": "张三", "salary": 6000},
    {"name": "李四", "salary": 4500},
    {"name": "王五", "salary": 8000},
    {"name": "赵六", "salary": 5500},
    {"name": "孙七", "salary": 3000}
]

# 1. 过滤出薪资大于5000的员工
high_salary_employees = list(filter(lambda e: e['salary'] > 5000, employees))
print(f"薪资大于5000的员工: {high_salary_employees}")

# 2. 提取员工姓名
names = list(map(lambda e: e['name'], employees))
print(f"所有员工姓名: {names}")

# 3. 计算平均薪资
salaries = list(map(lambda e: e['salary'], employees))
average_salary = reduce(lambda x, y: x + y, salaries) / len(salaries)
print(f"平均薪资: {average_salary:.2f}")

# 4. 按薪资降序排序
sorted_employees = sorted(employees, key=lambda e: e['salary'], reverse=True)
print("按薪资降序排序:")
for emp in sorted_employees:
    print(f"  {emp['name']}: {emp['salary']}元")`
  },
  {
    id: 'closures',
    title: '闭包和装饰器',
    description: '学习闭包概念和装饰器的使用',
    icon: 'el-icon-s-release',
    completed: false,
    progress: 60,
    theory: `
      <p><strong>闭包（Closure）</strong>是指一个函数能够访问并记住其词法作用域中的变量，即使该函数在其作用域之外执行。</p>
      <p><strong>装饰器（Decorator）</strong>是一种特殊的闭包，用于修改或增强函数的行为。</p>
      <p><strong>闭包的特点：</strong></p>
      <ul>
        <li>内部函数可以访问外部函数的变量</li>
        <li>外部函数返回内部函数</li>
        <li>内部函数保持对外部变量的引用</li>
      </ul>
      <p><strong>装饰器的语法：</strong></p>
      <pre><code>@decorator
def function():
    pass</code></pre>
    `,
    codeExample: `# 闭包示例
def outer_function(x):
    """外部函数"""
    def inner_function(y):
        """内部函数（闭包）"""
        return x + y
    return inner_function

# 创建闭包
add_five = outer_function(5)
add_ten = outer_function(10)

# 使用闭包
print(f"5 + 3 = {add_five(3)}")
print(f"10 + 3 = {add_ten(3)}")

# 更复杂的闭包示例
def make_multiplier(factor):
    """创建乘法器闭包"""
    def multiplier(number):
        return number * factor
    return multiplier

double = make_multiplier(2)
triple = make_multiplier(3)

print(f"双倍: {double(5)}")
print(f"三倍: {triple(5)}")

# 装饰器示例
# 简单的装饰器
def simple_decorator(func):
    """简单的装饰器，在函数执行前后打印信息"""
    def wrapper():
        print("函数开始执行...")
        result = func()
        print("函数执行完成!")
        return result
    return wrapper

@simple_decorator
def say_hello():
    """被装饰的函数"""
    print("Hello, World!")

say_hello()

# 带参数的装饰器
def timer_decorator(func):
    """计时装饰器"""
    import time
    def wrapper(*args, **kwargs):
        start_time = time.time()
        result = func(*args, **kwargs)
        end_time = time.time()
        print(f"函数 {func.__name__} 执行时间: {end_time - start_time:.4f}秒")
        return result
    return wrapper

@timer_decorator
def slow_function():
    """模拟耗时函数"""
    import time
    time.sleep(1)
    return "完成!"

result = slow_function()
print(result)

# 带参数的装饰器
def repeat(times):
    """重复执行装饰器"""
    def decorator(func):
        def wrapper(*args, **kwargs):
            for i in range(times):
                print(f"第 {i+1} 次执行:")
                result = func(*args, **kwargs)
            return result
        return wrapper
    return decorator

@repeat(3)
def greet(name):
    print(f"Hello, {name}!")

greet("Python")

# 类装饰器
class Logger:
    """日志装饰器类"""
    def __init__(self, func):
        self.func = func
    
    def __call__(self, *args, **kwargs):
        print(f"调用函数: {self.func.__name__}")
        print(f"参数: {args}, {kwargs}")
        result = self.func(*args, **kwargs)
        print(f"返回值: {result}")
        return result

@Logger
def add_numbers(a, b):
    return a + b

result = add_numbers(3, 5)
print(f"最终结果: {result}")`,
    exercise: '创建一个装饰器，用于缓存函数的计算结果，避免重复计算。',
    solution: `def cache_decorator(func):
    """缓存装饰器"""
    cache = {}
    
    def wrapper(*args):
        # 将参数作为缓存键
        key = args
        
        if key in cache:
            print(f"从缓存中获取结果: {cache[key]}")
            return cache[key]
        else:
            result = func(*args)
            cache[key] = result
            print(f"计算结果并缓存: {result}")
            return result
    
    return wrapper

@cache_decorator
def fibonacci(n):
    """计算斐波那契数列"""
    if n <= 1:
        return n
    return fibonacci(n-1) + fibonacci(n-2)

# 测试缓存装饰器
print(fibonacci(5))
print(fibonacci(5))  # 这次应该从缓存中获取
print(fibonacci(10))`
  },
  {
    id: 'generators',
    title: '生成器和迭代器',
    description: '学习生成器表达式和yield关键字',
    icon: 'el-icon-s-promotion',
    completed: false,
    progress: 0,
    theory: `
      <p><strong>生成器（Generator）</strong>是一种特殊的迭代器，可以按需生成值，而不是一次性生成所有值。</p>
      <p><strong>生成器的创建方式：</strong></p>
      <ul>
        <li>生成器表达式：类似列表推导式，但使用圆括号</li>
        <li>生成器函数：使用<code>yield</code>关键字</li>
      </ul>
      <p><strong>生成器的优势：</strong></p>
      <ul>
        <li>内存效率高</li>
        <li>可以处理无限序列</li>
        <li>支持惰性求值</li>
      </ul>
      <p><strong>yield关键字：</strong>用于暂停函数执行并返回一个值，下次调用时从暂停处继续执行。</p>
    `,
    codeExample: `# 生成器表达式示例
# 列表推导式（一次性生成所有值）
list_comp = [x ** 2 for x in range(5)]
print(f"列表推导式: {list_comp}")

# 生成器表达式（按需生成值）
gen_exp = (x ** 2 for x in range(5))
print(f"生成器表达式: {gen_exp}")
print(f"生成器值: {list(gen_exp)}")

# 生成器函数示例
def simple_generator():
    """简单的生成器函数"""
    yield 1
    yield 2
    yield 3

# 使用生成器
gen = simple_generator()
print(next(gen))  # 输出: 1
print(next(gen))  # 输出: 2
print(next(gen))  # 输出: 3

# 使用for循环遍历生成器
for value in simple_generator():
    print(value)

# 无限序列生成器
def infinite_counter():
    """无限计数器"""
    count = 0
    while True:
        yield count
        count += 1

# 使用有限次数的无限生成器
counter = infinite_counter()
for i in range(5):
    print(next(counter))

# 斐波那契数列生成器
def fibonacci_generator(limit):
    """斐波那契数列生成器"""
    a, b = 0, 1
    count = 0
    while count < limit:
        yield a
        a, b = b, a + b
        count += 1

print("斐波那契数列:")
for num in fibonacci_generator(10):
    print(num, end=" ")
print()

# 文件读取生成器
def read_large_file(filename):
    """逐行读取大文件"""
    with open(filename, 'r', encoding='utf-8') as file:
        for line in file:
            yield line.strip()

# 模拟文件读取
lines = ["第一行", "第二行", "第三行", "第四行"]

# 使用生成器处理大文件
line_generator = (line for line in lines)
for line in line_generator:
    print(line)

# 生成器表达式的高级用法
# 过滤和转换
numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

# 生成偶数的平方
even_squares = (x ** 2 for x in numbers if x % 2 == 0)
print(f"偶数的平方: {list(even_squares)}")

# 链式生成器
def chain_generators():
    """链式生成器示例"""
    # 第一个生成器
    for i in range(3):
        yield i
    
    # 第二个生成器
    for i in range(3, 6):
        yield i

print("链式生成器:")
for value in chain_generators():
    print(value, end=" ")
print()

# yield from 语法（Python 3.3+）
def combined_generator():
    """使用yield from组合生成器"""
    yield from range(3)
    yield from range(3, 6)
    yield from range(6, 9)

print("组合生成器:")
for value in combined_generator():
    print(value, end=" ")
print()`,
    exercise: '创建一个生成器，生成指定范围内的素数。',
    solution: `def is_prime(n):
    """判断是否为素数"""
    if n < 2:
        return False
    for i in range(2, int(n ** 0.5) + 1):
        if n % i == 0:
            return False
    return True

def prime_generator(limit):
    """素数生成器"""
    for num in range(2, limit + 1):
        if is_prime(num):
            yield num

# 生成100以内的素数
print("100以内的素数:")
primes = list(prime_generator(100))
print(primes)

# 使用生成器表达式
prime_gen = (num for num in range(2, 101) if is_prime(num))
print("使用生成器表达式:")
for prime in prime_gen:
    print(prime, end=" ")
print()`
  }
])

// 计算当前选中的课程
const currentLesson = computed(() => {
  return lessons.value.find(lesson => lesson.id === activeLesson.value)
})

// 选择课程
const selectLesson = (lessonId: string) => {
  activeLesson.value = lessonId
}

// 标记为完成
const markAsCompleted = () => {
  const lesson = lessons.value.find(l => l.id === activeLesson.value)
  if (lesson) {
    lesson.completed = true
    lesson.progress = 100
    ElMessage.success('课程标记为已完成！')
  }
}

// 运行代码
const runCode = () => {
  ElMessage.info('代码运行功能将在后续版本中实现')
}

// 复制代码
const copyCode = async () => {
  try {
    await navigator.clipboard.writeText(currentLesson.value?.codeExample || '')
    ElMessage.success('代码已复制到剪贴板')
  } catch (err) {
    ElMessage.error('复制失败')
  }
}

// 显示答案
const showSolution = () => {
  ElMessage.info(`答案: ${currentLesson.value?.solution}`)
}
</script>

<style scoped lang="scss">
.functional-container {
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