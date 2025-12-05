/**
 * 验证工具函数
 * 提供常用的表单验证和数据处理功能
 */

/**
 * 验证邮箱格式
 */
export function validateEmail(email: string): boolean {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  return emailRegex.test(email)
}

/**
 * 验证手机号格式
 */
export function validatePhone(phone: string): boolean {
  const phoneRegex = /^1[3-9]\d{9}$/
  return phoneRegex.test(phone)
}

/**
 * 验证身份证号格式
 */
export function validateIdCard(idCard: string): boolean {
  const idCardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  return idCardRegex.test(idCard)
}

/**
 * 验证密码强度
 * @param password 密码
 * @param minLength 最小长度
 * @param requireUppercase 是否需要大写字母
 * @param requireLowercase 是否需要小写字母
 * @param requireNumber 是否需要数字
 * @param requireSpecialChar 是否需要特殊字符
 */
export function validatePasswordStrength(
  password: string,
  minLength: number = 6,
  requireUppercase: boolean = true,
  requireLowercase: boolean = true,
  requireNumber: boolean = true,
  requireSpecialChar: boolean = false
): {
  isValid: boolean
  score: number
  suggestions: string[]
} {
  const suggestions: string[] = []
  let score = 0

  // 长度检查
  if (password.length >= minLength) {
    score += 1
  } else {
    suggestions.push(`密码长度至少为${minLength}位`)
  }

  // 大写字母检查
  if (/[A-Z]/.test(password)) {
    score += 1
  } else if (requireUppercase) {
    suggestions.push('密码应包含至少一个大写字母')
  }

  // 小写字母检查
  if (/[a-z]/.test(password)) {
    score += 1
  } else if (requireLowercase) {
    suggestions.push('密码应包含至少一个小写字母')
  }

  // 数字检查
  if (/\d/.test(password)) {
    score += 1
  } else if (requireNumber) {
    suggestions.push('密码应包含至少一个数字')
  }

  // 特殊字符检查
  if (/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)) {
    score += 1
  } else if (requireSpecialChar) {
    suggestions.push('密码应包含至少一个特殊字符')
  }

  const isValid = score >= 4 && password.length >= minLength

  return {
    isValid,
    score,
    suggestions
  }
}

/**
 * 验证用户名格式
 */
export function validateUsername(username: string): boolean {
  const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/
  return usernameRegex.test(username)
}

/**
 * 验证URL格式
 */
export function validateUrl(url: string): boolean {
  try {
    new URL(url)
    return true
  } catch {
    return false
  }
}

/**
 * 验证IP地址格式
 */
export function validateIpAddress(ip: string): boolean {
  const ipRegex = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
  return ipRegex.test(ip)
}

/**
 * 验证日期格式
 */
export function validateDate(dateStr: string, format: string = 'YYYY-MM-DD'): boolean {
  const date = new Date(dateStr)
  return !isNaN(date.getTime())
}

/**
 * 验证数字范围
 */
export function validateNumberRange(
  value: number,
  min?: number,
  max?: number
): boolean {
  if (min !== undefined && value < min) return false
  if (max !== undefined && value > max) return false
  return true
}

/**
 * 验证文件类型
 */
export function validateFileType(
  file: File,
  allowedTypes: string[]
): boolean {
  const fileType = file.type
  return allowedTypes.includes(fileType)
}

/**
 * 验证文件大小
 */
export function validateFileSize(
  file: File,
  maxSizeInMB: number
): boolean {
  const maxSize = maxSizeInMB * 1024 * 1024
  return file.size <= maxSize
}

/**
 * 验证必填字段
 */
export function validateRequired(value: any): boolean {
  if (value === null || value === undefined) return false
  if (typeof value === 'string') return value.trim().length > 0
  if (Array.isArray(value)) return value.length > 0
  return true
}

/**
 * 验证数组内容
 */
export function validateArray(
  arr: any[],
  validator?: (item: any) => boolean
): boolean {
  if (!Array.isArray(arr)) return false
  if (validator) {
    return arr.every(validator)
  }
  return true
}

/**
 * 验证对象结构
 */
export function validateObjectStructure(
  obj: any,
  structure: Record<string, (value: any) => boolean>
): boolean {
  if (typeof obj !== 'object' || obj === null) return false
  
  for (const [key, validator] of Object.entries(structure)) {
    if (!(key in obj) || !validator(obj[key])) {
      return false
    }
  }
  
  return true
}

