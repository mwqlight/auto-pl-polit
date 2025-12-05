import { userApi } from './modules/user'
import { learningApi } from './modules/learning'
import { converterApi } from './modules/converter'

/**
 * API统一导出
 */
export const api = {
  user: userApi,
  learning: learningApi,
  converter: converterApi
}

export default api