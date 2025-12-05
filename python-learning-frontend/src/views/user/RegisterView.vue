<template>
  <div class="register-container">
    <!-- 粒子背景 -->
    <vue-particles
      id="tsparticles"
      :particlesInit="particlesInit"
      :particlesLoaded="particlesLoaded"
      :options="particlesOptions"
    />

    <!-- 注册表单 -->
    <div class="register-form-wrapper">
      <div class="register-card">
        <!-- 头部 -->
        <div class="register-header">
          <h2 class="register-title">注册新账户</h2>
          <p class="register-subtitle">加入Python学习社区，开启您的编程之旅</p>
        </div>

        <!-- 注册表单 -->
        <el-form
          ref="registerFormRef"
          :model="registerForm"
          :rules="registerRules"
          label-width="80px"
          class="register-form"
          size="large"
        >
          <!-- 用户名 -->
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="registerForm.username"
              placeholder="请输入用户名"
              prefix-icon="User"
              clearable
            />
          </el-form-item>

          <!-- 邮箱 -->
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="registerForm.email"
              placeholder="请输入邮箱地址"
              prefix-icon="Message"
              clearable
            />
          </el-form-item>

          <!-- 密码 -->
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-password
              clearable
            />
          </el-form-item>

          <!-- 确认密码 -->
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              prefix-icon="Lock"
              show-password
              clearable
            />
          </el-form-item>

          <!-- 验证码 -->
          <el-form-item label="验证码" prop="captcha">
            <div class="captcha-container">
              <el-input
                v-model="registerForm.captcha"
                placeholder="请输入验证码"
                prefix-icon="Key"
                class="captcha-input"
                clearable
              />
              <div class="captcha-image" @click="refreshCaptcha">
                <img :src="captchaImage" alt="验证码" />
              </div>
            </div>
          </el-form-item>

          <!-- 协议同意 -->
          <el-form-item prop="agreement">
            <el-checkbox v-model="registerForm.agreement">
              我已阅读并同意
              <el-link type="primary" @click="showAgreement">《用户协议》</el-link>
              和
              <el-link type="primary" @click="showPrivacy">《隐私政策》</el-link>
            </el-checkbox>
          </el-form-item>

          <!-- 注册按钮 -->
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              class="register-button"
              :loading="registerLoading"
              @click="handleRegister"
            >
              {{ registerLoading ? '注册中...' : '立即注册' }}
            </el-button>
          </el-form-item>
        </el-form>

        <!-- 底部链接 -->
        <div class="register-footer">
          <span>已有账户？</span>
          <el-link type="primary" @click="goToLogin">立即登录</el-link>
        </div>
      </div>

      <!-- 特色功能展示 -->
      <div class="features-section">
        <h3>注册即可享受以下功能</h3>
        <div class="features-grid">
          <div class="feature-item">
            <div class="feature-icon">
              <i class="el-icon-cpu"></i>
            </div>
            <div class="feature-content">
              <h4>智能代码转换</h4>
              <p>Java到Python代码一键转换，支持复杂语法结构</p>
            </div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <i class="el-icon-notebook-2"></i>
            </div>
            <div class="feature-content">
              <h4>系统化学习</h4>
              <p>完整的Python学习路径，从基础到高级</p>
            </div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <i class="el-icon-data-analysis"></i>
            </div>
            <div class="feature-content">
              <h4>学习进度跟踪</h4>
              <p>实时记录学习进度，智能推荐学习内容</p>
            </div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <i class="el-icon-medal"></i>
            </div>
            <div class="feature-content">
              <h4>成就系统</h4>
              <p>完成学习任务获得成就，激励学习动力</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 用户协议对话框 -->
    <el-dialog
      v-model="agreementDialogVisible"
      title="用户协议"
      width="600px"
      :before-close="handleAgreementClose"
    >
      <div class="agreement-content">
        <h4>欢迎使用Python学习平台</h4>
        <p>请仔细阅读以下用户协议：</p>
        
        <div class="agreement-section">
          <h5>1. 服务条款</h5>
          <p>本平台提供Python学习相关的在线服务，包括代码转换、学习课程、学习进度跟踪等功能。</p>
        </div>

        <div class="agreement-section">
          <h5>2. 用户责任</h5>
          <p>用户需保证注册信息的真实性，不得使用他人信息注册。用户应妥善保管账户信息，不得泄露给他人。</p>
        </div>

        <div class="agreement-section">
          <h5>3. 知识产权</h5>
          <p>平台提供的学习内容、代码示例等知识产权归平台所有，用户不得用于商业用途。</p>
        </div>

        <div class="agreement-section">
          <h5>4. 隐私保护</h5>
          <p>平台将严格保护用户隐私，不会泄露用户个人信息给第三方。</p>
        </div>

        <div class="agreement-section">
          <h5>5. 服务变更</h5>
          <p>平台有权根据业务发展需要调整服务内容，但会提前通知用户。</p>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="agreementDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="agreeAgreement">同意协议</el-button>
      </template>
    </el-dialog>

    <!-- 隐私政策对话框 -->
    <el-dialog
      v-model="privacyDialogVisible"
      title="隐私政策"
      width="600px"
      :before-close="handlePrivacyClose"
    >
      <div class="privacy-content">
        <h4>隐私政策说明</h4>
        <p>我们非常重视您的隐私保护，请仔细阅读以下隐私政策：</p>
        
        <div class="privacy-section">
          <h5>1. 信息收集</h5>
          <p>我们收集的信息包括：注册信息、学习行为数据、设备信息等，用于提供更好的服务。</p>
        </div>

        <div class="privacy-section">
          <h5>2. 信息使用</h5>
          <p>收集的信息将用于：个性化学习推荐、服务优化、安全保障等目的。</p>
        </div>

        <div class="privacy-section">
          <h5>3. 信息共享</h5>
          <p>我们不会将您的个人信息出售或出租给第三方，除非获得您的明确同意。</p>
        </div>

        <div class="privacy-section">
          <h5>4. 数据安全</h5>
          <p>我们采用加密技术保护您的数据安全，防止未经授权的访问。</p>
        </div>

        <div class="privacy-section">
          <h5>5. 用户权利</h5>
          <p>您有权查看、修改、删除您的个人信息，也可以联系我们进行数据导出。</p>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="privacyDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="agreePrivacy">同意政策</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { loadFull } from 'tsparticles'
