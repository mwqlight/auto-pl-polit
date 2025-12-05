<template>
  <div :class="wrapperClasses">
    <label v-if="label" :for="id" :class="labelClasses">
      {{ label }}
      <span v-if="required" class="required-mark">*</span>
    </label>
    
    <div :class="inputContainerClasses">
      <div v-if="prefixIcon" class="input-prefix">
        <i :class="prefixIcon"></i>
      </div>
      
      <input
        :id="id"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        :maxlength="maxlength"
        :class="inputClasses"
        @input="handleInput"
        @focus="handleFocus"
        @blur="handleBlur"
        @keydown="handleKeydown"
      />
      
      <div v-if="suffixIcon || showClear || showPassword" class="input-suffix">
        <i
          v-if="showClear && modelValue && !disabled"
          class="el-icon-close input-clear"
          @click="handleClear"
        ></i>
        <i
          v-if="showPassword && type === 'password'"
          :class="passwordVisible ? 'el-icon-view' : 'el-icon-view-off'"
          class="input-password-toggle"
          @click="togglePasswordVisibility"
        ></i>
        <i v-if="suffixIcon && !showClear && !showPassword" :class="suffixIcon"></i>
      </div>
    </div>
    
    <div v-if="showWordLimit && maxlength" class="word-limit">
      {{ modelValue?.length || 0 }}/{{ maxlength }}
    </div>
    
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    
    <div v-if="helpText" class="help-text">
      {{ helpText }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'

interface Props {
  modelValue?: string
  type?: 'text' | 'password' | 'number' | 'email' | 'tel' | 'url'
  label?: string
  placeholder?: string
  disabled?: boolean
  readonly?: boolean
  required?: boolean
  size?: 'large' | 'default' | 'small'
  prefixIcon?: string
  suffixIcon?: string
  showClear?: boolean
  showPassword?: boolean
  showWordLimit?: boolean
  maxlength?: number
  errorMessage?: string
  helpText?: string
  id?: string
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  type: 'text',
  label: '',
  placeholder: '',
  disabled: false,
  readonly: false,
  required: false,
  size: 'default',
  prefixIcon: '',
  suffixIcon: '',
  showClear: false,
  showPassword: false,
  showWordLimit: false,
  maxlength: undefined,
  errorMessage: '',
  helpText: '',
  id: ''
})

const emit = defineEmits<{
  'update:modelValue': [value: string]
  input: [value: string]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
  clear: []
  keydown: [event: KeyboardEvent]
}>()

const passwordVisible = ref(false)
const isFocused = ref(false)

const wrapperClasses = computed(() => ({
  'base-input-wrapper': true,
  'base-input-wrapper--error': !!props.errorMessage,
  'base-input-wrapper--disabled': props.disabled,
  'base-input-wrapper--focused': isFocused.value
}))

const labelClasses = computed(() => ({
  'base-input-label': true,
  'base-input-label--required': props.required
}))

const inputContainerClasses = computed(() => ({
  'base-input-container': true,
  [`base-input-container--${props.size}`]: true,
  'base-input-container--disabled': props.disabled,
  'base-input-container--focused': isFocused.value,
  'base-input-container--error': !!props.errorMessage,
  'base-input-container--with-prefix': !!props.prefixIcon,
  'base-input-container--with-suffix': !!props.suffixIcon || props.showClear || props.showPassword
}))

const inputClasses = computed(() => ({
  'base-input': true,
  [`base-input--${props.size}`]: true
}))

const handleInput = (event: Event) => {
  const value = (event.target as HTMLInputElement).value
  emit('update:modelValue', value)
  emit('input', value)
}

const handleFocus = (event: FocusEvent) => {
  isFocused.value = true
  emit('focus', event)
}

const handleBlur = (event: FocusEvent) => {
  isFocused.value = false
  emit('blur', event)
}

const handleClear = () => {
  emit('update:modelValue', '')
  emit('input', '')
  emit('clear')
}

const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value
}

const handleKeydown = (event: KeyboardEvent) => {
  emit('keydown', event)
}

const currentType = computed(() => {
  if (props.type === 'password' && props.showPassword) {
    return passwordVisible.value ? 'text' : 'password'
  }
  return props.type
})
</script>

<style scoped>
.base-input-wrapper {
  margin-bottom: 16px;
}

.base-input-label {
  display: block;
  margin-bottom: 6px;
  font-size: 14px;
  color: #606266;
  font-weight: 500;
}

.base-input-label--required .required-mark {
  color: #f56c6c;
  margin-left: 4px;
}

.base-input-container {
  position: relative;
  display: flex;
  align-items: center;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background: #ffffff;
  transition: all 0.3s;
}

.base-input-container:hover {
  border-color: #c0c4cc;
}

.base-input-container--focused {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.base-input-container--error {
  border-color: #f56c6c;
}

.base-input-container--error.base-input-container--focused {
  box-shadow: 0 0 0 2px rgba(245, 108, 108, 0.2);
}

.base-input-container--disabled {
  background: #f5f7fa;
  border-color: #e4e7ed;
  cursor: not-allowed;
}

.base-input-container--disabled:hover {
  border-color: #e4e7ed;
}

/* 尺寸样式 */
.base-input-container--large {
  height: 40px;
}

.base-input-container--default {
  height: 36px;
}

.base-input-container--small {
  height: 32px;
}

/* 前缀后缀样式 */
.input-prefix,
.input-suffix {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
  transition: color 0.3s;
}

.input-prefix {
  padding-left: 12px;
  padding-right: 8px;
}

.input-suffix {
  padding-left: 8px;
  padding-right: 12px;
}

.base-input-container--focused .input-prefix,
.base-input-container--focused .input-suffix {
  color: #409eff;
}

.input-clear,
.input-password-toggle {
  cursor: pointer;
  transition: color 0.3s;
}

.input-clear:hover {
  color: #f56c6c;
}

.input-password-toggle:hover {
  color: #409eff;
}

.base-input {
  flex: 1;
  width: 100%;
  border: none;
  outline: none;
  background: transparent;
  color: #606266;
  font-size: 14px;
  line-height: 1;
  padding: 0 12px;
}

.base-input--large {
  font-size: 16px;
}

.base-input--small {
  font-size: 12px;
}

.base-input::placeholder {
  color: #c0c4cc;
}

.base-input:disabled {
  color: #c0c4cc;
  cursor: not-allowed;
}

/* 辅助信息样式 */
.word-limit {
  text-align: right;
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}

.error-message {
  font-size: 12px;
  color: #f56c6c;
  margin-top: 4px;
  line-height: 1.4;
}

.help-text {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  line-height: 1.4;
}

/* 响应式 */
@media (max-width: 768px) {
  .base-input-container--large {
    height: 44px;
  }
  
  .base-input-container--default {
    height: 40px;
  }
  
  .base-input-container--small {
    height: 36px;
  }
  
  .base-input--large {
    font-size: 16px;
  }
  
  .base-input--default {
    font-size: 16px;
  }
  
  .base-input--small {
    font-size: 14px;
  }
}
</style>