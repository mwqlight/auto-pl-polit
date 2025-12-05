-- Python语言学习驾驶舱系统数据初始化脚本（简化版）
-- 创建时间: 2024年
-- 数据库: MySQL 8.0+

USE `python_learning`;

-- 插入学习模块数据
INSERT INTO `learning_modules` (`title`, `description`, `module_type`, `difficulty_level`, `estimated_duration`, `prerequisites`, `learning_objectives`, `icon_url`, `thumbnail_url`, `is_active`, `is_published`, `sort_order`) VALUES
('Python基础入门', '学习Python编程语言的基础知识，包括变量、数据类型、运算符等', 'BASIC_SYNTAX', 'BEGINNER', 180, '无编程基础要求', '掌握Python基本语法、数据类型、控制结构', '/icons/python-basic.svg', '/images/python-basic.png', TRUE, TRUE, 1),
('Python控制结构', '掌握Python的条件判断和循环结构，编写更复杂的程序', 'BASIC_SYNTAX', 'BEGINNER', 240, 'Python基础语法', '掌握条件判断和循环结构的使用', '/icons/python-control.svg', '/images/python-control.png', TRUE, TRUE, 2),
('Python函数与模块', '学习函数的定义与使用，以及模块的导入和应用', 'MODULES_PACKAGES', 'INTERMEDIATE', 300, 'Python基础语法', '掌握函数定义与调用、模块导入', '/icons/python-function.svg', '/images/python-function.png', TRUE, TRUE, 3),
('Python面向对象编程', '深入理解Python的面向对象特性，包括类、对象、继承等', 'OOP', 'INTERMEDIATE', 360, 'Python基础语法', '理解类与对象、继承、多态等OOP概念', '/icons/python-oop.svg', '/images/python-oop.png', TRUE, TRUE, 4),
('Python高级特性', '探索Python的高级特性，如装饰器、生成器、上下文管理器等', 'ADVANCED_FEATURES', 'ADVANCED', 420, 'Python基础语法', '掌握装饰器、生成器等高级特性', '/icons/python-advanced.svg', '/images/python-advanced.png', TRUE, FALSE, 5);

-- 插入课程数据
INSERT INTO `lessons` (`module_id`, `title`, `description`, `content`, `video_url`, `duration`, `sort_order`, `is_free`, `prerequisites`, `learning_objectives`) VALUES
(1, 'Python变量与数据类型', '学习Python中的变量定义和基本数据类型', '<h2>Python变量与数据类型</h2><p>Python是一种动态类型语言...</p>', '/videos/python-variables.mp4', 30, 1, TRUE, '无', '掌握变量定义和基本数据类型'),
(1, 'Python运算符', '学习Python中的算术、比较和逻辑运算符', '<h2>Python运算符</h2><p>Python支持多种运算符...</p>', '/videos/python-operators.mp4', 25, 2, TRUE, '变量与数据类型', '掌握各种运算符的使用'),
(2, '条件判断语句', '学习if-elif-else条件判断结构', '<h2>条件判断语句</h2><p>Python使用if语句进行条件判断...</p>', '/videos/python-if.mp4', 35, 1, TRUE, '变量与运算符', '掌握条件判断语句的使用'),
(2, '循环结构', '学习for和while循环的使用', '<h2>循环结构</h2><p>Python提供for和while两种循环...</p>', '/videos/python-loops.mp4', 40, 2, TRUE, '条件判断', '掌握循环结构的使用'),
(3, '函数定义与调用', '学习如何定义和调用函数', '<h2>函数定义与调用</h2><p>函数是组织代码的重要方式...</p>', '/videos/python-functions.mp4', 45, 1, TRUE, '基础语法', '掌握函数的定义与调用'),
(3, '模块导入与使用', '学习如何导入和使用Python模块', '<h2>模块导入与使用</h2><p>Python有丰富的标准库...</p>', '/videos/python-modules.mp4', 35, 2, TRUE, '函数基础', '掌握模块的导入与使用'),
(4, '类与对象', '学习面向对象编程的基本概念', '<h2>类与对象</h2><p>Python是完全面向对象的语言...</p>', '/videos/python-classes.mp4', 50, 1, TRUE, '函数与模块', '理解类与对象的概念'),
(4, '继承与多态', '深入学习面向对象的继承和多态特性', '<h2>继承与多态</h2><p>继承是面向对象的重要特性...</p>', '/videos/python-inheritance.mp4', 55, 2, FALSE, '类与对象', '掌握继承与多态的使用'),
(5, '装饰器', '学习Python装饰器的使用', '<h2>装饰器</h2><p>装饰器是Python的高级特性...</p>', '/videos/python-decorators.mp4', 60, 1, FALSE, '函数基础', '掌握装饰器的使用'),
(5, '生成器与迭代器', '学习生成器和迭代器的概念', '<h2>生成器与迭代器</h2><p>生成器可以节省内存...</p>', '/videos/python-generators.mp4', 65, 2, FALSE, '基础语法', '掌握生成器与迭代器的使用');

