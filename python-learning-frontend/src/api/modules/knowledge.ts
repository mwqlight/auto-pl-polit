import request from '@/utils/request'
import type {
  ApiResponse,
  KnowledgeBaseArticle,
  KnowledgeBaseCategory,
  SearchRequest,
  SearchResponse
} from '@/types/api'

/**
 * 知识库相关API
 */
export const knowledgeApi = {
  /**
   * 获取所有知识库文章
   */
  getAllArticles: (): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get('/api/v1/knowledge/articles')
  },

  /**
   * 根据ID获取知识库文章
   */
  getArticleById: (id: number): Promise<ApiResponse<KnowledgeBaseArticle>> => {
    return request.get(`/api/v1/knowledge/articles/${id}`)
  },

  /**
   * 根据分类获取知识库文章
   */
  getArticlesByCategory: (categoryId: number): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get(`/api/v1/knowledge/categories/${categoryId}/articles`)
  },

  /**
   * 搜索知识库文章
   */
  searchArticles: (keyword: string): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get('/api/v1/knowledge/articles/search', { params: { keyword } })
  },

  /**
   * 获取所有知识库分类
   */
  getAllCategories: (): Promise<ApiResponse<KnowledgeBaseCategory[]>> => {
    return request.get('/api/v1/knowledge/categories')
  },

  /**
   * 根据ID获取知识库分类
   */
  getCategoryById: (id: number): Promise<ApiResponse<KnowledgeBaseCategory>> => {
    return request.get(`/api/v1/knowledge/categories/${id}`)
  },

  /**
   * 获取热门知识库文章
   */
  getPopularArticles: (limit: number = 10): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get('/api/v1/knowledge/articles/popular', { params: { limit } })
  },

  /**
   * 获取最新知识库文章
   */
  getLatestArticles: (limit: number = 10): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get('/api/v1/knowledge/articles/latest', { params: { limit } })
  },

  /**
   * 搜索知识库
   */
  searchKnowledgeBase: (data: SearchRequest): Promise<ApiResponse<SearchResponse>> => {
    return request.post('/api/v1/knowledge/search', data)
  },

  /**
   * 记录文章浏览量
   */
  recordArticleView: (articleId: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/knowledge/articles/${articleId}/view`)
  },

  /**
   * 点赞文章
   */
  likeArticle: (articleId: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/knowledge/articles/${articleId}/like`)
  },

  /**
   * 取消点赞文章
   */
  unlikeArticle: (articleId: number): Promise<ApiResponse<void>> => {
    return request.delete(`/api/v1/knowledge/articles/${articleId}/like`)
  },

  /**
   * 收藏文章
   */
  favoriteArticle: (articleId: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/knowledge/articles/${articleId}/favorite`)
  },

  /**
   * 取消收藏文章
   */
  unfavoriteArticle: (articleId: number): Promise<ApiResponse<void>> => {
    return request.delete(`/api/v1/knowledge/articles/${articleId}/favorite`)
  },

  /**
   * 获取用户收藏的文章
   */
  getFavoriteArticles: (userId: number = 1): Promise<ApiResponse<KnowledgeBaseArticle[]>> => {
    return request.get('/api/v1/knowledge/articles/favorite', { params: { userId } })
  },

  /**
   * 获取文章评论
   */
  getArticleComments: (articleId: number): Promise<ApiResponse<any[]>> => {
    return request.get(`/api/v1/knowledge/articles/${articleId}/comments`)
  },

  /**
   * 添加文章评论
   */
  addArticleComment: (articleId: number, content: string): Promise<ApiResponse<any>> => {
    return request.post(`/api/v1/knowledge/articles/${articleId}/comments`, { content })
  },

  /**
   * 删除文章评论
   */
  deleteArticleComment: (commentId: number): Promise<ApiResponse<void>> => {
    return request.delete(`/api/v1/knowledge/comments/${commentId}`)
  }
}
