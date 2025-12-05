/**
 * 组件相关类型定义
 */

// 基础组件属性
export interface BaseComponentProps {
  class?: string
  style?: Record<string, string | number>
  id?: string
}

// 按钮组件属性
export interface ButtonProps extends BaseComponentProps {
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info' | 'text'
  size?: 'large' | 'default' | 'small'
  disabled?: boolean
  loading?: boolean
  round?: boolean
  circle?: boolean
  plain?: boolean
  icon?: string
  nativeType?: 'button' | 'submit' | 'reset'
  onClick?: (event: MouseEvent) => void
}

// 输入框组件属性
export interface InputProps extends BaseComponentProps {
  type?: 'text' | 'password' | 'number' | 'email' | 'tel' | 'url'
  modelValue?: string | number
  placeholder?: string
  disabled?: boolean
  readonly?: boolean
  clearable?: boolean
  showPassword?: boolean
  prefixIcon?: string
  suffixIcon?: string
  maxlength?: number
  minlength?: number
  showWordLimit?: boolean
  size?: 'large' | 'default' | 'small'
  onInput?: (value: string) => void
  onChange?: (value: string) => void
  onFocus?: (event: FocusEvent) => void
  onBlur?: (event: FocusEvent) => void
  onClear?: () => void
}

// 表格列定义
export interface TableColumn {
  prop: string
  label: string
  width?: string | number
  minWidth?: string | number
  fixed?: boolean | 'left' | 'right'
  sortable?: boolean | 'custom'
  sortMethod?: (a: any, b: any) => number
  formatter?: (row: any, column: TableColumn, cellValue: any, index: number) => any
  align?: 'left' | 'center' | 'right'
  headerAlign?: 'left' | 'center' | 'right'
  className?: string
  labelClassName?: string
  showOverflowTooltip?: boolean
  children?: TableColumn[]
}

// 表格组件属性
export interface TableProps extends BaseComponentProps {
  data: any[]
  columns: TableColumn[]
  height?: string | number
  maxHeight?: string | number
  stripe?: boolean
  border?: boolean
  size?: 'large' | 'default' | 'small'
  fit?: boolean
  showHeader?: boolean
  highlightCurrentRow?: boolean
  currentRowKey?: string | number
  rowClassName?: string | ((row: any, rowIndex: number) => string)
  rowStyle?: Record<string, string | number> | ((row: any, rowIndex: number) => Record<string, string | number>)
  cellClassName?: string | ((row: any, column: TableColumn, rowIndex: number, columnIndex: number) => string)
  cellStyle?: Record<string, string | number> | ((row: any, column: TableColumn, rowIndex: number, columnIndex: number) => Record<string, string | number>)
  headerRowClassName?: string | ((row: any) => string)
  headerRowStyle?: Record<string, string | number> | ((row: any) => Record<string, string | number>)
  headerCellClassName?: string | ((row: any, column: TableColumn) => string)
  headerCellStyle?: Record<string, string | number> | ((row: any, column: TableColumn) => Record<string, string | number>)
  emptyText?: string
  defaultSort?: { prop: string; order: 'ascending' | 'descending' }
  tooltipEffect?: 'dark' | 'light'
  showSummary?: boolean
  sumText?: string
  summaryMethod?: (data: any[]) => any[]
  spanMethod?: (data: { row: any; rowIndex: number; column: TableColumn; columnIndex: number }) => { rowspan: number; colspan: number } | void
  selectOnIndeterminate?: boolean
  indent?: number
  lazy?: boolean
  load?: (row: any, treeNode: any, resolve: (data: any[]) => void) => void
  treeProps?: { hasChildren?: string; children?: string }
  onSelect?: (selection: any[], row: any) => void
  onSelectAll?: (selection: any[]) => void
  onSelectionChange?: (selection: any[]) => void
  onCellMouseEnter?: (row: any, column: TableColumn, cell: any, event: MouseEvent) => void
  onCellMouseLeave?: (row: any, column: TableColumn, cell: any, event: MouseEvent) => void
  onCellClick?: (row: any, column: TableColumn, cell: any, event: MouseEvent) => void
  onCellDblclick?: (row: any, column: TableColumn, cell: any, event: MouseEvent) => void
  onRowClick?: (row: any, column: TableColumn, event: MouseEvent) => void
  onRowDblclick?: (row: any, column: TableColumn, event: MouseEvent) => void
  onRowContextmenu?: (row: any, column: TableColumn, event: MouseEvent) => void
  onHeaderClick?: (column: TableColumn, event: MouseEvent) => void
  onHeaderContextmenu?: (column: TableColumn, event: MouseEvent) => void
  onSortChange?: (data: { column: TableColumn; prop: string; order: string }) => void
  onFilterChange?: (filters: any) => void
  onCurrentChange?: (currentRow: any, oldCurrentRow: any) => void
  onHeaderDragend?: (newWidth: number, oldWidth: number, column: TableColumn, event: MouseEvent) => void
  onExpandChange?: (row: any, expandedRows: any[]) => void
}

