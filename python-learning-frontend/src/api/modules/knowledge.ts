import request from '@/utils/request'
import type { 
  ApiResponse, 
  KnowledgeBaseItem, 
  SearchRequest, 
  SearchResponse
} from '@/types/api'

/**
 * 知识库相关API
 */
export const knowledgeApi = {
  /**
   * 搜索知识库
   */
  searchKnowledge: (params: SearchRequest): Promise<ApiResponse<SearchResponse<KnowledgeBaseItem>>> => {
    return request.get('/v1/knowledge/search', {
      params: params
    })
  },

  /**
   * 获取知识库条目详情
   */
  getKnowledgeItem: (id: string): Promise<ApiResponse<KnowledgeBaseItem>> => {
    return request.get(`/v1/knowledge/items/${id}`)
  },

  /**
   * 获取知识库分类列表
   */
  getKnowledgeCategories: (): Promise<ApiResponse<string[]>> => {
    return request.get('/v1/knowledge/categories')
  },

  /**
   * 获取分类下的知识库条目
   */
  getKnowledgeItemsByCategory: (category: string): Promise<ApiResponse<KnowledgeBaseItem[]>> => {
    return request.get(`/v1/knowledge/categories/${category}/items`)
  },

  /**
   * 收藏知识库条目
   */
  favoriteKnowledgeItem: (id: string): Promise<ApiResponse<void>> => {
    return request.post(`/v1/knowledge/items/${id}/favorite`)
  },

  /**
   * 取消收藏知识库条目
   */
  unfavoriteKnowledgeItem: (id: string): Promise<ApiResponse<void>> => {
    return request.delete(`/v1/knowledge/items/${id}/favorite`)
  },

  /**
   * 获取用户收藏的知识库条目
   */
  getFavoriteKnowledgeItems: (): Promise<ApiResponse<KnowledgeBaseItem[]>> => {
    return request.get('/v1/knowledge/favorites')
  },

  /**
   * 记录知识库条目访问
   */
  recordKnowledgeAccess: (id: string): Promise<ApiResponse<void>> => {
    return request.post(`/v1/knowledge/items/${id}/access`)
  },

  /**
   * 获取知识库热门条目
   */
  getHotKnowledgeItems: (limit: number = 10): Promise<ApiResponse<KnowledgeBaseItem[]>> => {
    return request.get('/v1/knowledge/hot', {
      params: { limit }
    })
  },

  /**
   * 获取知识库推荐条目
   */
  getRecommendedKnowledgeItems: (limit: number = 10): Promise<ApiResponse<KnowledgeBaseItem[]>> => {
    return request.get('/v1/knowledge/recommended', {
      params: { limit }
    })
  }
}
