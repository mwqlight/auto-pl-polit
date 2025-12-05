<template>
  <div class="base-table-wrapper">
    <!-- 表格头部 -->
    <div v-if="showHeader" class="table-header">
      <div class="header-left">
        <slot name="header-left">
          <h3 v-if="title" class="table-title">{{ title }}</h3>
        </slot>
      </div>
      <div class="header-right">
        <slot name="header-right">
          <div v-if="showSearch" class="table-search">
            <el-input
              v-model="searchValue"
              :placeholder="searchPlaceholder"
              size="small"
              clearable
              @input="handleSearch"
            >
              <template #prefix>
                <i class="el-icon-search"></i>
              </template>
            </el-input>
          </div>
          <el-button
            v-if="showRefresh"
            type="primary"
            size="small"
            :loading="loading"
            @click="handleRefresh"
          >
            <i class="el-icon-refresh"></i>
            刷新
          </el-button>
        </slot>
      </div>
    </div>

    <!-- 表格主体 -->
    <div class="table-container">
      <el-table
        :data="tableData"
        :border="border"
        :stripe="stripe"
        :size="size"
        :height="height"
        :max-height="maxHeight"
        :highlight-current-row="highlightCurrentRow"
        :row-class-name="rowClassName"
        :cell-class-name="cellClassName"
        @selection-change="handleSelectionChange"
        @current-change="handleCurrentChange"
        @row-click="handleRowClick"
        @sort-change="handleSortChange"
      >
        <!-- 选择列 -->
        <el-table-column
          v-if="showSelection"
          type="selection"
          width="55"
          align="center"
        />

        <!-- 序号列 -->
        <el-table-column
          v-if="showIndex"
          type="index"
          :label="indexLabel"
          width="80"
          align="center"
        />

        <!-- 数据列 -->
        <template v-for="column in columns" :key="column.prop">
          <el-table-column
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
            :min-width="column.minWidth"
            :align="column.align || 'left'"
            :sortable="column.sortable"
            :fixed="column.fixed"
          >
            <template #default="scope">
              <slot
                v-if="column.slot"
                :name="column.slot"
                :row="scope.row"
                :$index="scope.$index"
              />
              <template v-else>
                <span v-if="column.formatter">
                  {{ column.formatter(scope.row, scope.column, scope.$index) }}
                </span>
                <span v-else>
                  {{ scope.row[column.prop] }}
                </span>
              </template>
            </template>
          </el-table-column>
        </template>

        <!-- 操作列 -->
        <el-table-column
          v-if="showActions"
          :label="actionsLabel"
          :width="actionsWidth"
          :align="actionsAlign"
          :fixed="actionsFixed"
        >
          <template #default="scope">
            <slot name="actions" :row="scope.row" :$index="scope.$index">
              <el-button
                v-if="showEdit"
                type="primary"
                size="small"
                @click="handleEdit(scope.row, scope.$index)"
              >
                编辑
              </el-button>
              <el-button
                v-if="showDelete"
                type="danger"
                size="small"
                @click="handleDelete(scope.row, scope.$index)"
              >
                删除
              </el-button>
            </slot>
          </template>
        </el-table-column>

        <!-- 空数据 -->
        <template #empty>
          <div class="empty-content">
            <i class="el-icon-document" />
            <p>{{ emptyText }}</p>
          </div>
        </template>
      </el-table>
    </div>

    <!-- 表格底部 -->
    <div v-if="showPagination" class="table-footer">
      <div class="footer-left">
        <slot name="footer-left">
          <span v-if="showSelection" class="selection-info">
            已选择 {{ selection.length }} 项
          </span>
        </slot>
      </div>
      <div class="footer-right">
        <slot name="footer-right">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="pageSizes"
            :total="total"
            :layout="paginationLayout"
            :background="paginationBackground"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </slot>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'

interface TableColumn {
  prop: string
  label: string
  width?: number
  minWidth?: number
  align?: 'left' | 'center' | 'right'
  sortable?: boolean | 'custom'
  fixed?: boolean | 'left' | 'right'
  slot?: string
  formatter?: (row: any, column: any, index: number) => string
}