// 分页组件属性
export interface PaginationProps extends BaseComponentProps {
  total: number
  pageSize: number
  currentPage: number
  pageSizes?: number[]
  layout?: string
  background?: boolean
  disabled?: boolean
  hideOnSinglePage?: boolean
  onSizeChange?: (size: number) => void
  onCurrentChange?: (page: number) => void
}

// 表单组件属性
export interface FormProps extends BaseComponentProps {
  model: Record<string, any>
  rules?: Record<string, any>
  labelPosition?: 'left' | 'right' | 'top'
  labelWidth?: string | number
  labelSuffix?: string
  inline?: boolean
  inlineMessage?: boolean
  statusIcon?: boolean
  showMessage?: boolean
  size?: 'large' | 'default' | 'small'
  disabled?: boolean
  validateOnRuleChange?: boolean
  hideRequiredAsterisk?: boolean
  scrollToError?: boolean
  onSubmit?: (event: Event) => void
  onValidate?: (valid: boolean, fields: any) => void
}

// 表单项目属性
export interface FormItemProps extends BaseComponentProps {
  prop?: string
  label?: string
  labelWidth?: string | number
  required?: boolean
  rules?: any[] | Record<string, any>
  error?: string
  showMessage?: boolean
  inlineMessage?: boolean
  size?: 'large' | 'default' | 'small'
  for?: string
}

// 选择器组件属性
export interface SelectProps extends BaseComponentProps {
  modelValue?: any
  multiple?: boolean
  disabled?: boolean
  valueKey?: string
  size?: 'large' | 'default' | 'small'
  clearable?: boolean
  collapseTags?: boolean
  multipleLimit?: number
  name?: string
  autocomplete?: string
  placeholder?: string
  filterable?: boolean
  allowCreate?: boolean
  filterMethod?: (query: string) => void
  remote?: boolean
  remoteMethod?: (query: string) => void
  loading?: boolean
  loadingText?: string
  noMatchText?: string
  noDataText?: string
  popperClass?: string
  reserveKeyword?: boolean
  defaultFirstOption?: boolean
  popperAppendToBody?: boolean
  automaticDropdown?: boolean
  onVisibleChange?: (visible: boolean) => void
  onRemoveTag?: (tag: any) => void
  onClear?: () => void
  onBlur?: (event: FocusEvent) => void
  onFocus?: (event: FocusEvent) => void
}

// 选择器选项属性
export interface OptionProps extends BaseComponentProps {
  value: any
  label?: string
  disabled?: boolean
}

// 对话框组件属性
export interface DialogProps extends BaseComponentProps {
  modelValue: boolean
  title?: string
  width?: string
  fullscreen?: boolean
  top?: string
  modal?: boolean
  appendToBody?: boolean
  lockScroll?: boolean
  customClass?: string
  openDelay?: number
  closeDelay?: number
  closeOnClickModal?: boolean
  closeOnPressEscape?: boolean
  showClose?: boolean
  beforeClose?: (done: () => void) => void
  center?: boolean
  destroyOnClose?: boolean
  onOpen?: () => void
  onOpened?: () => void
  onClose?: () => void
  onClosed?: () => void
}

// 消息提示组件属性
export interface MessageProps {
  type?: 'success' | 'warning' | 'info' | 'error'
  message: string
  duration?: number
  showClose?: boolean
  center?: boolean
  onClose?: () => void
  offset?: number
}

// 加载组件属性
export interface LoadingProps extends BaseComponentProps {
  loading: boolean
  text?: string
  spinner?: string
  background?: string
  customClass?: string
  fullscreen?: boolean
  lock?: boolean
  target?: string | HTMLElement
  body?: boolean
}

