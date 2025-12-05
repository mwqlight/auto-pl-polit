import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'

// 请求配置
const config: AxiosRequestConfig = {
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
}

// 创建axios实例
const service: AxiosInstance = axios.create(config)

// 请求拦截器
service.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // 添加token
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers = config.headers || {}
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    
    // 添加时间戳防止缓存
    if (config.method?.toLowerCase() === 'get') {
      config.params = {
        ...config.params,
        _t: Date.now()
      }
    }
    
    return config
  },
  (error) => {
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response
    
    // 统一处理响应格式
    if (data.code === 200) {
      return data.data
    } else {
      // 业务错误处理
      handleBusinessError(data.code, data.message)
      return Promise.reject(new Error(data.message || '请求失败'))
    }
  },
  (error) => {
    // 网络错误处理
    handleNetworkError(error)
    return Promise.reject(error)
  }
)

// 业务错误处理
function handleBusinessError(code: number, message: string) {
  switch (code) {
    case 401:
      // 未授权，跳转到登录页
      handleUnauthorized()
      break
    case 403:
      // 权限不足
      ElMessage.error('权限不足，无法访问该资源')
      break
    case 404:
      // 资源不存在
      ElMessage.error('请求的资源不存在')
      break
    case 500:
      // 服务器错误
      ElMessage.error('服务器内部错误，请稍后重试')
      break
    default:
      // 其他业务错误
      ElMessage.error(message || '操作失败')
  }
}

// 网络错误处理
function handleNetworkError(error: any) {
  if (error.response) {
    // 服务器响应错误
    const { status, data } = error.response
    
    switch (status) {
      case 400:
        ElMessage.error(data?.message || '请求参数错误')
        break
      case 401:
        handleUnauthorized()
        break
      case 403:
        ElMessage.error('权限不足')
        break
      case 404:
        ElMessage.error('请求的资源不存在')
        break
      case 500:
        ElMessage.error('服务器内部错误')
        break
      case 502:
        ElMessage.error('网关错误')
        break
      case 503:
        ElMessage.error('服务不可用')
        break
      case 504:
        ElMessage.error('网关超时')
        break
      default:
        ElMessage.error(`网络错误: ${status}`)
    }
  } else if (error.request) {
    // 请求未收到响应
    if (error.code === 'ECONNABORTED') {
      ElMessage.error('请求超时，请检查网络连接')
    } else {
      ElMessage.error('网络连接失败，请检查网络设置')
    }
  } else {
    // 其他错误
    ElMessage.error(error.message || '未知错误')
  }
}

// 未授权处理
function handleUnauthorized() {
  const userStore = useUserStore()
  
  ElMessageBox.confirm('登录已过期，请重新登录', '提示', {
    confirmButtonText: '重新登录',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    // 跳转到登录页
    window.location.href = '/login'
  }).catch(() => {
    // 用户取消操作
  })
}

// 通用请求方法
export const request = {
  // GET请求
  get<T = any>(url: string, params?: any): Promise<T> {
    return service.get(url, { params })
  },
  
  // POST请求
  post<T = any>(url: string, data?: any): Promise<T> {
    return service.post(url, data)
  },
  
  // PUT请求
  put<T = any>(url: string, data?: any): Promise<T> {
    return service.put(url, data)
  },
  
  // DELETE请求
  delete<T = any>(url: string, params?: any): Promise<T> {
    return service.delete(url, { params })
  },
  
  // PATCH请求
  patch<T = any>(url: string, data?: any): Promise<T> {
    return service.patch(url, data)
  },
  
  // 上传文件
  upload<T = any>(url: string, formData: FormData, onProgress?: (progress: number) => void): Promise<T> {
    return service.post(url, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      onUploadProgress: (progressEvent) => {
        if (progressEvent.total && onProgress) {
          const progress = Math.round((progressEvent.loaded * 100) / progressEvent.total)
          onProgress(progress)
        }
      }
    })
  },
  
  // 下载文件
  download(url: string, params?: any, filename?: string): Promise<void> {
    return service.get(url, {
      params,
      responseType: 'blob'
    }).then((response) => {
      const blob = new Blob([response])
      const downloadUrl = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = downloadUrl
      link.download = filename || 'download'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      window.URL.revokeObjectURL(downloadUrl)
    })
  }
}

// 导出axios实例
export default service