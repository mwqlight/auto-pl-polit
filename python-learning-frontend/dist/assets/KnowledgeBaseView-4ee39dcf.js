import{_ as K}from"./index-4abfcac9.js";/* empty css                   *//* empty css                  *//* empty css                 */import{E as Q,t as O,k as W,H,P as J,z as $,i as G,Q as F,l as X,R as Y,n as Z,G as ee,S as te}from"./element-ui-92f352ec.js";import{y as se,r as _,c as I,A as u,B as t,Q as a,I as i,M as p,P as m,a4 as f,z as d,u as g,D as T,O as o,H as ae,L as le}from"./vue-vendor-8137f92e.js";import"./monaco-aa1fb2c3.js";const oe={class:"knowledge-base-view"},ie={class:"page-header"},ne={class:"page-title"},re={class:"knowledge-container"},de={class:"search-section"},ce={class:"search-bar"},ue={class:"filter-section"},pe={class:"knowledge-grid"},_e=["onClick"],ve={class:"card-header"},me={class:"card-content"},fe={class:"card-title"},ge={class:"card-desc"},ye={class:"tech-stack"},be={class:"card-footer"},we={class:"stats"},he={class:"stat"},ke={class:"stat"},Pe={class:"update-time"},Ee={class:"pagination-section"},Ve={key:0,class:"detail-content"},De={class:"detail-header"},Se={class:"detail-meta"},Ae={class:"meta-tags"},qe={class:"meta-stats"},ze={class:"detail-body"},Ce={class:"detail-section"},Fe={class:"detail-section"},Ie={class:"tech-details"},Te={class:"detail-section"},Re={class:"code-example"},xe={class:"code-display"},Le={class:"detail-section"},Ue={class:"best-practices"},je={class:"detail-section"},Ne={class:"faq-list"},Me={class:"faq-question"},Be={class:"faq-answer"},Ke=se({__name:"KnowledgeBaseView",setup(Qe){const y=_(""),b=_(""),w=_(""),h=_(""),S=_(1),A=_(12),v=_(!1),n=_(null),R=_([{id:1,title:"Django REST API开发",description:"使用Django REST Framework构建高性能的RESTful API服务",category:"web",difficulty:"intermediate",technologies:["Django","DRF","PostgreSQL","Redis"],views:1245,rating:4.8,updatedAt:"2024-01-15",scenario:"构建企业级后端API服务，支持用户管理、数据接口、权限控制等功能",technologyDetails:[{name:"Django",description:"高级Python Web框架，提供完整的MVT架构"},{name:"DRF",description:"Django REST Framework，强大的API开发工具包"},{name:"PostgreSQL",description:"企业级关系型数据库"},{name:"Redis",description:"内存数据存储，用于缓存和会话管理"}],codeExample:`# settings.py配置
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
        serializer.save(created_by=self.request.user)`,bestPractices:["使用序列化器进行数据验证和转换","实现适当的权限控制和认证机制","使用分页优化大数据集查询","实现API版本控制","添加适当的缓存策略"],faq:[{question:"如何处理复杂的关联查询？",answer:"可以使用prefetch_related和select_related优化查询性能"},{question:"如何实现文件上传功能？",answer:"使用FileField和ImageField，配合适当的存储后端"}]},{id:2,title:"Pandas数据分析",description:"使用Pandas进行数据清洗、转换和分析的完整流程",category:"data",difficulty:"beginner",technologies:["Pandas","NumPy","Matplotlib","Jupyter"],views:2890,rating:4.6,updatedAt:"2024-01-10",scenario:"处理结构化数据，进行数据清洗、统计分析、可视化展示",technologyDetails:[{name:"Pandas",description:"强大的数据处理和分析库"},{name:"NumPy",description:"科学计算基础库"},{name:"Matplotlib",description:"数据可视化库"},{name:"Jupyter",description:"交互式编程环境"}],codeExample:`import pandas as pd
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
print("
描述性统计:")
print(df.describe())

# 数据可视化
df['sales'].plot(kind='hist', bins=20)
plt.title('销售数据分布')
plt.show()`,bestPractices:["在处理大型数据集时使用适当的数据类型","使用向量化操作替代循环","定期保存中间结果","使用分组和聚合进行数据分析"],faq:[{question:"如何处理内存不足的问题？",answer:"可以使用chunksize分块读取，或者使用Dask处理超大数据集"},{question:"如何优化Pandas性能？",answer:"使用NumPy操作、避免链式赋值、使用适当的数据类型"}]},{id:3,title:"Flask微服务架构",description:"基于Flask构建微服务架构的完整解决方案",category:"web",difficulty:"advanced",technologies:["Flask","Docker","Kubernetes","RabbitMQ"],views:876,rating:4.9,updatedAt:"2024-01-08",scenario:"构建可扩展的微服务系统，支持服务发现、负载均衡、容错处理",technologyDetails:[{name:"Flask",description:"轻量级Web框架，适合微服务开发"},{name:"Docker",description:"容器化部署工具"},{name:"Kubernetes",description:"容器编排平台"},{name:"RabbitMQ",description:"消息队列，用于服务间通信"}],codeExample:`from flask import Flask, jsonify
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
    app.run(port=5001)`,bestPractices:["每个服务保持单一职责","使用API网关进行路由和认证","实现服务发现和负载均衡","添加适当的监控和日志","使用消息队列进行异步通信"],faq:[{question:"如何处理服务间的数据一致性？",answer:"可以使用Saga模式或事件溯源保证最终一致性"},{question:"如何实现服务熔断？",answer:"可以使用Hystrix或Resilience4j等熔断器库"}]}]),x=I(()=>q.value.length),q=I(()=>{let l=R.value;if(y.value){const e=y.value.toLowerCase();l=l.filter(c=>c.title.toLowerCase().includes(e)||c.description.toLowerCase().includes(e)||c.technologies.some(k=>k.toLowerCase().includes(e)))}return b.value&&(l=l.filter(e=>e.category===b.value)),w.value&&(l=l.filter(e=>e.difficulty===w.value)),h.value&&(l=l.filter(e=>e.technologies.some(c=>c.toLowerCase().includes(h.value)))),l}),z=l=>({web:"Web开发",data:"数据分析",ai:"人工智能",automation:"自动化脚本",network:"网络编程",game:"游戏开发"})[l]||l,C=l=>({beginner:"入门",intermediate:"中级",advanced:"高级"})[l]||l,L=l=>({web:"primary",data:"success",ai:"warning",automation:"info",network:"danger",game:""})[l]||"",U=l=>({beginner:"success",intermediate:"warning",advanced:"danger"})[l]||"",j=l=>{n.value=l,v.value=!0,l.views++},N=()=>{Z.success("示例代码已复制到剪贴板，请在开发环境中尝试"),v.value=!1};return(l,e)=>{const c=Q,k=O,P=W,r=ee,E=H,V=te,M=J,B=$;return d(),u("div",oe,[t("div",ie,[t("h1",ne,[a(c,null,{default:i(()=>[a(g(G))]),_:1}),e[8]||(e[8]=p(" Python 全场景应用知识库 ",-1))]),e[9]||(e[9]=t("p",{class:"page-desc"},"探索Python在不同领域的实际应用案例和最佳实践",-1))]),t("div",re,[t("div",de,[t("div",ce,[a(k,{modelValue:y.value,"onUpdate:modelValue":e[0]||(e[0]=s=>y.value=s),placeholder:"搜索应用场景、技术栈或关键词...",size:"large",clearable:""},{prefix:i(()=>[a(c,null,{default:i(()=>[a(g(F))]),_:1})]),_:1},8,["modelValue"]),a(P,{type:"primary",size:"large"},{default:i(()=>[a(c,null,{default:i(()=>[a(g(F))]),_:1}),e[10]||(e[10]=p(" 搜索 ",-1))]),_:1})]),t("div",ue,[a(E,{modelValue:b.value,"onUpdate:modelValue":e[1]||(e[1]=s=>b.value=s),placeholder:"选择应用领域",clearable:""},{default:i(()=>[a(r,{label:"Web开发",value:"web"}),a(r,{label:"数据分析",value:"data"}),a(r,{label:"人工智能",value:"ai"}),a(r,{label:"自动化脚本",value:"automation"}),a(r,{label:"网络编程",value:"network"}),a(r,{label:"游戏开发",value:"game"})]),_:1},8,["modelValue"]),a(E,{modelValue:w.value,"onUpdate:modelValue":e[2]||(e[2]=s=>w.value=s),placeholder:"选择难度级别",clearable:""},{default:i(()=>[a(r,{label:"入门",value:"beginner"}),a(r,{label:"中级",value:"intermediate"}),a(r,{label:"高级",value:"advanced"})]),_:1},8,["modelValue"]),a(E,{modelValue:h.value,"onUpdate:modelValue":e[3]||(e[3]=s=>h.value=s),placeholder:"选择技术栈",clearable:""},{default:i(()=>[a(r,{label:"Django",value:"django"}),a(r,{label:"Flask",value:"flask"}),a(r,{label:"Pandas",value:"pandas"}),a(r,{label:"NumPy",value:"numpy"}),a(r,{label:"TensorFlow",value:"tensorflow"}),a(r,{label:"PyTorch",value:"pytorch"})]),_:1},8,["modelValue"])])]),t("div",pe,[(d(!0),u(m,null,f(q.value,s=>(d(),u("div",{key:s.id,class:"knowledge-card",onClick:D=>j(s)},[t("div",ve,[t("div",{class:T(["category-tag",s.category])},o(z(s.category)),3),t("div",{class:T(["difficulty-badge",s.difficulty])},o(C(s.difficulty)),3)]),t("div",me,[t("h3",fe,o(s.title),1),t("p",ge,o(s.description),1),t("div",ye,[(d(!0),u(m,null,f(s.technologies,D=>(d(),ae(V,{key:D,size:"small",type:"info"},{default:i(()=>[p(o(D),1)]),_:2},1024))),128))])]),t("div",be,[t("div",we,[t("span",he,[a(c,null,{default:i(()=>[a(g(X))]),_:1}),p(" "+o(s.views),1)]),t("span",ke,[a(c,null,{default:i(()=>[a(g(Y))]),_:1}),p(" "+o(s.rating),1)])]),t("div",Pe," 更新: "+o(s.updatedAt),1)])],8,_e))),128))]),t("div",Ee,[a(M,{"current-page":S.value,"onUpdate:currentPage":e[4]||(e[4]=s=>S.value=s),"page-size":A.value,"onUpdate:pageSize":e[5]||(e[5]=s=>A.value=s),"page-sizes":[12,24,36,48],total:x.value,layout:"total, sizes, prev, pager, next, jumper",background:""},null,8,["current-page","page-size","total"])])]),a(B,{modelValue:v.value,"onUpdate:modelValue":e[7]||(e[7]=s=>v.value=s),title:n.value?.title,width:"90%",top:"5vh","custom-class":"knowledge-detail-dialog"},{footer:i(()=>[a(P,{onClick:e[6]||(e[6]=s=>v.value=!1)},{default:i(()=>[...e[16]||(e[16]=[p("关闭",-1)])]),_:1}),a(P,{type:"primary",onClick:N},{default:i(()=>[...e[17]||(e[17]=[p("尝试示例",-1)])]),_:1})]),default:i(()=>[n.value?(d(),u("div",Ve,[t("div",De,[t("div",Se,[t("div",Ae,[a(V,{type:L(n.value.category)},{default:i(()=>[p(o(z(n.value.category)),1)]),_:1},8,["type"]),a(V,{type:U(n.value.difficulty)},{default:i(()=>[p(o(C(n.value.difficulty)),1)]),_:1},8,["type"])]),t("div",qe,[t("span",null,"浏览: "+o(n.value.views),1),t("span",null,"评分: "+o(n.value.rating)+"/5.0",1),t("span",null,"更新: "+o(n.value.updatedAt),1)])])]),t("div",ze,[t("div",Ce,[e[11]||(e[11]=t("h3",null,"应用场景",-1)),t("p",null,o(n.value.scenario),1)]),t("div",Fe,[e[12]||(e[12]=t("h3",null,"技术栈",-1)),t("div",Ie,[(d(!0),u(m,null,f(n.value.technologyDetails,s=>(d(),u("div",{key:s.name,class:"tech-item"},[t("strong",null,o(s.name)+":",1),p(" "+o(s.description),1)]))),128))])]),t("div",Te,[e[13]||(e[13]=t("h3",null,"代码示例",-1)),t("div",Re,[t("pre",xe,o(n.value.codeExample),1)])]),t("div",Le,[e[14]||(e[14]=t("h3",null,"最佳实践",-1)),t("ul",Ue,[(d(!0),u(m,null,f(n.value.bestPractices,s=>(d(),u("li",{key:s},o(s),1))),128))])]),t("div",je,[e[15]||(e[15]=t("h3",null,"常见问题",-1)),t("div",Ne,[(d(!0),u(m,null,f(n.value.faq,s=>(d(),u("div",{key:s.question,class:"faq-item"},[t("div",Me,[t("strong",null,"Q: "+o(s.question),1)]),t("div",Be," A: "+o(s.answer),1)]))),128))])])])])):le("",!0)]),_:1},8,["modelValue","title"])])}}});const Ye=K(Ke,[["__scopeId","data-v-7cd9f191"]]);export{Ye as default};
