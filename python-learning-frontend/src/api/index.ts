import { userApi } from './modules/user'
import { learningApi } from './modules/learning'
import { converterApi } from './modules/converter'
import { knowledgeApi } from './modules/knowledge'

/**
 * API统一导出
 */
export const api = {
  user: userApi,
  learning: learningApi,
  converter: converterApi,
  knowledge: knowledgeApi
}

export default api