// 标签页组件属性
export interface TabsProps extends BaseComponentProps {
  modelValue: string
  type?: 'card' | 'border-card'
  closable?: boolean
  addable?: boolean
  editable?: boolean
  tabPosition?: 'top' | 'right' | 'bottom' | 'left'
  beforeLeave?: (activeName: string, oldActiveName: string) => boolean | Promise<boolean>
  stretch?: boolean
  onTabClick?: (tab: any, event: Event) => void
  onTabRemove?: (name: string) => void
  onTabAdd?: () => void
  onEdit?: (targetName: string, action: 'add' | 'remove') => void
}

// 标签页项目属性
export interface TabPaneProps extends BaseComponentProps {
  label: string
  name: string
  disabled?: boolean
  closable?: boolean
  lazy?: boolean
}

// 卡片组件属性
export interface CardProps extends BaseComponentProps {
  header?: string
  bodyStyle?: Record<string, string | number>
  shadow?: 'always' | 'hover' | 'never'
}

// 进度条组件属性
export interface ProgressProps extends BaseComponentProps {
  percentage: number
  type?: 'line' | 'circle' | 'dashboard'
  strokeWidth?: number
  textInside?: boolean
  status?: 'success' | 'exception' | 'warning'
  color?: string | ((percentage: number) => string) | Array<{ color: string; percentage: number }>
  width?: number
  showText?: boolean
  strokeLinecap?: 'butt' | 'round' | 'square'
  format?: (percentage: number) => string
}

// 步骤条组件属性
export interface StepsProps extends BaseComponentProps {
  space?: number | string
  direction?: 'vertical' | 'horizontal'
  active: number
  processStatus?: 'wait' | 'process' | 'finish' | 'error' | 'success'
  finishStatus?: 'wait' | 'process' | 'finish' | 'error' | 'success'
  alignCenter?: boolean
  simple?: boolean
}

// 步骤条项目属性
export interface StepProps extends BaseComponentProps {
  title: string
  description?: string
  icon?: string
  status?: 'wait' | 'process' | 'finish' | 'error' | 'success'
}

// 树形组件属性
export interface TreeProps extends BaseComponentProps {
  data: TreeNode[]
  emptyText?: string
  nodeKey?: string
  props?: {
    children?: string
    label?: string
    disabled?: string
    isLeaf?: string
  }
  renderAfterExpand?: boolean
  load?: (node: TreeNode, resolve: (data: TreeNode[]) => void) => void
  renderContent?: (h: any, { node: TreeNode, data: any, store: any }) => any
  highlightCurrent?: boolean
  defaultExpandAll?: boolean
  expandOnClickNode?: boolean
  checkOnClickNode?: boolean
  autoExpandParent?: boolean
  defaultExpandedKeys?: any[]
  showCheckbox?: boolean
  checkStrictly?: boolean
  defaultCheckedKeys?: any[]
  currentNodeKey?: any
  filterNodeMethod?: (value: any, data: any, node: TreeNode) => boolean
  accordion?: boolean
  indent?: number
  iconClass?: string
  lazy?: boolean
  draggable?: boolean
  allowDrag?: (node: TreeNode) => boolean
  allowDrop?: (draggingNode: TreeNode, dropNode: TreeNode, type: 'prev' | 'inner' | 'next') => boolean
  onNodeClick?: (data: any, node: TreeNode, component: any) => void
  onNodeContextmenu?: (event: Event, data: any, node: TreeNode, component: any) => void
  onNodeCollapse?: (data: any, node: TreeNode, component: any) => void
  onNodeExpand?: (data: any, node: TreeNode, component: any) => void
  onCheckChange?: (data: any, checked: boolean, indeterminate: boolean) => void
  onCheck?: (data: any, checkedInfo: { checkedNodes: any[]; checkedKeys: any[]; halfCheckedNodes: any[]; halfCheckedKeys: any[] }) => void
  onCurrentChange?: (data: any, node: TreeNode) => void
  onNodeDragStart?: (node: TreeNode, event: Event) => void
  onNodeDragEnter?: (draggingNode: TreeNode, dropNode: TreeNode, event: Event) => void
  onNodeDragLeave?: (draggingNode: TreeNode, dropNode: TreeNode, event: Event) => void
  onNodeDragOver?: (draggingNode: TreeNode, dropNode: TreeNode, event: Event) => void
  onNodeDragEnd?: (draggingNode: TreeNode, dropNode: TreeNode, event: Event) => void
  onNodeDrop?: (draggingNode: TreeNode, dropNode: TreeNode, dropType: 'before' | 'after' | 'inner', event: Event) => void
}

