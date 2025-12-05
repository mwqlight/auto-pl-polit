<template>
  <div class="oop-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">面向对象编程</h2>
      <p class="module-description">学习Python中的类、对象、继承、多态等面向对象概念</p>
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

        <!-- UML图 -->
        <div v-if="currentLesson.uml" class="uml-section">
          <h4>UML类图</h4>
          <div class="uml-content">
            <pre><code class="language-plantuml">{{ currentLesson.uml }}</code></pre>
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
  uml?: string
  exercise: string
  solution: string
}

// 响应式数据
const activeLesson = ref('classes')
const lessons = ref<Lesson[]>([
  {
    id: 'classes',
    title: '类和对象',
    description: '学习如何定义类和创建对象',
    icon: 'el-icon-s-custom',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>类（Class）</strong>是面向对象编程的核心概念，它定义了对象的属性和方法。</p>
      <p><strong>对象（Object）</strong>是类的实例，具有类定义的属性和方法。</p>
      <p><strong>类的定义语法：</strong></p>
      <pre><code>class ClassName:
    def __init__(self, parameters):
        # 初始化方法
        self.attribute = value
    
    def method_name(self):
        # 方法定义
        pass</code></pre>
    `,
    codeExample: `# 定义一个简单的Person类
class Person:
    """人类"""
    
    def __init__(self, name, age):
        """初始化方法"""
        self.name = name
        self.age = age
    
    def introduce(self):
        """自我介绍方法"""
        return f"大家好，我叫{self.name}，今年{self.age}岁。"
    
    def have_birthday(self):
        """过生日方法"""
        self.age += 1
        return f"{self.name}过生日了，现在{self.age}岁！"

# 创建对象
person1 = Person("张三", 25)
person2 = Person("李四", 30)

# 调用方法
print(person1.introduce())
print(person2.introduce())

# 修改属性
person1.have_birthday()
print(person1.introduce())`,
    uml: `@startuml
class Person {
  - name: str
  - age: int
  + __init__(name: str, age: int)
  + introduce(): str
  + have_birthday(): str
}
@enduml`,
    exercise: '创建一个Student类，包含姓名、学号、成绩属性，以及计算平均分的方法。',
    solution: `class Student:
    def __init__(self, name, student_id, scores):
        self.name = name
        self.student_id = student_id
        self.scores = scores
    
    def calculate_average(self):
        return sum(self.scores) / len(self.scores)
    
    def get_info(self):
        return f"学生{self.name}(学号:{self.student_id})，平均分:{self.calculate_average():.2f}"

student = Student("王五", "2023001", [85, 92, 78, 90])
print(student.get_info())`
  },
  {
    id: 'inheritance',
    title: '继承',
    description: '学习类的继承和多级继承',
    icon: 'el-icon-s-fold',
    completed: true,
    progress: 100,
    theory: `
      <p><strong>继承（Inheritance）</strong>允许一个类继承另一个类的属性和方法。</p>
      <p><strong>继承的语法：</strong></p>
      <pre><code>class ChildClass(ParentClass):
    # 子类可以添加新的属性和方法
    # 也可以重写父类的方法
    pass</code></pre>
      <p><strong>继承的类型：</strong></p>
      <ul>
        <li>单继承：一个子类继承一个父类</li>
        <li>多继承：一个子类继承多个父类</li>
        <li>多级继承：子类继承父类，父类又继承祖父类</li>
      </ul>
    `,
    codeExample: `# 父类：Animal
class Animal:
    def __init__(self, name, species):
        self.name = name
        self.species = species
    
    def speak(self):
        return "动物发出声音"
    
    def get_info(self):
        return f"{self.name}是一只{self.species}"

# 子类：Dog，继承Animal
class Dog(Animal):
    def __init__(self, name, breed):
        super().__init__(name, "狗")
        self.breed = breed
    
    def speak(self):  # 重写父类方法
        return "汪汪！"
    
    def fetch(self):  # 子类特有方法
        return f"{self.name}在捡球"

# 子类：Cat，继承Animal
class Cat(Animal):
    def __init__(self, name, color):
        super().__init__(name, "猫")
        self.color = color
    
    def speak(self):  # 重写父类方法
        return "喵喵！"
    
    def climb(self):  # 子类特有方法
        return f"{self.name}在爬树"

# 创建对象
dog = Dog("旺财", "金毛")
cat = Cat("咪咪", "白色")

print(dog.get_info())
print(dog.speak())
print(dog.fetch())

print(cat.get_info())
print(cat.speak())
print(cat.climb())`,
    uml: `@startuml
class Animal {
  - name: str
  - species: str
  + __init__(name: str, species: str)
  + speak(): str
  + get_info(): str
}

class Dog {
  - breed: str
  + __init__(name: str, breed: str)
  + speak(): str
  + fetch(): str
}

class Cat {
  - color: str
  + __init__(name: str, color: str)
  + speak(): str
  + climb(): str
}

Animal <|-- Dog
Animal <|-- Cat
@enduml`,
    exercise: '创建一个Vehicle父类和Car、Motorcycle子类，实现多态特性。',
    solution: `class Vehicle:
    def __init__(self, brand, speed):
        self.brand = brand
        self.speed = speed
    
    def start(self):
        return "车辆启动"
    
    def get_info(self):
        return f"{self.brand}车辆，最高速度{self.speed}km/h"

class Car(Vehicle):
    def __init__(self, brand, speed, seats):
        super().__init__(brand, speed)
        self.seats = seats
    
    def start(self):
        return "汽车启动：引擎轰鸣"
    
    def honk(self):
        return "汽车鸣笛：滴滴"

class Motorcycle(Vehicle):
    def __init__(self, brand, speed, type):
        super().__init__(brand, speed)
        self.type = type
    
    def start(self):
        return "摩托车启动：引擎咆哮"
    
    def wheelie(self):
        return "摩托车翘头特技"

# 多态演示
vehicles = [Car("丰田", 180, 5), Motorcycle("本田", 200, "跑车")]
for vehicle in vehicles:
    print(vehicle.get_info())
    print(vehicle.start())`
  },
  {
    id: 'encapsulation',
    title: '封装',
    description: '学习访问控制和属性封装',
    icon: 'el-icon-lock',
    completed: false,
    progress: 40,
    theory: `
      <p><strong>封装（Encapsulation）</strong>是将数据和方法包装在类中，并控制对它们的访问。</p>
      <p><strong>访问控制：</strong></p>
      <ul>
        <li><code>public</code>：公开的，可以在任何地方访问</li>
        <li><code>protected</code>：受保护的，以单下划线开头，建议只在类内部和子类中访问</li>
        <li><code>private</code>：私有的，以双下划线开头，只能在类内部访问</li>
      </ul>
      <p><strong>属性封装：</strong>使用getter和setter方法来控制对属性的访问。</p>
    `,
    codeExample: `# 封装示例：BankAccount类
class BankAccount:
    def __init__(self, account_holder, initial_balance=0):
        self.account_holder = account_holder  # 公开属性
        self._account_number = self._generate_account_number()  # 受保护属性
        self.__balance = initial_balance  # 私有属性
    
    def _generate_account_number(self):
        """生成账户号码（受保护方法）"""
        import random
        return f"ACC{random.randint(100000, 999999)}"
    
    def get_balance(self):
        """获取余额（getter方法）"""
        return self.__balance
    
    def deposit(self, amount):
        """存款"""
        if amount > 0:
            self.__balance += amount
            return f"存款成功，当前余额：{self.__balance}"
        else:
            return "存款金额必须大于0"
    
    def withdraw(self, amount):
        """取款"""
        if amount > 0 and amount <= self.__balance:
            self.__balance -= amount
            return f"取款成功，当前余额：{self.__balance}"
        else:
            return "取款失败，余额不足或金额无效"
    
    def get_account_info(self):
        """获取账户信息"""
        return f"账户持有人：{self.account_holder}\n账户号码：{self._account_number}\n当前余额：{self.__balance}"

# 创建银行账户
account = BankAccount("张三", 1000)

# 公开属性可以访问
print(f"账户持有人：{account.account_holder}")

# 受保护属性（不建议直接访问）
print(f"账户号码：{account._account_number}")

# 私有属性无法直接访问（会报错）
# print(account.__balance)  # 错误！

# 通过方法访问
print(account.get_balance())
print(account.deposit(500))
print(account.withdraw(200))
print(account.get_account_info())`,
    uml: `@startuml
class BankAccount {
  + account_holder: str
  - _account_number: str
  - __balance: float
  + __init__(account_holder: str, initial_balance: float)
  - _generate_account_number(): str
  + get_balance(): float
  + deposit(amount: float): str
  + withdraw(amount: float): str
  + get_account_info(): str
}
@enduml`,
    exercise: '创建一个Employee类，封装薪资信息，提供计算年终奖的方法。',
    solution: `class Employee:
    def __init__(self, name, position, base_salary):
        self.name = name
        self.position = position
        self._base_salary = base_salary  # 受保护属性
        self.__bonus_rate = 0.1  # 私有属性
    
    def get_salary_info(self):
        """获取薪资信息"""
        return f"{self.name}({self.position})，基本工资：{self._base_salary}"
    
    def calculate_bonus(self, performance_rating):
        """计算年终奖"""
        if performance_rating == "A":
            bonus_multiplier = 2.0
        elif performance_rating == "B":
            bonus_multiplier = 1.5
        else:
            bonus_multiplier = 1.0
        
        bonus = self._base_salary * self.__bonus_rate * bonus_multiplier
        return f"年终奖：{bonus:.2f}元"
    
    def set_bonus_rate(self, rate):
        """设置奖金率（需要验证）"""
        if 0 <= rate <= 0.2:
            self.__bonus_rate = rate
            return "奖金率设置成功"
        else:
            return "奖金率必须在0到0.2之间"

employee = Employee("李四", "工程师", 15000)
print(employee.get_salary_info())
print(employee.calculate_bonus("A"))
print(employee.set_bonus_rate(0.15))`
  },
  {
    id: 'polymorphism',
    title: '多态',
    description: '学习多态的概念和实现',
    icon: 'el-icon-s-opportunity',
    completed: false,
    progress: 0,
    theory: `
      <p><strong>多态（Polymorphism）</strong>是指同一个方法在不同对象上表现出不同的行为。</p>
      <p><strong>多态的实现方式：</strong></p>
      <ul>
        <li>方法重写（Method Overriding）：子类重写父类的方法</li>
        <li>方法重载（Method Overloading）：Python不支持传统的方法重载，但可以通过默认参数实现类似功能</li>
        <li>鸭子类型（Duck Typing）："如果它走起来像鸭子，叫起来像鸭子，那么它就是鸭子"</li>
      </ul>
      <p><strong>多态的优势：</strong>提高代码的灵活性和可扩展性。</p>
    `,
    codeExample: `# 多态示例：图形类
class Shape:
    """图形基类"""
    def area(self):
        """计算面积"""
        raise NotImplementedError("子类必须实现area方法")
    
    def perimeter(self):
        """计算周长"""
        raise NotImplementedError("子类必须实现perimeter方法")

class Rectangle(Shape):
    """矩形类"""
    def __init__(self, width, height):
        self.width = width
        self.height = height
    
    def area(self):
        return self.width * self.height
    
    def perimeter(self):
        return 2 * (self.width + self.height)

class Circle(Shape):
    """圆形类"""
    def __init__(self, radius):
        self.radius = radius
    
    def area(self):
        import math
        return math.pi * self.radius ** 2
    
    def perimeter(self):
        import math
        return 2 * math.pi * self.radius

class Triangle(Shape):
    """三角形类"""
    def __init__(self, a, b, c):
        self.a = a
        self.b = b
        self.c = c
    
    def area(self):
        # 使用海伦公式计算面积
        s = (self.a + self.b + self.c) / 2
        return (s * (s - self.a) * (s - self.b) * (s - self.c)) ** 0.5
    
    def perimeter(self):
        return self.a + self.b + self.c

# 多态演示：处理不同类型的图形
def process_shapes(shapes):
    """处理图形列表，展示多态特性"""
    for shape in shapes:
        print(f"图形面积: {shape.area():.2f}")
        print(f"图形周长: {shape.perimeter():.2f}")
        print("-" * 30)

# 创建不同类型的图形
shapes = [
    Rectangle(5, 3),
    Circle(4),
    Triangle(3, 4, 5)
]

# 统一处理，每个图形调用相同的方法名但执行不同的实现
process_shapes(shapes)

# 鸭子类型示例
def make_sound(animal):
    """只要对象有speak方法，就可以调用"""
    return animal.speak()

class Dog:
    def speak(self):
        return "汪汪！"

class Cat:
    def speak(self):
        return "喵喵！"

class Car:
    def speak(self):
        return "滴滴！"

# 不同类型的对象都可以调用make_sound函数
print(make_sound(Dog()))
print(make_sound(Cat()))
print(make_sound(Car()))`,
    uml: `@startuml
class Shape {
  + area(): float
  + perimeter(): float
}

class Rectangle {
  - width: float
  - height: float
  + __init__(width: float, height: float)
  + area(): float
  + perimeter(): float
}

class Circle {
  - radius: float
  + __init__(radius: float)
  + area(): float
  + perimeter(): float
}

class Triangle {
  - a: float
  - b: float
  - c: float
  + __init__(a: float, b: float, c: float)
  + area(): float
  + perimeter(): float
}

Shape <|-- Rectangle
Shape <|-- Circle
Shape <|-- Triangle
@enduml`,
    exercise: '创建一个支付系统，支持多种支付方式（支付宝、微信、银行卡），实现多态支付。',
    solution: `class Payment:
    """支付基类"""
    def pay(self, amount):
        raise NotImplementedError("子类必须实现pay方法")

class Alipay(Payment):
    def pay(self, amount):
        return f"支付宝支付：{amount}元"

class WechatPay(Payment):
    def pay(self, amount):
        return f"微信支付：{amount}元"

class BankCardPay(Payment):
    def pay(self, amount):
        return f"银行卡支付：{amount}元"

class PaymentProcessor:
    """支付处理器"""
    def process_payment(self, payment_method, amount):
        return payment_method.pay(amount)

# 使用多态处理不同支付方式
processor = PaymentProcessor()
payments = [Alipay(), WechatPay(), BankCardPay()]

for payment in payments:
    print(processor.process_payment(payment, 100))`
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
.oop-container {
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
  
  .theory-section, .code-section, .uml-section, .exercise-section {
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
  
  .code-example, .uml-content {
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