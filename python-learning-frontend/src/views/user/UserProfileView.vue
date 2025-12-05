<template>
  <div class="user-profile-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="page-title">用户信息</h2>
      <p class="page-description">管理您的个人信息和学习设置</p>
    </div>

    <!-- 用户信息卡片 -->
    <el-row :gutter="20">
      <!-- 基本信息 -->
      <el-col :span="8">
        <el-card class="info-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">基本信息</span>
              <el-button type="primary" size="small" @click="editBasicInfo">
                编辑
              </el-button>
            </div>
          </template>

          <div class="user-avatar">
            <el-avatar :size="80" :src="userInfo.avatar" />
            <div class="avatar-actions">
              <el-button type="text" @click="changeAvatar">更换头像</el-button>
            </div>
          </div>

          <div class="user-info">
            <div class="info-item">
              <label>用户名：</label>
              <span>{{ userInfo.username }}</span>
            </div>
            <div class="info-item">
              <label>邮箱：</label>
              <span>{{ userInfo.email }}</span>
            </div>
            <div class="info-item">
              <label>注册时间：</label>
              <span>{{ formatDate(userInfo.createTime) }}</span>
            </div>
            <div class="info-item">
              <label>最后登录：</label>
              <span>{{ formatDate(userInfo.lastLoginTime) }}</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 学习统计 -->
      <el-col :span="8">
        <el-card class="stats-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">学习统计</span>
            </div>
          </template>

          <div class="stats-content">
            <div class="stat-item">
              <div class="stat-icon">
                <i class="el-icon-notebook-2"></i>
              </div>
              <div class="stat-info">
                <div class="stat-value">{{ userStats.completedCourses }}</div>
                <div class="stat-label">已完成课程</div>
              </div>
            </div>

            <div class="stat-item">
              <div class="stat-icon">
                <i class="el-icon-time"></i>
              </div>
              <div class="stat-info">
                <div class="stat-value">{{ formatLearningTime(userStats.totalLearningTime) }}</div>
                <div class="stat-label">总学习时长</div>
              </div>
            </div>

            <div class="stat-item">
              <div class="stat-icon">
                <i class="el-icon-star"></i>
              </div>
              <div class="stat-info">
                <div class="stat-value">{{ userStats.conversionCount }}</div>
                <div class="stat-label">代码转换次数</div>
              </div>
            </div>

            <div class="stat-item">
              <div class="stat-icon">
                <i class="el-icon-medal"></i>
              </div>
              <div class="stat-info">
                <div class="stat-value">{{ userStats.achievementCount }}</div>
                <div class="stat-label">获得成就</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 学习进度 -->
      <el-col :span="8">
        <el-card class="progress-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">学习进度</span>
            </div>
          </template>

          <div class="progress-content">
            <div class="module-progress" v-for="module in learningProgress" :key="module.id">
              <div class="module-info">
                <span class="module-name">{{ module.name }}</span>
                <span class="module-percent">{{ module.progress }}%</span>
              </div>
              <el-progress 
                :percentage="module.progress" 
                :stroke-width="8"
                :show-text="false"
              />
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 设置区域 -->
    <el-card class="settings-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span class="card-title">个人设置</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <!-- 通知设置 -->
        <el-tab-pane label="通知设置" name="notifications">
          <div class="settings-section">
            <h4>通知偏好</h4>
            <div class="setting-item">
              <el-switch
                v-model="settings.emailNotifications"
                active-text="邮件通知"
                inactive-text="关闭邮件通知"
              />
              <span class="setting-description">接收学习进度和系统通知邮件</span>
            </div>
            <div class="setting-item">
              <el-switch
                v-model="settings.courseReminders"
                active-text="课程提醒"
                inactive-text="关闭课程提醒"
              />
              <span class="setting-description">接收课程学习提醒和推荐</span>
            </div>
            <div class="setting-item">
              <el-switch
                v-model="settings.achievementAlerts"
                active-text="成就提醒"
                inactive-text="关闭成就提醒"
              />
              <span class="setting-description">接收成就获得提醒</span>
            </div>
          </div>
        </el-tab-pane>

        <!-- 学习设置 -->
        <el-tab-pane label="学习设置" name="learning">
          <div class="settings-section">
            <h4>学习偏好</h4>
            <div class="setting-item">
              <label>默认语言：</label>
              <el-select v-model="settings.defaultLanguage" placeholder="选择语言">
                <el-option label="中文" value="zh-CN" />
                <el-option label="English" value="en-US" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>代码主题：</label>
              <el-select v-model="settings.codeTheme" placeholder="选择代码主题">
                <el-option label="默认" value="default" />
                <el-option label="暗色" value="dark" />
                <el-option label="高对比度" value="high-contrast" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>自动保存间隔：</label>
              <el-select v-model="settings.autoSaveInterval" placeholder="选择保存间隔">
                <el-option label="30秒" value="30" />
                <el-option label="1分钟" value="60" />
                <el-option label="5分钟" value="300" />
              </el-select>
            </div>
          </div>
        </el-tab-pane>

        <!-- 安全设置 -->
        <el-tab-pane label="安全设置" name="security">
          <div class="settings-section">
            <h4>账户安全</h4>
            <div class="setting-item">
              <el-button type="warning" @click="changePassword">
                修改密码
              </el-button>
              <span class="setting-description">定期修改密码保护账户安全</span>
            </div>
            <div class="setting-item">
              <el-button type="info" @click="viewLoginHistory">
                查看登录历史
              </el-button>
              <span class="setting-description">查看最近的登录记录和设备信息</span>
            </div>
            <div class="setting-item">
              <el-button type="danger" @click="deleteAccount" plain>
                注销账户
              </el-button>
              <span class="setting-description">永久删除账户和所有数据</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>

      <div class="settings-actions">
        <el-button type="primary" @click="saveSettings">保存设置</el-button>
        <el-button @click="resetSettings">重置</el-button>
      </div>
    </el-card>

    <!-- 编辑基本信息对话框 -->
    <el-dialog
      v-model="editDialogVisible"
      title="编辑基本信息"
      width="500px"
      :before-close="handleEditDialogClose"
    >
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" type="email" />
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input
            v-model="editForm.bio"
            type="textarea"
            :rows="3"
            placeholder="介绍一下自己..."
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveBasicInfo">保存</el-button>
      </template>
    </el-dialog>

    <!-- 修改密码对话框 -->
    <el-dialog
      v-model="passwordDialogVisible"
      title="修改密码"
      width="400px"
    >
      <el-form :model="passwordForm" label-width="80px" :rules="passwordRules" ref="passwordFormRef">
        <el-form-item label="当前密码" prop="currentPassword">
          <el-input v-model="passwordForm.currentPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="savePassword">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'