interface Props {
  data: any[]
  columns: TableColumn[]
  title?: string
  showHeader?: boolean
  showSearch?: boolean
  searchPlaceholder?: string
  showRefresh?: boolean
  showSelection?: boolean
  showIndex?: boolean
  indexLabel?: string
  showActions?: boolean
  actionsLabel?: string
  actionsWidth?: number
  actionsAlign?: 'left' | 'center' | 'right'
  actionsFixed?: boolean | 'left' | 'right'
  showEdit?: boolean
  showDelete?: boolean
  showPagination?: boolean
  paginationLayout?: string
  paginationBackground?: boolean
  pageSizes?: number[]
  total?: number
  border?: boolean
  stripe?: boolean
  size?: 'large' | 'default' | 'small'
  height?: string | number
  maxHeight?: string | number
  highlightCurrentRow?: boolean
  emptyText?: string
  loading?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  data: () => [],
  columns: () => [],
  title: '',
  showHeader: true,
  showSearch: true,
  searchPlaceholder: '请输入搜索内容',
  showRefresh: true,
  showSelection: false,
  showIndex: false,
  indexLabel: '序号',
  showActions: false,
  actionsLabel: '操作',
  actionsWidth: 200,
  actionsAlign: 'center',
  actionsFixed: false,
  showEdit: false,
  showDelete: false,
  showPagination: true,
  paginationLayout: 'total, sizes, prev, pager, next, jumper',
  paginationBackground: true,
  pageSizes: () => [10, 20, 50, 100],
  total: 0,
  border: true,
  stripe: true,
  size: 'default',
  height: undefined,
  maxHeight: undefined,
  highlightCurrentRow: false,
  emptyText: '暂无数据',
  loading: false
})

const emit = defineEmits<{
  search: [value: string]
  refresh: []
  selectionChange: [selection: any[]]
  currentChange: [currentRow: any, oldCurrentRow: any]
  rowClick: [row: any, column: any, event: Event]
  sortChange: [sortInfo: any]
  edit: [row: any, index: number]
  delete: [row: any, index: number]
  sizeChange: [size: number]
  currentPageChange: [page: number]
}>()

// 响应式数据
const searchValue = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const selection = ref<any[]>([])

// 计算属性
const tableData = computed(() => {
  if (!searchValue.value) {
    return props.data
  }
  
  return props.data.filter(item => {
    return Object.values(item).some(value => 
      String(value).toLowerCase().includes(searchValue.value.toLowerCase())
    )
  })
})

// 方法定义
const handleSearch = () => {
  emit('search', searchValue.value)
}

const handleRefresh = () => {
  emit('refresh')
}

const handleSelectionChange = (val: any[]) => {
  selection.value = val
  emit('selectionChange', val)
}

const handleCurrentChange = (currentRow: any, oldCurrentRow: any) => {
  emit('currentChange', currentRow, oldCurrentRow)
}

const handleRowClick = (row: any, column: any, event: Event) => {
  emit('rowClick', row, column, event)
}

const handleSortChange = (sortInfo: any) => {
  emit('sortChange', sortInfo)
}

const handleEdit = (row: any, index: number) => {
  emit('edit', row, index)
}

const handleDelete = (row: any, index: number) => {
  emit('delete', row, index)
}

const handleSizeChange = (size: number) => {
  pageSize.value = size
  emit('sizeChange', size)
}

const handleCurrentPageChange = (page: number) => {
  currentPage.value = page
  emit('currentPageChange', page)
}

const rowClassName = ({ row, rowIndex }: { row: any; rowIndex: number }) => {
  return rowIndex % 2 === 0 ? 'even-row' : 'odd-row'
}

const cellClassName = ({ row, column, rowIndex, columnIndex }: { 
  row: any; 
  column: any; 
  rowIndex: number; 
  columnIndex: number 
}) => {
  return ''
}

// 监听器
watch(() => props.data, () => {
  currentPage.value = 1
})

// 暴露方法
defineExpose({
  clearSelection: () => {
    selection.value = []
  },
  getSelection: () => selection.value,
  setCurrentRow: (row: any) => {
    // 需要实现设置当前行的方法
  }
})
</script>

<style scoped>
.base-table-wrapper {
  background: #ffffff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #ebeef5;
  background: #f8f9fa;
}

.header-left {
  display: flex;
  align-items: center;
}

.table-title {
  margin: 0;
  font-size: 18px;
  color: #303133;
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.table-search {
  width: 240px;
}

.table-container {
  padding: 0 20px;
}

.empty-content {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}

.empty-content i {
  font-size: 48px;
  margin-bottom: 16px;
  display: block;
}

.empty-content p {
  margin: 0;
  font-size: 14px;
}

.table-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-top: 1px solid #ebeef5;
  background: #f8f9fa;
}

.selection-info {
  font-size: 14px;
  color: #606266;
}

/* 表格行样式 */
:deep(.even-row) {
  background-color: #fafafa;
}

:deep(.odd-row) {
  background-color: #ffffff;
}

:deep(.el-table__header) {
  background-color: #f5f7fa;
}

:deep(.el-table__header th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 600;
}

:deep(.el-table__body tr:hover > td) {
  background-color: #f5f7fa !important;
}

/* 响应式 */
@media (max-width: 768px) {
  .table-header {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }
  
  .header-right {
    justify-content: space-between;
  }
  
  .table-search {
    width: 100%;
  }
  
  .table-container {
    padding: 0 10px;
  }
  
  .table-footer {
    flex-direction: column;
    gap: 12px;
    align-items: stretch;
  }
  
  .footer-left,
  .footer-right {
    text-align: center;
  }
  
  :deep(.el-pagination) {
    justify-content: center;
  }
}
</style>