-- 插入代码示例数据
INSERT INTO `code_examples` (`lesson_id`, `title`, `description`, `java_code`, `python_code`, `explanation`, `difficulty_level`, `sort_order`, `is_interactive`) VALUES
(1, '变量声明对比', 'Java和Python中变量声明的差异', 'String name = "张三";
int age = 25;', 'name = "张三"
age = 25', 'Python是动态类型语言，无需声明变量类型', 'EASY', 1, TRUE),
(1, '数据类型转换', '不同数据类型之间的转换方法', 'int num = Integer.parseInt("123");
double d = Double.parseDouble("3.14");', 'num = int("123")
d = float("3.14")', 'Python的类型转换函数更简洁直观', 'EASY', 2, TRUE),
(2, '条件判断示例', 'if-else语句的对比', 'if (score >= 90) {
    System.out.println("优秀");
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}', 'if score >= 90:
    print("优秀")
elif score >= 60:
    print("及格")
else:
    print("不及格")', 'Python使用缩进代替大括号，elif代替else if', 'MEDIUM', 1, TRUE),
(2, '循环结构对比', 'for循环的语法差异', 'for (int i = 0; i < 5; i++) {
    System.out.println(i);
}', 'for i in range(5):
    print(i)', 'Python的for循环更简洁，使用range函数生成序列', 'MEDIUM', 2, TRUE),
(3, '函数定义对比', '函数定义和调用的语法差异', 'public static int add(int a, int b) {
    return a + b;
}', 'def add(a, b):
    return a + b', 'Python函数定义更简洁，无需声明返回类型', 'EASY', 1, TRUE),
(3, '模块导入示例', '导入和使用外部模块', 'import java.util.ArrayList;
import java.util.List;', 'import math
from datetime import datetime', 'Python的模块导入语法更灵活', 'EASY', 2, TRUE),
(4, '类定义对比', '面向对象编程中的类定义', 'public class Person {
    private String name;
    public Person(String name) {
        this.name = name;
    }
}', 'class Person:
    def __init__(self, name):
        self.name = name', 'Python的类定义更简洁，构造函数使用__init__', 'MEDIUM', 1, TRUE),
(4, '继承示例', '面向对象继承的实现', 'class Student extends Person {
    private String major;
    public Student(String name, String major) {
        super(name);
        this.major = major;
    }
}', 'class Student(Person):
    def __init__(self, name, major):
        super().__init__(name)
        self.major = major', 'Python使用括号表示继承，super()调用父类方法', 'HARD', 2, FALSE),
(5, '装饰器示例', 'Python装饰器的使用', '// Java中使用注解实现类似功能
@Deprecated
public void oldMethod() {}', '@deprecated
def old_function():
    pass', 'Python装饰器是函数，可以动态修改函数行为', 'HARD', 1, FALSE),
(5, '生成器示例', 'Python生成器的创建和使用', '// Java中使用迭代器实现类似功能
Iterator<Integer> iterator = list.iterator();', 'def fibonacci(n):
    a, b = 0, 1
    for _ in range(n):
        yield a
        a, b = b, a + b', 'Python生成器使用yield关键字，可以节省内存', 'HARD', 2, FALSE);

-- 插入系统配置数据
INSERT INTO `system_configs` (`config_key`, `config_value`, `description`, `config_type`, `is_public`) VALUES
('site.name', '"Python语言学习驾驶舱"', '网站名称', 'STRING', TRUE),
('site.version', '"1.0.0"', '系统版本', 'STRING', TRUE),
('learning.min_score', '60', '最低通过分数', 'NUMBER', TRUE),
('code_converter.timeout', '30000', '代码转换超时时间(毫秒)', 'NUMBER', FALSE);

-- 输出初始化完成信息
SELECT '数据库初始化完成！' as message;
SELECT CONCAT('已创建 ', COUNT(*), ' 个表') as table_count FROM information_schema.tables WHERE table_schema = 'python_learning';