// 响应式数据
const activeTab = ref('notifications')
const editDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const passwordFormRef = ref<FormInstance>()

// 用户信息
const userInfo = reactive({
  username: 'Python学习者',
  email: 'user@example.com',
  avatar: '',
  bio: '热爱编程的Python学习者',
  createTime: '2024-01-01T00:00:00Z',
  lastLoginTime: '2024-03-15T10:30:00Z'
})

// 学习统计
const userStats = reactive({
  completedCourses: 12,
  totalLearningTime: 86400, // 秒
  conversionCount: 45,
  achievementCount: 8
})

// 学习进度
const learningProgress = reactive([
  { id: 'basic', name: '基础语法', progress: 85 },
  { id: 'oop', name: '面向对象', progress: 60 },
  { id: 'functional', name: '函数式编程', progress: 40 },
  { id: 'exceptions', name: '异常处理', progress: 20 }
])

// 设置
const settings = reactive({
  emailNotifications: true,
  courseReminders: true,
  achievementAlerts: true,
  defaultLanguage: 'zh-CN',
  codeTheme: 'default',
  autoSaveInterval: '60'
})

// 编辑表单
const editForm = reactive({
  username: '',
  email: '',
  bio: ''
})

// 密码表单
const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 密码验证规则
const passwordRules: FormRules = {
  currentPassword: [
    { required: true, message: '请输入当前密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 工具函数
const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleString('zh-CN')
}

const formatLearningTime = (seconds: number) => {
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  return `${hours}小时${minutes}分钟`
}

// 方法定义
const editBasicInfo = () => {
  editForm.username = userInfo.username
  editForm.email = userInfo.email
  editForm.bio = userInfo.bio
  editDialogVisible.value = true
}

const handleEditDialogClose = (done: () => void) => {
  ElMessageBox.confirm('确定要取消编辑吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    done()
  }).catch(() => {
    // 取消关闭
  })
}

const saveBasicInfo = () => {
  userInfo.username = editForm.username
  userInfo.email = editForm.email
  userInfo.bio = editForm.bio
  editDialogVisible.value = false
  ElMessage.success('基本信息更新成功')
}

const changeAvatar = () => {
  ElMessage.info('头像上传功能开发中')
}

const changePassword = () => {
  passwordForm.currentPassword = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
  passwordDialogVisible.value = true
}

const savePassword = async () => {
  if (!passwordFormRef.value) return
  
  try {
    await passwordFormRef.value.validate()
    
    // 模拟密码修改
    ElMessage.success('密码修改成功')
    passwordDialogVisible.value = false
  } catch (error) {
    ElMessage.error('表单验证失败')
  }
}

const viewLoginHistory = () => {
  ElMessage.info('登录历史功能开发中')
}

const deleteAccount = () => {
  ElMessageBox.confirm('此操作将永久删除您的账户，是否继续？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'error',
    center: true
  }).then(() => {
    ElMessage.success('账户删除成功')
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

const saveSettings = () => {
  ElMessage.success('设置保存成功')
}

const resetSettings = () => {
  ElMessageBox.confirm('确定要重置所有设置吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    Object.assign(settings, {
      emailNotifications: true,
      courseReminders: true,
      achievementAlerts: true,
      defaultLanguage: 'zh-CN',
      codeTheme: 'default',
      autoSaveInterval: '60'
    })
    ElMessage.success('设置已重置')
  }).catch(() => {
    // 取消重置
  })
}

