import{_ as V}from"./index-920881de.js";import{o as h,E as A,K as I,k as L,l as j,L as O,M as c,h as B,v as z,N as J,O as H,P as Q,Q as D}from"./element-ui-08f13b0c.js";import{y as K,r as y,l as R,c as W,A as b,B as e,Q as s,I as l,M as _,O as i,L as k,z as C,u as a}from"./vue-vendor-8137f92e.js";import"./monaco-aa1fb2c3.js";const q={class:"learning-center-view"},G={class:"page-header"},U={class:"page-title"},X={class:"learning-container"},Y={class:"sidebar-nav"},Z={class:"nav-section"},$={class:"nav-section"},ee={class:"progress-section"},te={class:"progress-stats"},se={class:"progress-item"},le={class:"progress-value"},ne={class:"progress-item"},oe={class:"progress-value"},ae={class:"progress-item"},ie={class:"progress-value"},re={class:"content-area"},ue={class:"chapter-content"},de={class:"chapter-header"},pe={class:"chapter-actions"},ce={class:"chapter-body"},ve={class:"theory-section"},_e=["innerHTML"],me={key:0,class:"code-section"},ge={class:"code-editor"},ye={class:"code-display"},fe={key:0,class:"code-result"},xe={class:"result-output"},he={class:"comparison-section"},be={class:"comparison-grid"},Ce={class:"comparison-item"},Se={class:"comparison-code"},Te={class:"comparison-item"},ke={class:"comparison-code"},Ee={class:"navigation-controls"},we=K({__name:"LearningCenterView",setup(Fe){const r=y("1"),f=y(!0),x=y(!1),d=y(""),u=R({completion:25,learned:2,total:8,timeSpent:"2小时15分钟"}),m=y([{id:"1",title:"变量和数据类型",completed:!0,theory:`
      <p>Python是一种动态类型语言，变量不需要显式声明类型。Python会自动推断变量的类型。</p>
      <h4>基本数据类型：</h4>
      <ul>
        <li><strong>整数(int)</strong>：如 42, -10, 0</li>
        <li><strong>浮点数(float)</strong>：如 3.14, -0.001, 2.0</li>
        <li><strong>字符串(str)</strong>：如 "hello", 'world', """多行字符串"""</li>
        <li><strong>布尔值(bool)</strong>：True 或 False</li>
        <li><strong>None</strong>：表示空值</li>
      </ul>
    `,codeExample:`# 变量定义和数据类型
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
print(type(is_active)) # <class 'bool'>`,javaComparison:`// Java 变量定义
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
}`,pythonComparison:`# Python 变量定义
# 无需声明类型，自动推断
age = 25
price = 19.99
name = "Alice"
is_active = True

# 类型检查
print(type(age))
print(type(price))
print(type(name))
print(type(is_active))`},{id:"2",title:"运算符和表达式",completed:!0,theory:`
      <p>Python支持丰富的运算符，包括算术运算符、比较运算符、逻辑运算符等。</p>
      <h4>主要运算符：</h4>
      <ul>
        <li><strong>算术运算符</strong>：+ - * / % ** //</li>
        <li><strong>比较运算符</strong>：== != > < >= <=</li>
        <li><strong>逻辑运算符</strong>：and or not</li>
        <li><strong>赋值运算符</strong>：= += -= *= /=</li>
      </ul>
    `,codeExample:`# 算术运算符
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
print(not a)   # False`,javaComparison:`// Java 运算符
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
}`,pythonComparison:`# Python 运算符
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
print(not a)   # False`}]),v=W(()=>m.value.find(n=>n.id===r.value)||m.value[0]),S=n=>{r.value=n,d.value=""},E=()=>{f.value=!f.value},w=async()=>{x.value=!0;try{switch(await new Promise(n=>setTimeout(n,1e3)),r.value){case"1":d.value=`<class 'int'>
<class 'float'>
<class 'str'>
<class 'bool'>`;break;case"2":d.value=`13
7
30
3.3333333333333335
1
1000
3
False
True
True
False
True
False`;break;default:d.value="代码执行成功！"}h.success("代码执行完成")}catch(n){h.error("代码执行失败"),console.error("执行错误:",n)}finally{x.value=!1}},F=()=>{const n=v.value;n.completed=!n.completed,n.completed?(h.success("章节标记为已完成"),u.learned=m.value.filter(t=>t.completed).length,u.completion=Math.round(u.learned/u.total*100)):h.info("章节标记为未完成")},P=()=>{const n=parseInt(r.value);n>1&&(r.value=(n-1).toString(),d.value="")},N=()=>{const n=parseInt(r.value);n<m.value.length&&(r.value=(n+1).toString(),d.value="")};return(n,t)=>{const o=A,p=D,T=I,M=L,g=j;return C(),b("div",q,[e("div",G,[e("h1",U,[s(o,null,{default:l(()=>[s(a(O))]),_:1}),t[0]||(t[0]=_(" Python 核心语法学习中心 ",-1))]),t[1]||(t[1]=e("p",{class:"page-desc"},"系统化学习Python编程语言，从基础到高级语法",-1))]),e("div",X,[e("div",Y,[e("div",Z,[t[6]||(t[6]=e("h3",null,"基础语法",-1)),s(T,{"default-active":r.value,onSelect:S,"background-color":"transparent","text-color":"rgba(255,255,255,0.7)","active-text-color":"#00d4ff"},{default:l(()=>[s(p,{index:"1"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[2]||(t[2]=e("span",null,"变量和数据类型",-1))]),_:1}),s(p,{index:"2"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[3]||(t[3]=e("span",null,"运算符和表达式",-1))]),_:1}),s(p,{index:"3"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[4]||(t[4]=e("span",null,"控制流程",-1))]),_:1}),s(p,{index:"4"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[5]||(t[5]=e("span",null,"函数定义",-1))]),_:1})]),_:1},8,["default-active"])]),e("div",$,[t[11]||(t[11]=e("h3",null,"高级特性",-1)),s(T,{"default-active":r.value,onSelect:S,"background-color":"transparent","text-color":"rgba(255,255,255,0.7)","active-text-color":"#00d4ff"},{default:l(()=>[s(p,{index:"5"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[7]||(t[7]=e("span",null,"面向对象编程",-1))]),_:1}),s(p,{index:"6"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[8]||(t[8]=e("span",null,"异常处理",-1))]),_:1}),s(p,{index:"7"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[9]||(t[9]=e("span",null,"模块和包",-1))]),_:1}),s(p,{index:"8"},{default:l(()=>[s(o,null,{default:l(()=>[s(a(c))]),_:1}),t[10]||(t[10]=e("span",null,"装饰器",-1))]),_:1})]),_:1},8,["default-active"])]),e("div",ee,[t[15]||(t[15]=e("h3",null,"学习进度",-1)),e("div",te,[e("div",se,[t[12]||(t[12]=e("div",{class:"progress-label"},"完成度",-1)),e("div",le,i(u.completion)+"%",1),s(M,{percentage:u.completion,"show-text":!1,color:"#00d4ff"},null,8,["percentage"])]),e("div",ne,[t[13]||(t[13]=e("div",{class:"progress-label"},"已学习",-1)),e("div",oe,i(u.learned)+"/"+i(u.total),1)]),e("div",ae,[t[14]||(t[14]=e("div",{class:"progress-label"},"学习时间",-1)),e("div",ie,i(u.timeSpent),1)])])])]),e("div",re,[e("div",ue,[e("div",de,[e("h2",null,i(v.value.title),1),e("div",pe,[s(g,{size:"small",onClick:E},{default:l(()=>[s(o,null,{default:l(()=>[s(a(B))]),_:1}),_(" "+i(f.value?"隐藏代码":"显示代码"),1)]),_:1}),s(g,{size:"small",onClick:w,loading:x.value},{default:l(()=>[s(o,null,{default:l(()=>[s(a(z))]),_:1}),_(" "+i(x.value?"运行中...":"运行代码"),1)]),_:1},8,["loading"]),s(g,{size:"small",onClick:F},{default:l(()=>[s(o,null,{default:l(()=>[s(a(J))]),_:1}),_(" "+i(v.value.completed?"已学习":"标记完成"),1)]),_:1})])]),e("div",ce,[e("div",ve,[t[16]||(t[16]=e("h3",null,"概念讲解",-1)),e("div",{class:"theory-content",innerHTML:v.value.theory},null,8,_e)]),f.value?(C(),b("div",me,[t[18]||(t[18]=e("h3",null,"代码示例",-1)),e("div",ge,[e("pre",ye,i(v.value.codeExample),1)]),d.value?(C(),b("div",fe,[t[17]||(t[17]=e("h4",null,"运行结果",-1)),e("pre",xe,i(d.value),1)])):k("",!0)])):k("",!0),e("div",he,[t[21]||(t[21]=e("h3",null,"Java vs Python 对比",-1)),e("div",be,[e("div",Ce,[t[19]||(t[19]=e("h4",null,"Java 实现",-1)),e("pre",Se,i(v.value.javaComparison),1)]),e("div",Te,[t[20]||(t[20]=e("h4",null,"Python 实现",-1)),e("pre",ke,i(v.value.pythonComparison),1)])])])])]),e("div",Ee,[s(g,{disabled:r.value<=1,onClick:P},{default:l(()=>[s(o,null,{default:l(()=>[s(a(H))]),_:1}),t[22]||(t[22]=_(" 上一章 ",-1))]),_:1},8,["disabled"]),s(g,{type:"primary",disabled:r.value>=m.value.length,onClick:N},{default:l(()=>[s(o,null,{default:l(()=>[s(a(Q))]),_:1}),t[23]||(t[23]=_(" 下一章 ",-1))]),_:1},8,["disabled"])])])])])}}});const Ae=V(we,[["__scopeId","data-v-0fb2179f"]]);export{Ae as default};