/**
 * 深度验证对象
 */
export function deepValidate(
  obj: any,
  rules: Record<string, (value: any) => boolean>
): {
  isValid: boolean
  errors: Record<string, string>
} {
  const errors: Record<string, string> = {}
  
  for (const [path, validator] of Object.entries(rules)) {
    const value = getNestedValue(obj, path)
    if (!validator(value)) {
      errors[path] = `字段 ${path} 验证失败`
    }
  }
  
  return {
    isValid: Object.keys(errors).length === 0,
    errors
  }
}

/**
 * 获取嵌套对象的值
 */
function getNestedValue(obj: any, path: string): any {
  return path.split('.').reduce((current, key) => {
    return current?.[key]
  }, obj)
}

/**
 * 创建验证器组合
 */
export function createValidator(...validators: Array<(value: any) => boolean>) {
  return (value: any) => {
    return validators.every(validator => validator(value))
  }
}

/**
 * 异步验证器
 */
export async function asyncValidate(
  value: any,
  validator: (value: any) => Promise<boolean>
): Promise<boolean> {
  try {
    return await validator(value)
  } catch {
    return false
  }
}

/**
 * 防抖验证
 */
export function debounceValidate(
  validator: (value: any) => boolean,
  delay: number = 300
): (value: any) => Promise<boolean> {
  let timeoutId: NodeJS.Timeout
  
  return (value: any) => {
    return new Promise((resolve) => {
      clearTimeout(timeoutId)
      timeoutId = setTimeout(() => {
        resolve(validator(value))
      }, delay)
    })
  }
}

/**
 * 批量验证
 */
export function batchValidate(
  values: Record<string, any>,
  rules: Record<string, (value: any) => boolean>
): {
  isValid: boolean
  results: Record<string, boolean>
} {
  const results: Record<string, boolean> = {}
  let isValid = true
  
  for (const [key, validator] of Object.entries(rules)) {
    const result = validator(values[key])
    results[key] = result
    if (!result) {
      isValid = false
    }
  }
  
  return { isValid, results }
}

/**
 * 验证码验证
 */
export function validateCaptcha(captcha: string, length: number = 4): boolean {
  const captchaRegex = new RegExp(`^[A-Za-z0-9]{${length}}$`)
  return captchaRegex.test(captcha)
}

/**
 * 银行卡号验证（Luhn算法）
 */
export function validateBankCard(cardNumber: string): boolean {
  // 移除空格和连字符
  const cleanNumber = cardNumber.replace(/[\s-]/g, '')
  
  // 检查是否为数字
  if (!/^\d+$/.test(cleanNumber)) return false
  
  // Luhn算法验证
  let sum = 0
  let isEven = false
  
  for (let i = cleanNumber.length - 1; i >= 0; i--) {
    let digit = parseInt(cleanNumber.charAt(i), 10)
    
    if (isEven) {
      digit *= 2
      if (digit > 9) {
        digit -= 9
      }
    }
    
    sum += digit
    isEven = !isEven
  }
  
  return sum % 10 === 0
}

/**
 * 统一社会信用代码验证
 */
export function validateUnifiedSocialCreditCode(code: string): boolean {
  const pattern = /^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/
  return pattern.test(code)
}

/**
 * 验证工具类
 */
export class Validator {
  private rules: Array<{ 
    validator: (value: any) => boolean 
    message: string 
  }> = []

  addRule(validator: (value: any) => boolean, message: string): this {
    this.rules.push({ validator, message })
    return this
  }

  validate(value: any): { isValid: boolean; errors: string[] } {
    const errors: string[] = []
    
    for (const rule of this.rules) {
      if (!rule.validator(value)) {
        errors.push(rule.message)
      }
    }
    
    return {
      isValid: errors.length === 0,
      errors
    }
  }

  static create(): Validator {
    return new Validator()
  }
}

// 默认导出常用验证器
export default {
  email: validateEmail,
  phone: validatePhone,
  idCard: validateIdCard,
  password: validatePasswordStrength,
  username: validateUsername,
  url: validateUrl,
  ip: validateIpAddress,
  date: validateDate,
  numberRange: validateNumberRange,
  required: validateRequired,
  captcha: validateCaptcha,
  bankCard: validateBankCard,
  socialCreditCode: validateUnifiedSocialCreditCode
}