// 树节点类型
export interface TreeNode {
  id: string | number
  label: string
  children?: TreeNode[]
  disabled?: boolean
  isLeaf?: boolean
  data?: any
}

// 上传组件属性
export interface UploadProps extends BaseComponentProps {
  action: string
  headers?: Record<string, string>
  data?: Record<string, any>
  multiple?: boolean
  name?: string
  withCredentials?: boolean
  showFileList?: boolean
  drag?: boolean
  accept?: string
  onPreview?: (file: UploadFile) => void
  onRemove?: (file: UploadFile, fileList: UploadFile[]) => void
  onSuccess?: (response: any, file: UploadFile, fileList: UploadFile[]) => void
  onError?: (error: Error, file: UploadFile, fileList: UploadFile[]) => void
  onProgress?: (event: ProgressEvent, file: UploadFile, fileList: UploadFile[]) => void
  onChange?: (file: UploadFile, fileList: UploadFile[]) => void
  beforeUpload?: (file: UploadFile) => boolean | Promise<any>
  beforeRemove?: (file: UploadFile, fileList: UploadFile[]) => boolean | Promise<any>
  fileList?: UploadFile[]
  autoUpload?: boolean
  listType?: 'text' | 'picture' | 'picture-card'
  httpRequest?: (options: any) => void
  disabled?: boolean
  limit?: number
  onExceed?: (files: File[], fileList: UploadFile[]) => void
}

// 上传文件类型
export interface UploadFile {
  name: string
  percentage?: number
  status: 'ready' | 'uploading' | 'success' | 'fail'
  size?: number
  response?: any
  uid: number
  url?: string
}

// 图标组件属性
export interface IconProps extends BaseComponentProps {
  name: string
  size?: string | number
  color?: string
  onClick?: (event: MouseEvent) => void
}

// 面包屑组件属性
export interface BreadcrumbProps extends BaseComponentProps {
  separator?: string
  separatorClass?: string
}

// 面包屑项目属性
export interface BreadcrumbItemProps extends BaseComponentProps {
  to?: string | Record<string, any>
  replace?: boolean
}

// 菜单组件属性
export interface MenuProps extends BaseComponentProps {
  mode?: 'horizontal' | 'vertical'
  collapse?: boolean
  backgroundColor?: string
  textColor?: string
  activeTextColor?: string
  defaultActive?: string
  defaultOpeneds?: string[]
  uniqueOpened?: boolean
  menuTrigger?: 'hover' | 'click'
  router?: boolean
  collapseTransition?: boolean
  onSelect?: (index: string, indexPath: string[]) => void
  onOpen?: (index: string, indexPath: string[]) => void
  onClose?: (index: string, indexPath: string[]) => void
}

// 菜单项目属性
export interface MenuItemProps extends BaseComponentProps {
  index: string
  route?: string | Record<string, any>
  disabled?: boolean
}

// 子菜单属性
export interface SubMenuProps extends BaseComponentProps {
  index: string
  popperClass?: string
  showTimeout?: number
  hideTimeout?: number
  disabled?: boolean
  popperAppendToBody?: boolean
}

// 工具提示组件属性
export interface TooltipProps extends BaseComponentProps {
  effect?: 'dark' | 'light'
  content?: string
  placement?: 'top' | 'top-start' | 'top-end' | 'bottom' | 'bottom-start' | 'bottom-end' | 'left' | 'left-start' | 'left-end' | 'right' | 'right-start' | 'right-end'
  value?: boolean
  disabled?: boolean
  offset?: number
  transition?: string
  visibleArrow?: boolean
  popperOptions?: Record<string, any>
  openDelay?: number
  manual?: boolean
  popperClass?: string
  enterable?: boolean
  hideAfter?: number
  tabindex?: number
}

