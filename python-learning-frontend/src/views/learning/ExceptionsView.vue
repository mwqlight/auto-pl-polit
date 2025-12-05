<template>
  <div class="exceptions-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">异常处理</h2>
      <p class="module-description">学习Python中的异常处理机制和最佳实践</p>
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
const activeLesson = ref('basic-exceptions')
const lessons = ref<Lesson[]>([
  {
    id: 'basic-exceptions',
    title: '基础异常处理',
    description: '学习try-except语句和常见异常类型',
    icon: 'el-icon-warning-outline',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>异常（Exception）</strong>是程序运行时发生的错误事件。</p>
      <p><strong>异常处理的基本语法：</strong></p>
      <pre><code>try:
    # 可能引发异常的代码
    risky_operation()
except ExceptionType:
    # 异常处理代码
    handle_exception()
else:
    # 没有异常时执行的代码
    print("操作成功")
finally:
    # 无论是否发生异常都会执行的代码
    cleanup_resources()</code></pre>
      <p><strong>常见异常类型：</strong></p>
      <ul>
        <li><code>ZeroDivisionError</code>：除零错误</li>
        <li><code>ValueError</code>：值错误</li>
        <li><code>TypeError</code>：类型错误</li>
        <li><code>IndexError</code>：索引错误</li>
        <li><code>KeyError</code>：键错误</li>
        <li><code>FileNotFoundError</code>：文件未找到错误</li>
      </ul>
    `,
    codeExample: `# 基础异常处理示例
# 1. 除零错误处理
try:
    result = 10 / 0
    print(f"结果: {result}")
except ZeroDivisionError:
    print("错误：除数不能为零！")

# 2. 值错误处理
try:
    number = int("abc")
    print(f"转换后的数字: {number}")
except ValueError as e:
    print(f"值错误: {e}")

# 3. 类型错误处理
try:
    result = "hello" + 5
    print(f"结果: {result}")
except TypeError as e:
    print(f"类型错误: {e}")

# 4. 索引错误处理
numbers = [1, 2, 3]
try:
    print(f"第五个元素: {numbers[4]}")
except IndexError as e:
    print(f"索引错误: {e}")

# 5. 键错误处理
person = {"name": "张三", "age": 25}
try:
    print(f"职业: {person['job']}")
except KeyError as e:
    print(f"键错误: 键'{e}'不存在")

# 6. 文件操作异常
try:
    with open("nonexistent_file.txt", "r") as file:
        content = file.read()
        print(content)
except FileNotFoundError as e:
    print(f"文件未找到错误: {e}")

# 7. 多个异常处理
def divide_numbers(a, b):
    try:
        result = a / b
        return result
    except ZeroDivisionError:
        return "除数不能为零"
    except TypeError:
        return "参数类型错误"
    except Exception as e:
        return f"未知错误: {e}"

print(divide_numbers(10, 2))    # 正常
print(divide_numbers(10, 0))    # 除零错误
print(divide_numbers(10, "2"))   # 类型错误

# 8. else和finally子句
def read_file(filename):
    try:
        with open(filename, "r") as file:
            content = file.read()
    except FileNotFoundError:
        print("文件不存在")
        content = ""
    else:
        print("文件读取成功")
    finally:
        print("清理资源完成")
    return content

# 测试文件读取
content = read_file("example.txt")
print(f"文件内容: {content}")`,
    exercise: '编写一个函数，接收两个数字参数，进行除法运算，并处理所有可能的异常。',
    solution: `def safe_divide(a, b):
    """安全的除法运算"""
    try:
        result = a / b
        return f"{a} / {b} = {result}"
    except ZeroDivisionError:
        return "错误：除数不能为零"
    except TypeError:
        return "错误：参数必须是数字"
    except Exception as e:
        return f"未知错误: {e}"

# 测试函数
print(safe_divide(10, 2))
print(safe_divide(10, 0))
print(safe_divide(10, "2"))
print(safe_divide("10", 2))`
  },
  {
    id: 'custom-exceptions',
    title: '自定义异常',
    description: '学习如何创建和使用自定义异常类',
    icon: 'el-icon-s-custom',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>自定义异常</strong>允许我们创建特定于应用程序的异常类型。</p>
      <p><strong>创建自定义异常的步骤：</strong></p>
      <ol>
        <li>继承<code>Exception</code>类或其子类</li>
        <li>定义<code>__init__</code>方法初始化异常</li>
        <li>可选：重写<code>__str__</code>方法提供自定义错误信息</li>
      </ol>
      <p><strong>自定义异常的优势：</strong></p>
      <ul>
        <li>提供更具体的错误信息</li>
        <li>便于错误分类和处理</li>
        <li>提高代码的可读性和可维护性</li>
      </ul>
    `,
    codeExample: `# 自定义异常示例

# 1. 基础自定义异常
class MyCustomError(Exception):
    """基础自定义异常"""
    pass

# 2. 带消息的自定义异常
class ValidationError(Exception):
    """验证错误异常"""
    def __init__(self, message, field=None):
        self.message = message
        self.field = field
        super().__init__(self.message)
    
    def __str__(self):
        if self.field:
            return f"字段 '{self.field}' 验证失败: {self.message}"
        else:
            return f"验证失败: {self.message}"

# 3. 业务逻辑异常
class InsufficientFundsError(Exception):
    """余额不足异常"""
    def __init__(self, balance, amount):
        self.balance = balance
        self.amount = amount
        super().__init__(f"余额不足: 当前余额{balance}，需要{amount}")

class AccountNotFoundError(Exception):
    """账户未找到异常"""
    def __init__(self, account_id):
        self.account_id = account_id
        super().__init__(f"账户 {account_id} 不存在")

# 4. 异常层次结构
class BankError(Exception):
    """银行系统基础异常"""
    pass

class AccountError(BankError):
    """账户相关异常"""
    pass

class TransactionError(BankError):
    """交易相关异常"""
    pass

# 使用自定义异常
def validate_age(age):
    """验证年龄"""
    if not isinstance(age, int):
        raise ValidationError("年龄必须是整数", "age")
    if age < 0:
        raise ValidationError("年龄不能为负数", "age")
    if age > 150:
        raise ValidationError("年龄不能超过150岁", "age")
    return True

# 测试验证函数
try:
    validate_age(-5)
except ValidationError as e:
    print(f"验证错误: {e}")

try:
    validate_age("25")
except ValidationError as e:
    print(f"验证错误: {e}")

# 银行系统示例
class BankAccount:
    def __init__(self, account_id, balance=0):
        self.account_id = account_id
        self.balance = balance
    
    def withdraw(self, amount):
        if amount <= 0:
            raise ValidationError("取款金额必须大于0")
        if amount > self.balance:
            raise InsufficientFundsError(self.balance, amount)
        self.balance -= amount
        return self.balance
    
    def deposit(self, amount):
        if amount <= 0:
            raise ValidationError("存款金额必须大于0")
        self.balance += amount
        return self.balance

# 测试银行账户
def process_transaction(account, amount, operation):
    try:
        if operation == "withdraw":
            new_balance = account.withdraw(amount)
            print(f"取款成功，新余额: {new_balance}")
        elif operation == "deposit":
            new_balance = account.deposit(amount)
            print(f"存款成功，新余额: {new_balance}")
        else:
            raise ValidationError("无效的操作类型")
    except BankError as e:
        print(f"银行交易错误: {e}")
    except Exception as e:
        print(f"未知错误: {e}")

# 创建账户并测试
account = BankAccount("123456", 1000)

process_transaction(account, 500, "withdraw")   # 成功
process_transaction(account, 600, "withdraw")   # 余额不足
process_transaction(account, -100, "deposit")   # 验证错误
process_transaction(account, 200, "transfer")   # 无效操作`,
    exercise: '创建一个学生管理系统，定义自定义异常来处理年龄验证、成绩验证等错误。',
    solution: `class StudentError(Exception):
    """学生管理系统基础异常"""
    pass

class AgeValidationError(StudentError):
    """年龄验证错误"""
    def __init__(self, age):
        self.age = age
        super().__init__(f"年龄 {age} 无效，必须在6-60岁之间")

class ScoreValidationError(StudentError):
    """成绩验证错误"""
    def __init__(self, score):
        self.score = score
        super().__init__(f"成绩 {score} 无效，必须在0-100分之间")

class StudentNotFoundError(StudentError):
    """学生未找到错误"""
    def __init__(self, student_id):
        self.student_id = student_id
        super().__init__(f"学生 {student_id} 不存在")

class Student:
    def __init__(self, student_id, name, age, score):
        self.validate_age(age)
        self.validate_score(score)
        
        self.student_id = student_id
        self.name = name
        self.age = age
        self.score = score
    
    def validate_age(self, age):
        if not (6 <= age <= 60):
            raise AgeValidationError(age)
    
    def validate_score(self, score):
        if not (0 <= score <= 100):
            raise ScoreValidationError(score)
    
    def update_score(self, new_score):
        self.validate_score(new_score)
        self.score = new_score
        return f"{self.name}的成绩已更新为{new_score}"

# 测试学生管理系统
def create_student(student_id, name, age, score):
    try:
        student = Student(student_id, name, age, score)
        print(f"学生 {name} 创建成功")
        return student
    except StudentError as e:
        print(f"创建学生失败: {e}")
        return None

# 测试
student1 = create_student("001", "张三", 18, 85)  # 成功
student2 = create_student("002", "李四", 5, 90)   # 年龄错误
student3 = create_student("003", "王五", 20, 105) # 成绩错误

if student1:
    print(student1.update_score(95))  # 成功更新
    print(student1.update_score(150)) # 成绩错误`
  },
  {
    id: 'exception-best-practices',
    title: '异常处理最佳实践',
    description: '学习异常处理的最佳实践和常见模式',
    icon: 'el-icon-s-claim',
    completed: false,
    progress: 40,
    theory: `
      <p><strong>异常处理的最佳实践：</strong></p>
      <ol>
        <li><strong>只捕获你能处理的异常</strong>：不要捕获所有异常，让无法处理的异常向上传播</li>
        <li><strong>提供具体的错误信息</strong>：异常信息应该清晰、具体，便于调试</li>
        <li><strong>避免空的except块</strong>：至少记录异常信息</li>
        <li><strong>使用finally进行资源清理</strong>：确保资源（如文件、网络连接）被正确释放</li>
        <li><strong>异常链</strong>：使用<code>raise ... from ...</code>保留原始异常信息</li>
        <li><strong>日志记录</strong>：重要的异常应该被记录到日志中</li>
      </ol>
      <p><strong>常见的异常处理模式：</strong></p>
      <ul>
        <li>EAFP（Easier to Ask for Forgiveness than Permission）：先尝试操作，再处理异常</li>
        <li>LBYL（Look Before You Leap）：先检查条件，再执行操作</li>
      </ul>
    `,
    codeExample: `# 异常处理最佳实践示例

import logging

# 配置日志
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# 1. 只捕获你能处理的异常
def process_data(data):
    """处理数据，只捕获ValueError"""
    try:
        # 尝试将数据转换为整数
        number = int(data)
        return number * 2
    except ValueError:
        # 只处理ValueError，其他异常会向上传播
        logging.warning(f"无法将 '{data}' 转换为整数")
        return None

# 测试
print(process_data("123"))     # 正常
print(process_data("abc"))     # ValueError被处理

# 2. 避免空的except块
def bad_example():
    """不好的示例：空的except块"""
    try:
        risky_operation()
    except:
        pass  # 空的except块，隐藏了错误

def good_example():
    """好的示例：记录异常"""
    try:
        risky_operation()
    except Exception as e:
        logging.error(f"操作失败: {e}")
        # 可以选择重新抛出异常
        raise

# 3. 使用finally进行资源清理
def read_file_safely(filename):
    """安全读取文件，使用finally确保资源释放"""
    file = None
    try:
        file = open(filename, 'r')
        content = file.read()
        return content
    except FileNotFoundError:
        logging.error(f"文件 {filename} 未找到")
        return ""
    finally:
        if file:
            file.close()
            logging.info("文件已关闭")

# 4. 异常链：保留原始异常信息
def process_user_input(input_data):
    """处理用户输入，保留原始异常信息"""
    try:
        # 尝试处理输入
        result = complex_processing(input_data)
        return result
    except Exception as e:
        # 使用raise ... from ... 保留原始异常
        raise ProcessingError(f"处理输入失败: {input_data}") from e

class ProcessingError(Exception):
    pass

def complex_processing(data):
    """复杂的处理逻辑"""
    if not data:
        raise ValueError("输入数据不能为空")
    return data.upper()

# 测试异常链
try:
    process_user_input("")
except ProcessingError as e:
    print(f"处理错误: {e}")
    print(f"原始异常: {e.__cause__}")

# 5. EAFP vs LBYL模式
# EAFP模式（推荐）
def eafp_example(data):
    """EAFP：先尝试操作，再处理异常"""
    try:
        value = data['key']
        return value.upper()
    except KeyError:
        return "默认值"
    except AttributeError:
        return "无效的值"

# LBYL模式
def lbyl_example(data):
    """LBYL：先检查条件，再执行操作"""
    if 'key' in data and hasattr(data['key'], 'upper'):
        return data['key'].upper()
    else:
        return "默认值"

# 测试两种模式
test_data = {'key': 'hello'}
print(f"EAFP结果: {eafp_example(test_data)}")
print(f"LBYL结果: {lbyl_example(test_data)}")

# 6. 上下文管理器（with语句）
class DatabaseConnection:
    """数据库连接上下文管理器"""
    def __init__(self, connection_string):
        self.connection_string = connection_string
        self.connection = None
    
    def __enter__(self):
        """进入上下文时调用"""
        print("连接数据库...")
        self.connection = f"连接到 {self.connection_string}"
        return self.connection
    
    def __exit__(self, exc_type, exc_val, exc_tb):
        """退出上下文时调用"""
        print("关闭数据库连接...")
        if exc_type:
            logging.error(f"数据库操作出错: {exc_val}")
        self.connection = None

# 使用上下文管理器
with DatabaseConnection("mysql://localhost:3306/mydb") as conn:
    print(f"使用连接: {conn}")
    # 这里可以执行数据库操作
    # 无论是否发生异常，连接都会被正确关闭

print("连接已自动关闭")

# 7. 使用断言进行调试
def calculate_average(scores):
    """计算平均分，使用断言验证前提条件"""
    assert len(scores) > 0, "分数列表不能为空"
    assert all(isinstance(score, (int, float)) for score in scores), "分数必须是数字"
    assert all(0 <= score <= 100 for score in scores), "分数必须在0-100之间"
    
    return sum(scores) / len(scores)

# 测试断言
try:
    result = calculate_average([85, 92, 78])
    print(f"平均分: {result}")
    
    # 这会触发断言错误
    # result = calculate_average([])
except AssertionError as e:
    print(f"断言失败: {e}")`,
    exercise: '实现一个文件处理工具类，使用上下文管理器和异常处理最佳实践来安全地处理文件操作。',
    solution: `import logging

logging.basicConfig(level=logging.INFO)

class FileProcessor:
    """文件处理工具类"""
    
    @staticmethod
    def read_file_safely(filename, encoding='utf-8'):
        """安全读取文件"""
        try:
            with open(filename, 'r', encoding=encoding) as file:
                content = file.read()
                logging.info(f"成功读取文件: {filename}")
                return content
        except FileNotFoundError:
            logging.error(f"文件未找到: {filename}")
            raise
        except UnicodeDecodeError as e:
            logging.error(f"文件编码错误: {filename} - {e}")
            raise
        except Exception as e:
            logging.error(f"读取文件时发生未知错误: {filename} - {e}")
            raise
    
    @staticmethod
    def write_file_safely(filename, content, encoding='utf-8'):
        """安全写入文件"""
        try:
            with open(filename, 'w', encoding=encoding) as file:
                file.write(content)
                logging.info(f"成功写入文件: {filename}")
        except PermissionError:
            logging.error(f"没有写入权限: {filename}")
            raise
        except Exception as e:
            logging.error(f"写入文件时发生未知错误: {filename} - {e}")
            raise
    
    @staticmethod
    def copy_file_safely(source, destination):
        """安全复制文件"""
        try:
            content = FileProcessor.read_file_safely(source)
            FileProcessor.write_file_safely(destination, content)
            logging.info(f"成功复制文件: {source} -> {destination}")
        except Exception as e:
            logging.error(f"复制文件失败: {source} -> {destination} - {e}")
            raise

# 测试文件处理工具
try:
    # 写入测试文件
    FileProcessor.write_file_safely("test.txt", "Hello, World!")
    
    # 读取测试文件
    content = FileProcessor.read_file_safely("test.txt")
    print(f"文件内容: {content}")
    
    # 复制文件
    FileProcessor.copy_file_safely("test.txt", "test_copy.txt")
    
    # 读取不存在的文件（会抛出异常）
    # FileProcessor.read_file_safely("nonexistent.txt")
    
except Exception as e:
    print(f"文件操作失败: {e}")`
  },
  {
    id: 'advanced-exception-handling',
    title: '高级异常处理技巧',
    description: '学习异常链、上下文管理器和断言等高级技巧',
    icon: 'el-icon-s-flag',
    completed: false,
    progress: 0,
    theory: `
      <p><strong>高级异常处理技巧：</strong></p>
      <ol>
        <li><strong>异常链（Exception Chaining）</strong>：使用<code>raise ... from ...</code>保留原始异常信息</li>
        <li><strong>上下文管理器（Context Managers）</strong>：使用<code>with</code>语句自动管理资源</li>
        <li><strong>断言（Assertions）</strong>：用于调试和验证程序状态</li>
        <li><strong>警告（Warnings）</strong>：用于非致命问题的通知</li>
        <li><strong>日志记录（Logging）</strong>：系统化的错误信息管理</li>
      </ol>
      <p><strong>异常处理的设计模式：</strong></p>
      <ul>
        <li>重试模式：在遇到临时性错误时重试操作</li>
        <li>熔断器模式：防止系统因连续错误而崩溃</li>
        <li>降级模式：在主要功能失败时提供备用方案</li>
      </ul>
    `,
    codeExample: `# 高级异常处理技巧示例

import time
import random
from contextlib import contextmanager

# 1. 重试模式
def retry_operation(operation, max_retries=3, delay=1):
    """重试操作装饰器"""
    def wrapper(*args, **kwargs):
        for attempt in range(max_retries + 1):
            try:
                return operation(*args, **kwargs)
            except Exception as e:
                if attempt == max_retries:
                    raise
                print(f"尝试 {attempt + 1} 失败: {e}，{delay}秒后重试...")
                time.sleep(delay)
    return wrapper

# 模拟不稳定的操作
def unstable_operation():
    """模拟不稳定的操作"""
    if random.random() < 0.7:  # 70%的概率失败
        raise ConnectionError("网络连接失败")
    return "操作成功"

# 使用重试模式
@retry_operation
def reliable_operation():
    return unstable_operation()

# 测试重试
try:
    result = reliable_operation()
    print(f"最终结果: {result}")
except Exception as e:
    print(f"所有重试都失败了: {e}")

# 2. 熔断器模式
class CircuitBreaker:
    """熔断器模式"""
    def __init__(self, failure_threshold=3, timeout=10):
        self.failure_threshold = failure_threshold
        self.timeout = timeout
        self.failure_count = 0
        self.last_failure_time = None
        self.state = "CLOSED"  # CLOSED, OPEN, HALF_OPEN
    
    def call(self, operation):
        """调用受保护的操作"""
        if self.state == "OPEN":
            # 检查是否应该进入半开状态
            if time.time() - self.last_failure_time > self.timeout:
                self.state = "HALF_OPEN"
            else:
                raise CircuitBreakerOpenError("熔断器已打开")
        
        try:
            result = operation()
            # 操作成功，重置熔断器
            if self.state == "HALF_OPEN":
                self.state = "CLOSED"
                self.failure_count = 0
            return result
        except Exception as e:
            self.failure_count += 1
            self.last_failure_time = time.time()
            
            if self.failure_count >= self.failure_threshold:
                self.state = "OPEN"
            
            raise

class CircuitBreakerOpenError(Exception):
    pass

# 使用熔断器
breaker = CircuitBreaker(failure_threshold=2, timeout=5)

for i in range(5):
    try:
        result = breaker.call(unstable_operation)
        print(f"调用 {i+1}: {result}")
    except Exception as e:
        print(f"调用 {i+1} 失败: {e}")
    time.sleep(1)

# 3. 自定义上下文管理器
@contextmanager
def timer_context():
    """计时上下文管理器"""
    start_time = time.time()
    try:
        yield
    finally:
        end_time = time.time()
        print(f"操作耗时: {end_time - start_time:.4f}秒")

# 使用计时上下文
with timer_context():
    time.sleep(1)
    print("执行耗时操作...")

# 4. 警告系统
import warnings

def deprecated_function():
    """已弃用的函数"""
    warnings.warn(
        "此函数已弃用，请使用new_function代替",
        DeprecationWarning,
        stacklevel=2
    )
    return "旧功能"

def experimental_function():
    """实验性功能"""
    warnings.warn(
        "此功能是实验性的，可能会在未来的版本中更改",
        FutureWarning,
        stacklevel=2
    )
    return "实验功能"

# 测试警告
print("调用已弃用函数:")
result = deprecated_function()
print(f"结果: {result}")

print("\n调用实验性函数:")
result = experimental_function()
print(f"结果: {result}")

# 5. 高级断言技巧
def process_data(data):
    """处理数据，使用高级断言"""
    # 验证输入数据
    assert isinstance(data, (list, tuple)), "数据必须是列表或元组"
    assert len(data) > 0, "数据不能为空"
    assert all(isinstance(x, (int, float)) for x in data), "所有元素必须是数字"
    
    # 处理数据
    try:
        result = sum(data) / len(data)
        assert not isinstance(result, complex), "结果不能是复数"
        return result
    except ZeroDivisionError:
        # 这不应该发生，因为我们已经验证了数据长度>0
        raise AssertionError("数据长度验证失败，不应该出现除零错误")

# 测试高级断言
try:
    print(process_data([1, 2, 3, 4, 5]))  # 正常
    # print(process_data([]))              # 会触发断言错误
    # print(process_data(["a", "b"]))      # 会触发断言错误
except AssertionError as e:
    print(f"断言失败: {e}")

# 6. 异常链的高级用法
class DatabaseError(Exception):
    """数据库错误"""
    pass

class NetworkError(Exception):
    """网络错误"""
    pass

def complex_operation():
    """复杂的操作，可能抛出多种异常"""
    try:
        # 模拟数据库操作
        if random.random() < 0.3:
            raise DatabaseError("数据库连接失败")
        
        # 模拟网络操作
        if random.random() < 0.3:
            raise NetworkError("网络请求超时")
        
        return "操作成功"
    except (DatabaseError, NetworkError) as e:
        # 包装原始异常，提供更多上下文信息
        raise RuntimeError(f"复杂操作失败: {type(e).__name__}") from e

# 测试异常链
try:
    result = complex_operation()
    print(f"操作结果: {result}")
except RuntimeError as e:
    print(f"运行时错误: {e}")
    if e.__cause__:
        print(f"原始异常: {e.__cause__}")

# 7. 使用warnings模块进行性能警告
def process_large_dataset(data):
    """处理大型数据集"""
    if len(data) > 10000:
        warnings.warn(
            "处理大型数据集可能会影响性能，建议分批处理",
            PerformanceWarning
        )
    
    # 处理数据...
    return f"处理了 {len(data)} 条数据"

class PerformanceWarning(Warning):
    pass

# 测试性能警告
print("处理小型数据集:")
result = process_large_dataset(list(range(100)))
print(f"结果: {result}")

print("\n处理大型数据集:")
result = process_large_dataset(list(range(15000)))
print(f"结果: {result}")

# 8. 综合示例：文件下载器
class FileDownloader:
    """文件下载器，使用多种异常处理技巧"""
    
    def __init__(self, max_retries=3):
        self.max_retries = max_retries
        self.breaker = CircuitBreaker(failure_threshold=2, timeout=10)
    
    @retry_operation
    def download_file(self, url, filename):
        """下载文件"""
        try:
            # 使用熔断器保护下载操作
            return self.breaker.call(lambda: self._download(url, filename))
        except CircuitBreakerOpenError:
            raise ConnectionError("下载服务暂时不可用，请稍后重试")
    
    def _download(self, url, filename):
        """实际的下载逻辑"""
        # 模拟下载过程
        if random.random() < 0.4:
            raise ConnectionError(f"下载失败: {url}")
        
        # 模拟下载成功
        print(f"下载成功: {url} -> {filename}")
        return f"文件已保存到: {filename}"

# 测试文件下载器
downloader = FileDownloader()

for i in range(5):
    try:
        result = downloader.download_file(
            f"http://example.com/file{i}.txt",
            f"downloaded_file{i}.txt"
        )
        print(f"下载 {i+1}: {result}")
    except Exception as e:
        print(f"下载 {i+1} 失败: {e}")
    time.sleep(1)`,
    exercise: '实现一个完整的API客户端，包含重试机制、熔断器、异常链和日志记录等高级异常处理技巧。',
    solution: `import time
import random
import logging
from contextlib import contextmanager

# 配置日志
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)

class APIClientError(Exception):
    """API客户端基础异常"""
    pass

class APITimeoutError(APIClientError):
    """API超时异常"""
    pass

class APIRateLimitError(APIClientError):
    """API限流异常"""
    pass

class CircuitBreaker:
    """熔断器"""
    def __init__(self, failure_threshold=3, timeout=30):
        self.failure_threshold = failure_threshold
        self.timeout = timeout
        self.failure_count = 0
        self.last_failure_time = None
        self.state = "CLOSED"
    
    def call(self, operation):
        if self.state == "OPEN":
            if time.time() - self.last_failure_time > self.timeout:
                self.state = "HALF_OPEN"
            else:
                raise APIClientError("服务暂时不可用")
        
        try:
            result = operation()
            if self.state == "HALF_OPEN":
                self.state = "CLOSED"
                self.failure_count = 0
            return result
        except Exception as e:
            self.failure_count += 1
            self.last_failure_time = time.time()
            if self.failure_count >= self.failure_threshold:
                self.state = "OPEN"
            raise

def retry_with_backoff(operation, max_retries=3, base_delay=1):
    """指数退避重试"""
    for attempt in range(max_retries + 1):
        try:
            return operation()
        except Exception as e:
            if attempt == max_retries:
                raise
            delay = base_delay * (2 ** attempt) + random.uniform(0, 0.1)
            logging.warning(f"尝试 {attempt + 1} 失败: {e}，{delay:.2f}秒后重试")
            time.sleep(delay)

@contextmanager
def api_call_context(api_name):
    """API调用上下文管理器"""
    start_time = time.time()
    try:
        logging.info(f"开始调用API: {api_name}")
        yield
    except Exception as e:
        logging.error(f"API调用失败: {api_name} - {e}")
        raise
    finally:
        duration = time.time() - start_time
        logging.info(f"API调用完成: {api_name} - 耗时{duration:.2f}秒")

class APIClient:
    """完整的API客户端"""
    
    def __init__(self, base_url):
        self.base_url = base_url
        self.breaker = CircuitBreaker()
    
    def get(self, endpoint, timeout=5):
        """GET请求"""
        def operation():
            return self._make_request("GET", endpoint, timeout)
        
        return retry_with_backoff(
            lambda: self.breaker.call(operation),
            max_retries=2
        )
    
    def post(self, endpoint, data, timeout=5):
        """POST请求"""
        def operation():
            return self._make_request("POST", endpoint, timeout, data)
        
        return retry_with_backoff(
            lambda: self.breaker.call(operation),
            max_retries=2
        )
    
    def _make_request(self, method, endpoint, timeout, data=None):
        """实际发起请求"""
        url = f"{self.base_url}/{endpoint}"
        
        with api_call_context(f"{method} {url}"):
            # 模拟网络请求
            if random.random() < 0.3:
                raise APITimeoutError(f"请求超时: {url}")
            
            if random.random() < 0.2:
                raise APIRateLimitError("API调用频率超限")
            
            # 模拟成功响应
            return {
                "status": "success",
                "data": {
                    "method": method,
                    "endpoint": endpoint,
                    "timestamp": time.time()
                }
            }

# 测试API客户端
client = APIClient("https://api.example.com")

# 测试正常请求
for i in range(3):
    try:
        response = client.get(f"users/{i}")
        print(f"请求 {i+1} 成功: {response}")
    except Exception as e:
        print(f"请求 {i+1} 失败: {e}")
    time.sleep(1)

# 测试熔断器
print("\n测试熔断器:")
for i in range(5):
    try:
        response = client.get("unstable-endpoint")
        print(f"熔断测试 {i+1} 成功: {response}")
    except Exception as e:
        print(f"熔断测试 {i+1} 失败: {e}")
    time.sleep(1)`
  }
])

