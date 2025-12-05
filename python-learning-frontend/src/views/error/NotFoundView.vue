<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <h1 class="not-found-title">404</h1>
      <p class="not-found-subtitle">页面未找到</p>
      <p class="not-found-description">抱歉，您访问的页面不存在或已被移除。</p>
      <button class="not-found-button" @click="goBack">返回首页</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/')
}
</script>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.not-found-content {
  text-align: center;
  padding: 2rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.not-found-title {
  font-size: 4rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 1rem;
}

.not-found-subtitle {
  font-size: 1.5rem;
  font-weight: 600;
  color: #666;
  margin-bottom: 1rem;
}

.not-found-description {
  font-size: 1rem;
  color: #999;
  margin-bottom: 2rem;
}

.not-found-button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: white;
  background-color: #409eff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.not-found-button:hover {
  background-color: #66b1ff;
}
</style>