// 生命周期
onMounted(() => {
  // 模拟加载用户数据
  console.log('用户信息页面加载完成')
})
</script>

<style scoped>
.user-profile-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-title {
  font-size: 2.2rem;
  color: #2c3e50;
  margin-bottom: 10px;
}

.page-description {
  font-size: 1rem;
  color: #7f8c8d;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #2c3e50;
}

.info-card,
.stats-card,
.progress-card {
  height: 100%;
  margin-bottom: 20px;
}

.user-avatar {
  text-align: center;
  margin-bottom: 20px;
}

.avatar-actions {
  margin-top: 10px;
}

.user-info {
  margin-top: 20px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item label {
  font-weight: 500;
  color: #606266;
}

.info-item span {
  color: #909399;
}

.stats-content {
  padding: 10px 0;
}

.stat-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.stat-item:last-child {
  border-bottom: none;
}

.stat-icon {
  font-size: 24px;
  color: #409eff;
  margin-right: 15px;
  width: 40px;
  text-align: center;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 600;
  color: #2c3e50;
}

.stat-label {
  font-size: 0.9rem;
  color: #909399;
}

.progress-content {
  padding: 10px 0;
}

.module-progress {
  margin-bottom: 15px;
}

.module-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.module-name {
  font-size: 0.9rem;
  color: #606266;
}

.module-percent {
  font-size: 0.8rem;
  color: #909399;
}

.settings-card {
  margin-top: 20px;
}

.settings-section {
  padding: 20px;
}

.settings-section h4 {
  margin-bottom: 20px;
  color: #2c3e50;
  border-bottom: 1px solid #eaeaea;
  padding-bottom: 10px;
}

.setting-item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.setting-item label {
  min-width: 100px;
  font-weight: 500;
  color: #606266;
  margin-right: 15px;
}

.setting-description {
  margin-left: 15px;
  font-size: 0.9rem;
  color: #909399;
}

.settings-actions {
  text-align: center;
  padding: 20px;
  border-top: 1px solid #eaeaea;
}

@media (max-width: 768px) {
  .user-profile-container {
    padding: 10px;
  }
  
  .el-col {
    margin-bottom: 20px;
  }
  
  .setting-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .setting-item label {
    margin-bottom: 10px;
  }
  
  .setting-description {
    margin-left: 0;
    margin-top: 10px;
  }
}
</style>