import type { Engine } from 'tsparticles-engine'

const router = useRouter()
const registerFormRef = ref<FormInstance>()

// 响应式数据
const registerLoading = ref(false)
const agreementDialogVisible = ref(false)
const privacyDialogVisible = ref(false)
const captchaImage = ref('')

// 注册表单
const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  captcha: '',
  agreement: false
})

// 表单验证规则
const registerRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' },
    { 
      pattern: /^[a-zA-Z0-9_]+$/, 
      message: '用户名只能包含字母、数字和下划线', 
      trigger: 'blur' 
    }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' },
    { 
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/, 
      message: '密码必须包含大小写字母和数字', 
      trigger: 'blur' 
    }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value !== registerForm.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { len: 4, message: '验证码长度为4位', trigger: 'blur' }
  ],
  agreement: [
    {
      validator: (rule: any, value: boolean, callback: any) => {
        if (!value) {
          callback(new Error('请同意用户协议和隐私政策'))
        } else {
          callback()
        }
      },
      trigger: 'change'
    }
  ]
}

// 粒子效果配置
const particlesOptions = {
  background: {
    color: {
      value: 'transparent'
    }
  },
  fpsLimit: 120,
  interactivity: {
    events: {
      onClick: {
        enable: true,
        mode: 'push'
      },
      onHover: {
        enable: true,
        mode: 'repulse'
      }
    },
    modes: {
      push: {
        quantity: 4
      },
      repulse: {
        distance: 200,
        duration: 0.4
      }
    }
  },
  particles: {
    color: {
      value: '#409EFF'
    },
    links: {
      color: '#409EFF',
      distance: 150,
      enable: true,
      opacity: 0.5,
      width: 1
    },
    move: {
      direction: 'none',
      enable: true,
      outModes: {
        default: 'bounce'
      },
      random: false,
      speed: 2,
      straight: false
    },
    number: {
      density: {
        enable: true,
        area: 800
      },
      value: 80
    },
    opacity: {
      value: 0.5
    },
    shape: {
      type: 'circle'
    },
    size: {
      value: { min: 1, max: 5 }
    }
  },
  detectRetina: true
}

// 粒子效果初始化
const particlesInit = async (engine: Engine) => {
  await loadFull(engine)
}

const particlesLoaded = async () => {
  console.log('Particles loaded')
}

