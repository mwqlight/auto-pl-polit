<template>
  <div id="app" class="app-container">
    <!-- 粒子背景 -->
    <ParticleBackground />
    
    <!-- 主布局 -->
    <div class="main-layout">
      <!-- 顶部导航 -->
      <HeaderNav />
      
      <!-- 侧边栏和内容区 -->
      <div class="content-wrapper">
        <!-- 侧边栏导航 -->
        <SidebarNav />
        
        <!-- 主内容区 -->
        <main class="main-content">
          <router-view v-slot="{ Component }">
            <transition name="fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </main>
      </div>
      
      <!-- 底部状态栏 -->
      <FooterStatus />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ParticleBackground from '@/components/base/ParticleBackground.vue'
import HeaderNav from '@/components/layout/HeaderNav.vue'
import SidebarNav from '@/components/layout/SidebarNav.vue'
import FooterStatus from '@/components/layout/FooterStatus.vue'

const router = useRouter()

onMounted(() => {
  console.log('Python学习平台前端界面已加载')
  console.log('后端服务地址: http://localhost:8080')
  console.log('API文档地址: http://localhost:8080/swagger-ui.html')
})
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background: linear-gradient(135deg, #0c0c0c 0%, #1a1a2e 50%, #16213e 100%);
  color: #ffffff;
  overflow: hidden;
}

#app {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.main-layout {
  position: relative;
  z-index: 1;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 0;
  background: rgba(255, 255, 255, 0.02);
}

// 路由过渡动画
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

// 滚动条样式
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
}

::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.5);
}
</style>