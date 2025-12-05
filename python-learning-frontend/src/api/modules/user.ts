import request from '@/utils/request'
import type { 
  LoginRequest, 
  RegisterRequest, 
  UserInfo, 
  UpdateUserRequest,
  ChangePasswordRequest,
  ApiResponse
} from '@/types/api'

/**
 * 用户认证相关API
 */
export const userApi = {
  /**
   * 用户登录
   */
  login: (data: LoginRequest): Promise<ApiResponse<{ token: string; user: UserInfo }>> => {
    return request.post('/v1/auth/login', data)
  },

  /**
   * 用户注册
   */
  register: (data: RegisterRequest): Promise<ApiResponse<UserInfo>> => {
    return request.post('/v1/auth/register', data)
  },

  /**
   * 刷新Token
   */
  refreshToken: (): Promise<ApiResponse<{ token: string }>> => {
    return request.post('/v1/auth/refresh')
  },

  /**
   * 用户登出
   */
  logout: (): Promise<ApiResponse<void>> => {
    return request.post('/v1/auth/logout')
  },

  /**
   * 获取当前用户信息
   */
  getCurrentUser: (): Promise<ApiResponse<UserInfo>> => {
    return request.get('/v1/user/profile')
  },

  /**
   * 更新用户信息
   */
  updateUser: (data: UpdateUserRequest): Promise<ApiResponse<UserInfo>> => {
    return request.put('/v1/user/profile', data)
  },

  /**
   * 修改密码
   */
  changePassword: (data: ChangePasswordRequest): Promise<ApiResponse<void>> => {
    return request.put('/v1/user/password', data)
  },

  /**
   * 上传头像
   */
  uploadAvatar: (file: File): Promise<ApiResponse<{ avatarUrl: string }>> => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/v1/user/avatar', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}