// 计算属性：当前选中的课程
const currentLesson = computed(() => {
  return lessons.value.find(lesson => lesson.id === activeLesson.value) || null
})

// 方法定义
const selectLesson = (lessonId: string) => {
  activeLesson.value = lessonId
}

const markAsCompleted = () => {
  const lesson = lessons.value.find(l => l.id === activeLesson.value)
  if (lesson && !lesson.completed) {
    lesson.completed = true
    lesson.progress = 100
    ElMessage.success(`课程"${lesson.title}"已标记为完成`)
  }
}

const runCode = async () => {
  ElMessage.info('代码执行功能需要后端服务支持')
}

const copyCode = async () => {
  try {
    await navigator.clipboard.writeText(currentLesson.value?.codeExample || '')
    ElMessage.success('代码已复制到剪贴板')
  } catch (err) {
    ElMessage.error('复制失败，请手动复制代码')
  }
}

const showSolution = () => {
  ElMessage.info(`答案：${currentLesson.value?.solution}`)
}
</script>

<style scoped>
.exceptions-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.module-header {
  text-align: center;
  margin-bottom: 30px;
}

.module-title {
  font-size: 2.5rem;
  color: #2c3e50;
  margin-bottom: 10px;
}

.module-description {
  font-size: 1.1rem;
  color: #7f8c8d;
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
}

