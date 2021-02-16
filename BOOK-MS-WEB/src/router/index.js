import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '@/layout'

Vue.use(Router)

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  // { path: '/404', component: () => import('@/views/404'),
  { path: '', component: Layout, children: [{ path: '/404', component: () => import('@/views/404'), hidden: true }] },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'dashboard', component: () => import('@/views/dashboard/index'), meta: { title: 'dashboard', icon: 'home' }}
    ]
  },
  {
    path: '/tableImport',
    component: Layout,
    redirect: '/tableImport/BookStore',
    name: 'TableImport',
    meta: { title: '填报导入', icon: 'table' },
    children: [
      { path: 'BookStore', name: 'BookStore', component: () => import('@/views/table/BookStore'), meta: { title: '书籍库管理', icon: 'BookStore' }},
      { path: 'StudentInfo', name: 'StudentInfo', component: () => import('@/views/table/StudentInfo'), meta: { title: '学生信息管理', icon: 'StudentInfo' }},
      { path: 'CurriculumPlan', name: 'CurriculumPlan', component: () => import('@/views/table/CurriculumPlan'), meta: { title: '开课计划管理', icon: 'CurriculumPlan' }}
    ]
  },
  {
    // PA : 填报表格
    path: '/PA',
    component: Layout,
    redirect: '/PA/BookTotal',
    meta: { title: '填报管理', icon: 'table' },
    children: [
      { path: 'BookTotal', name: 'BookTotal', component: () => import('@/views/pa/BookTotal'), meta: { title: '填报总表管理', icon: 'BookTotal' }},
      { path: 'BookTotalNull', name: 'BookTotalNull', component: () => import('@/views/pa/BookTotalNull'), meta: { title: '开启填报', icon: 'BookTotal' }},
      { path: 'TimeChange', name: 'TimeChange', component: () => import('@/views/pa/TimeChange'), meta: { title: '填报时间修改' }, hidden: true }
    ]
  },
  {
    path: '/selectorManage',
    component: Layout,
    redirect: '/selectorManage/XqidManage',
    meta: { title: '公共资源', icon: 'ziyuan' },
    children: [
      { path: 'XqidManage', name: 'XqidManage', component: () => import('@/views/common/XqidManage'), meta: { title: '学期管理', icon: 'XqidManage' }},
      { path: 'XqidManage11', name: 'XqidManage11', component: () => import('@/views/common/XqidManage'), meta: { title: '合作出版社', icon: 'XqidManage' }},
      { path: 'SelectorManage', name: 'SelectorManage', component: () => import('@/views/common/SelectorManage'), meta: { title: '选择列表', icon: 'SelectorManage' }}
    ]
  },
  {
    path: '/fill',
    component: Layout,
    redirect: '/fill/tableFill',
    meta: { title: '填报模块', icon: 'form' },
    children: [
      { path: 'tableFill', name: 'tableFill', component: () => import('@/views/fill_in/tableFill'), meta: { title: '教材填报列表', icon: 'form' }},
      { path: 'formFill/:uuid', name: 'formFill', component: () => import('@/views/fill_in/formFill'), meta: { title: '教材填报' }, hidden: true },
      { path: 'StatusFail', name: 'StatusFail', component: () => import('@/views/fill_in/StatusFail'), meta: { title: '失败！' }, hidden: true }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