// 弹出框组件属性
export interface PopoverProps extends BaseComponentProps {
  trigger?: 'click' | 'focus' | 'hover' | 'manual'
  title?: string
  content?: string
  width?: string | number
  placement?: 'top' | 'top-start' | 'top-end' | 'bottom' | 'bottom-start' | 'bottom-end' | 'left' | 'left-start' | 'left-end' | 'right' | 'right-start' | 'right-end'
  disabled?: boolean
  value?: boolean
  offset?: number
  transition?: string
  visibleArrow?: boolean
  popperOptions?: Record<string, any>
  popperClass?: string
  openDelay?: number
  closeDelay?: number
  tabindex?: number
}

// 抽屉组件属性
export interface DrawerProps extends BaseComponentProps {
  modelValue: boolean
  title?: string
  direction?: 'rtl' | 'ltr' | 'ttb' | 'btt'
  size?: string | number
  withHeader?: boolean
  modal?: boolean
  appendToBody?: boolean
  lockScroll?: boolean
  customClass?: string
  closeOnPressEscape?: boolean
  showClose?: boolean
  beforeClose?: (done: () => void) => void
  center?: boolean
  destroyOnClose?: boolean
  onOpen?: () => void
  onOpened?: () => void
  onClose?: () => void
  onClosed?: () => void
}

// 时间线组件属性
export interface TimelineProps extends BaseComponentProps {
  reverse?: boolean
}

// 时间线项目属性
export interface TimelineItemProps extends BaseComponentProps {
  timestamp?: string
  hideTimestamp?: boolean
  placement?: 'top' | 'bottom'
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info'
  color?: string
  size?: 'normal' | 'large'
  icon?: string
}

// 徽章组件属性
export interface BadgeProps extends BaseComponentProps {
  value?: string | number
  max?: number
  isDot?: boolean
  hidden?: boolean
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info'
}

// 头像组件属性
export interface AvatarProps extends BaseComponentProps {
  icon?: string
  size?: 'large' | 'default' | 'small' | number
  shape?: 'circle' | 'square'
  src?: string
  srcSet?: string
  alt?: string
  fit?: 'fill' | 'contain' | 'cover' | 'none' | 'scale-down'
}

// 骨架屏组件属性
export interface SkeletonProps extends BaseComponentProps {
  animated?: boolean
  count?: number
  rows?: number
  loading?: boolean
  throttle?: number
}

// 骨架屏项目属性
export interface SkeletonItemProps extends BaseComponentProps {
  variant?: 'text' | 'circle' | 'rect'
}

// 空状态组件属性
export interface EmptyProps extends BaseComponentProps {
  image?: string
  imageSize?: number
  description?: string
}

// 结果组件属性
export interface ResultProps extends BaseComponentProps {
  icon?: string
  title?: string
  subTitle?: string
  extra?: any
}

// 描述列表组件属性
export interface DescriptionsProps extends BaseComponentProps {
  border?: boolean
  column?: number
  direction?: 'vertical' | 'horizontal'
  size?: 'large' | 'default' | 'small'
  title?: string
  extra?: any
  colon?: boolean
}

// 描述列表项目属性
export interface DescriptionsItemProps extends BaseComponentProps {
  label?: string
  span?: number
  labelClassName?: string
  contentClassName?: string
}

// 统计数值组件属性
export interface StatisticProps extends BaseComponentProps {
  title?: string
  value?: string | number
  precision?: number
  prefix?: string
  suffix?: string
  valueStyle?: Record<string, string | number>
}

// 评论组件属性
export interface CommentProps extends BaseComponentProps {
  author?: string
  avatar?: string
  content?: string
  datetime?: string
}

// 锚点组件属性
export interface AnchorProps extends BaseComponentProps {
  affix?: boolean
  bounds?: number
  offsetTop?: number
  targetOffset?: number
  container?: string | HTMLElement
  showInkInFixed?: boolean
}

// 锚点链接属性
export interface AnchorLinkProps extends BaseComponentProps {
  href: string
  title: string
  target?: string
}

// 回到顶部组件属性
export interface BacktopProps extends BaseComponentProps {
  target?: string
  visibilityHeight?: number
  right?: number
  bottom?: number
}

// 无限滚动组件属性
export interface InfiniteScrollProps {
  distance?: number
  immediate?: boolean
  disabled?: boolean
  delay?: number
}

