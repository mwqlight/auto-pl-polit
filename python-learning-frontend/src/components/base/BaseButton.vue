<template>
  <button
    :class="buttonClasses"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <span v-if="loading" class="button-loading">
      <i class="el-icon-loading"></i>
    </span>
    <span v-else-if="icon" class="button-icon">
      <i :class="icon"></i>
    </span>
    <span class="button-text">
      <slot></slot>
    </span>
  </button>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info' | 'text'
  size?: 'large' | 'default' | 'small'
  disabled?: boolean
  loading?: boolean
  round?: boolean
  circle?: boolean
  plain?: boolean
  icon?: string
}

const props = withDefaults(defineProps<Props>(), {
  type: 'primary',
  size: 'default',
  disabled: false,
  loading: false,
  round: false,
  circle: false,
  plain: false,
  icon: ''
})

const emit = defineEmits<{
  click: [event: MouseEvent]
}>()

const buttonClasses = computed(() => ({
  'base-button': true,
  [`base-button--${props.type}`]: true,
  [`base-button--${props.size}`]: true,
  'base-button--disabled': props.disabled,
  'base-button--loading': props.loading,
  'base-button--round': props.round,
  'base-button--circle': props.circle,
  'base-button--plain': props.plain
}))

const handleClick = (event: MouseEvent) => {
  if (!props.disabled && !props.loading) {
    emit('click', event)
  }
}
</script>

<style scoped>
.base-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background: #ffffff;
  color: #606266;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
  outline: none;
  user-select: none;
  position: relative;
}

.base-button:hover {
  border-color: #c6e2ff;
  background: #ecf5ff;
  color: #409eff;
}

.base-button:active {
  transform: translateY(1px);
}

/* 类型样式 */
.base-button--primary {
  border-color: #409eff;
  background: #409eff;
  color: #ffffff;
}

.base-button--primary:hover {
  background: #66b1ff;
  border-color: #66b1ff;
}

.base-button--success {
  border-color: #67c23a;
  background: #67c23a;
  color: #ffffff;
}

.base-button--success:hover {
  background: #85ce61;
  border-color: #85ce61;
}

.base-button--warning {
  border-color: #e6a23c;
  background: #e6a23c;
  color: #ffffff;
}

.base-button--warning:hover {
  background: #ebb563;
  border-color: #ebb563;
}

.base-button--danger {
  border-color: #f56c6c;
  background: #f56c6c;
  color: #ffffff;
}

.base-button--danger:hover {
  background: #f78989;
  border-color: #f78989;
}

.base-button--info {
  border-color: #909399;
  background: #909399;
  color: #ffffff;
}

.base-button--info:hover {
  background: #a6a9ad;
  border-color: #a6a9ad;
}

.base-button--text {
  border-color: transparent;
  background: transparent;
  color: #409eff;
}

.base-button--text:hover {
  color: #66b1ff;
  background: transparent;
}

/* 尺寸样式 */
.base-button--large {
  padding: 12px 20px;
  font-size: 16px;
}

.base-button--default {
  padding: 8px 16px;
  font-size: 14px;
}

.base-button--small {
  padding: 6px 12px;
  font-size: 12px;
}

/* 特殊样式 */
.base-button--round {
  border-radius: 20px;
}

.base-button--circle {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  padding: 0;
}

.base-button--circle.base-button--large {
  width: 48px;
  height: 48px;
}

.base-button--circle.base-button--small {
  width: 32px;
  height: 32px;
}

.base-button--plain {
  background: transparent;
}

.base-button--plain.base-button--primary {
  color: #409eff;
}

.base-button--plain.base-button--success {
  color: #67c23a;
}

.base-button--plain.base-button--warning {
  color: #e6a23c;
}

.base-button--plain.base-button--danger {
  color: #f56c6c;
}

.base-button--plain.base-button--info {
  color: #909399;
}

/* 禁用状态 */
.base-button--disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.base-button--disabled:hover {
  background: inherit;
  border-color: inherit;
  color: inherit;
  transform: none;
}

/* 加载状态 */
.base-button--loading {
  opacity: 0.7;
  cursor: not-allowed;
}

.button-loading,
.button-icon {
  margin-right: 6px;
  font-size: inherit;
}

.button-text {
  display: inline-flex;
  align-items: center;
}

/* 响应式 */
@media (max-width: 768px) {
  .base-button--large {
    padding: 10px 16px;
    font-size: 14px;
  }
  
  .base-button--default {
    padding: 6px 12px;
    font-size: 13px;
  }
  
  .base-button--small {
    padding: 4px 8px;
    font-size: 12px;
  }
}
</style>