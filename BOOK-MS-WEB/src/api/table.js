import request from '@/utils/request'
import store from '@/store'

const xqid = store.getters.activeXqid

// 导入表格管理的api
// ==================== 书籍库管理 ==============================

// 获取书籍库书籍列表
export function getBookStoreList(pageStart, pageSize, search) {
  const url = search === ''
    ? `/admin/BookStore/all/${xqid}/${pageStart}/${pageSize}`
    : `/admin/BookStore/findByBookNameOrPym/${xqid}/${search}/${pageStart}/${pageSize}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取全部的出版社
export function getPublishingHouse() {
  const url = '/admin/PublishingHouse/all'
  return request({
    url: url,
    method: 'get'
  })
}

// 新增书籍
export function addBookStore(data) {
  const url = '/admin/BookStore'
  data['xqid'] = xqid
  return request({
    url: url,
    method: 'post',
    data
  })
}

// 修改书籍
export function putBookStore(data) {
  const url = '/admin/BookStore'
  return request({
    url: url,
    method: 'put',
    data
  })
}

// 删除书籍
export function deleteBookStore(uuid) {
  const url = `/admin/BookStore/${uuid}`
  return request({
    url: url,
    method: 'delete'
  })
}

// 删除学期全部书籍
export function deleteBookStoreAllByXqid() {
  const url = `/admin/BookStore/all/${xqid}`
  return request({
    url: url,
    method: 'delete'
  })
}

// excel导入书籍
export function importBookStoreByExcel(data) {
  const url = `/admin/BookStore/${xqid}/improt/excel`
  return request({
    url: url,
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data
  })
}

// ==================== 开课计划 ==============================
// 获取开课计划列表
export function getCurriculumPlanList(pageStart, pageSize) {
  const url = `/admin/CurriculumPlan/${xqid}/${pageStart}/${pageSize}`
  return request({
    url: url,
    method: 'get'
  })
}