.lesson-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.lesson-card.active {
  border: 2px solid #409eff;
}

.lesson-card.completed {
  border-left: 4px solid #67c23a;
}

.lesson-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.lesson-icon {
  font-size: 24px;
  color: #409eff;
  margin-right: 15px;
}

.lesson-info {
  flex: 1;
}

.lesson-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 5px;
  color: #2c3e50;
}

.lesson-desc {
  font-size: 0.9rem;
  color: #7f8c8d;
  margin: 0;
}

.lesson-status {
  margin-left: 10px;
}

.lesson-progress {
  margin-top: 10px;
}

.lesson-content {
  margin-top: 30px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-header h3 {
  margin: 0;
  color: #2c3e50;
}

.theory-section,
.code-section,
.exercise-section {
  margin-bottom: 30px;
}

.theory-section h4,
.code-section h4,
.exercise-section h4 {
  color: #2c3e50;
  border-bottom: 2px solid #eaeaea;
  padding-bottom: 10px;
  margin-bottom: 15px;
}

.theory-content {
  line-height: 1.6;
}

.theory-content :deep(p) {
  margin-bottom: 15px;
}

.theory-content :deep(ul),
.theory-content :deep(ol) {
  margin-bottom: 15px;
  padding-left: 20px;
}

.theory-content :deep(li) {
  margin-bottom: 5px;
}

.theory-content :deep(pre) {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  padding: 15px;
  overflow-x: auto;
  margin: 15px 0;
}

.theory-content :deep(code) {
  background-color: #f8f9fa;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: 'Courier New', monospace;
}

.code-example {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  overflow: hidden;
}

.code-example pre {
  margin: 0;
  padding: 20px;
  overflow-x: auto;
  background-color: #2d2d2d;
  color: #f8f8f2;
}

.code-example code {
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
}

.code-actions {
  padding: 15px 20px;
  background-color: #f8f9fa;
  border-top: 1px solid #e9ecef;
  display: flex;
  gap: 10px;
}

.exercise-content {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  padding: 20px;
}

.exercise-actions {
  margin-top: 15px;
}

@media (max-width: 768px) {
  .exceptions-container {
    padding: 10px;
  }
  
  .lessons-container {
    grid-template-columns: 1fr;
  }
  
  .content-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
}
</style>