// 方法定义
const refreshCaptcha = () => {
  // 模拟刷新验证码
  captchaImage.value = `data:image/svg+xml;base64,${btoa(`
    <svg width="120" height="40" xmlns="http://www.w3.org/2000/svg">
      <rect width="120" height="40" fill="#f5f7fa"/>
      <text x="60" y="25" text-anchor="middle" font-family="Arial" font-size="18" fill="#409EFF">
        ${Math.random().toString(36).substr(2, 4).toUpperCase()}
      </text>
      <line x1="10" y1="15" x2="110" y2="25" stroke="#409EFF" stroke-width="1"/>
      <line x1="10" y1="25" x2="110" y2="15" stroke="#409EFF" stroke-width="1"/>
    </svg>
  `)}`
}

const showAgreement = () => {
  agreementDialogVisible.value = true
}

const showPrivacy = () => {
  privacyDialogVisible.value = true
}

const handleAgreementClose = (done: () => void) => {
  ElMessageBox.confirm('确定要关闭用户协议吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    done()
  }).catch(() => {
    // 取消关闭
  })
}

const handlePrivacyClose = (done: () => void) => {
  ElMessageBox.confirm('确定要关闭隐私政策吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    done()
  }).catch(() => {
    // 取消关闭
  })
}

const agreeAgreement = () => {
  registerForm.agreement = true
  agreementDialogVisible.value = false
  ElMessage.success('已同意用户协议')
}

const agreePrivacy = () => {
  registerForm.agreement = true
  privacyDialogVisible.value = false
  ElMessage.success('已同意隐私政策')
}

const handleRegister = async () => {
  if (!registerFormRef.value) return

  try {
    // 表单验证
    await registerFormRef.value.validate()
    
    registerLoading.value = true
    
    // 模拟注册请求
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    ElMessage.success('注册成功！欢迎加入Python学习社区')
    
    // 跳转到登录页面
    router.push('/login')
  } catch (error) {
    ElMessage.error('注册失败，请检查表单信息')
  } finally {
    registerLoading.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}

// 生命周期
onMounted(() => {
  // 初始化验证码
  refreshCaptcha()
})
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

#tsparticles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.register-form-wrapper {
  display: flex;
  max-width: 1200px;
  width: 90%;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  backdrop-filter: blur(10px);
  z-index: 1;
}

.register-card {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.register-header {
  text-align: center;
  margin-bottom: 40px;
}

.register-title {
  font-size: 2.5rem;
  color: #2c3e50;
  margin-bottom: 10px;
  font-weight: 600;
}

.register-subtitle {
  font-size: 1.1rem;
  color: #7f8c8d;
  margin: 0;
}

.register-form {
  max-width: 400px;
  margin: 0 auto;
  width: 100%;
}

.captcha-container {
  display: flex;
  gap: 10px;
  align-items: center;
}

.captcha-input {
  flex: 1;
}

.captcha-image {
  width: 120px;
  height: 40px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  transition: all 0.3s;
}

.captcha-image:hover {
  border-color: #409eff;
  background: #ecf5ff;
}

.captcha-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.register-button {
  width: 100%;
  height: 50px;
  font-size: 1.1rem;
  border-radius: 8px;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  border: none;
  transition: all 0.3s;
}

.register-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(64, 158, 255, 0.3);
}

.register-footer {
  text-align: center;
  margin-top: 20px;
  color: #7f8c8d;
}

.features-section {
  flex: 1;
  padding: 40px;
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.features-section h3 {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 30px;
  font-size: 1.8rem;
}

.features-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  transition: all 0.3s;
}

.feature-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 2rem;
  color: #409eff;
  margin-right: 15px;
  width: 50px;
  text-align: center;
}

.feature-content h4 {
  color: #2c3e50;
  margin-bottom: 8px;
  font-size: 1.1rem;
}

.feature-content p {
  color: #7f8c8d;
  font-size: 0.9rem;
  line-height: 1.4;
  margin: 0;
}

.agreement-content,
.privacy-content {
  max-height: 400px;
  overflow-y: auto;
  padding-right: 10px;
}

.agreement-section,
.privacy-section {
  margin-bottom: 20px;
}

.agreement-section h5,
.privacy-section h5 {
  color: #2c3e50;
  margin-bottom: 8px;
}

.agreement-section p,
.privacy-section p {
  color: #606266;
  line-height: 1.6;
  margin: 0;
}

@media (max-width: 768px) {
  .register-form-wrapper {
    flex-direction: column;
    width: 95%;
  }
  
  .register-card {
    padding: 30px 20px;
  }
  
  .features-section {
    padding: 30px 20px;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
  }
  
  .register-title {
    font-size: 2rem;
  }
}
</style>