// 图片组件属性
export interface ImageProps extends BaseComponentProps {
  src?: string
  fit?: 'fill' | 'contain' | 'cover' | 'none' | 'scale-down'
  alt?: string
  referrerPolicy?: string
  lazy?: boolean
  scrollContainer?: string | HTMLElement
  previewSrcList?: string[]
  zIndex?: number
  initialIndex?: number
  hideOnClickModal?: boolean
  previewTeleported?: boolean
}

// 日历组件属性
export interface CalendarProps extends BaseComponentProps {
  value?: Date | string | number
  range?: [Date | string | number, Date | string | number]
  firstDayOfWeek?: number
}

// 颜色选择器组件属性
export interface ColorPickerProps extends BaseComponentProps {
  modelValue?: string
  disabled?: boolean
  size?: 'large' | 'default' | 'small'
  showAlpha?: boolean
  colorFormat?: 'hsl' | 'hsv' | 'hex' | 'rgb'
  popperClass?: string
  predefine?: string[]
}

// 级联选择器组件属性
export interface CascaderProps extends BaseComponentProps {
  modelValue?: any
  options: CascaderOption[]
  props?: {
    expandTrigger?: 'click' | 'hover'
    multiple?: boolean
    checkStrictly?: boolean
    emitPath?: boolean
    lazy?: boolean
    lazyLoad?: (node: CascaderOption, resolve: (data: CascaderOption[]) => void) => void
    value?: string
    label?: string
    children?: string
    disabled?: string
    leaf?: string
  }
  size?: 'large' | 'default' | 'small'
  placeholder?: string
  disabled?: boolean
  clearable?: boolean
  showAllLevels?: boolean
  collapseTags?: boolean
  separator?: string
  filterable?: boolean
  filterMethod?: (node: CascaderOption, keyword: string) => boolean
  debounce?: number
  beforeFilter?: (value: string) => boolean | Promise<boolean>
  popperClass?: string
}

// 级联选择器选项类型
export interface CascaderOption {
  value: any
  label: string
  children?: CascaderOption[]
  disabled?: boolean
  leaf?: boolean
}

// 穿梭框组件属性
export interface TransferProps extends BaseComponentProps {
  modelValue: any[]
  data: TransferData[]
  filterable?: boolean
  filterPlaceholder?: string
  filterMethod?: (query: string, item: TransferData) => boolean
  targetOrder?: 'original' | 'push' | 'unshift'
  titles?: [string, string]
  buttonTexts?: [string, string]
  renderContent?: (h: any, option: TransferData) => any
  format?: {
    noChecked?: string
    hasChecked?: string
  }
  props?: {
    key?: string
    label?: string
    disabled?: string
  }
  leftDefaultChecked?: any[]
  rightDefaultChecked?: any[]
}

// 穿梭框数据类型
export interface TransferData {
  key: any
  label: string
  disabled?: boolean
}

// 虚拟滚动组件属性
export interface VirtualScrollProps extends BaseComponentProps {
  data: any[]
  itemSize: number
  height: number
  buffer?: number
}

// 水印组件属性
export interface WatermarkProps extends BaseComponentProps {
  content?: string | string[]
  fontSize?: number
  fontColor?: string
  gap?: [number, number]
  offset?: [number, number]
  rotate?: number
  zIndex?: number
}

// 分割线组件属性
export interface DividerProps extends BaseComponentProps {
  direction?: 'horizontal' | 'vertical'
  contentPosition?: 'left' | 'center' | 'right'
}

// 空间组件属性
export interface SpaceProps extends BaseComponentProps {
  direction?: 'horizontal' | 'vertical'
  alignment?: 'start' | 'end' | 'center' | 'baseline'
  size?: 'small' | 'medium' | 'large' | number | [number, number]
  wrap?: boolean
  fill?: boolean
  fillRatio?: number
}

// 配置提供者属性
export interface ConfigProviderProps extends BaseComponentProps {
  locale?: any
  size?: 'large' | 'default' | 'small'
  button?: {
    autoInsertSpace?: boolean
  }
  message?: {
    max?: number
  }
  zIndex?: number
}

// 组件插槽类型
export interface ComponentSlots {
  default?: () => any
  [key: string]: (() => any) | undefined
}

// 组件事件类型
export interface ComponentEvents {
  [key: string]: (...args: any[]) => void
}

// 组件暴露的方法
export interface ComponentExposed {
  [key: string]: (...args: any[]) => any
}