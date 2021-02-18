import request from '@/utils/request'
import store from '@/store'
import qs from 'qs'

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

// 获取全部的出版社:admin
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
    headers: { 'Content-Type': 'multipart/fill_in-data' },
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

// 修改开课计划
export function putCurriculumPlan(data) {
  const url = '/admin/CurriculumPlan'
  return request({
    url: url,
    method: 'put',
    data
  })
}

// 新增开课计划
export function addCurriculumPlan(data) {
  const url = '/admin/CurriculumPlan'
  data['xqid'] = xqid
  return request({
    url: url,
    method: 'post',
    data
  })
}

// 删除开课计划
export function deleteCurriculumPlan(uuid) {
  const url = `/admin/CurriculumPlan/${uuid}`
  return request({
    url: url,
    method: 'delete'
  })
}

// 按学期删除全部开课计划
export function deleteCurriculumPlanByXqid() {
  const url = `/admin/CurriculumPlanAll/${xqid}`
  return request({
    url: url,
    method: 'delete'
  })
}

// excel导入开课计划
export function importCurriculumPlanByExcel(data) {
  const url = `/admin/CurriculumPlan/${xqid}/improt/excel`
  return request({
    url: url,
    method: 'post',
    headers: { 'Content-Type': 'multipart/fill_in-data' },
    data
  })
}

// ==================== 学生信息管理 ==============================

// 获取书籍库书籍列表
export function getStudentInfoList(pageStart, pageSize, search) {
  const url = search === ''
    ? `/admin/StudentInfo/findStudentInfo/${xqid}/${pageStart}/${pageSize}`
    : `/admin/StudentInfo/findStudentInfoByXhOrName/${xqid}/${pageStart}/${pageSize}/${search}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取所有班级
export function getAllClasspath() {
  const url = `/admin/StudentInfo/${xqid}/getAllClasspath`
  return request({
    url: url,
    method: 'get'
  })
}
// 获取所有班级
export function getAllClasspathByQuery(query) {
  const url = `/admin/StudentInfo/${xqid}/${query}/getAllClasspath`
  return request({
    url: url,
    method: 'get'
  })
}

// 新增学生
export function addStudentInfo(data) {
  const url = '/admin/StudentInfo'
  data['xqid'] = xqid
  return request({
    url: url,
    method: 'post',
    data
  })
}

// 修改学生
export function putStudentInfo(data) {
  const url = '/admin/StudentInfo'
  return request({
    url: url,
    method: 'put',
    data
  })
}

// 删除学生
export function deleteStudentInfo(uuid) {
  const url = `/admin/StudentInfo/${uuid}`
  return request({
    url: url,
    method: 'delete'
  })
}
// 删除学期全部学生
export function deleteStudentInfoAll() {
  const url = `/admin/StudentInfo/all/${xqid}`
  return request({
    url: url,
    method: 'delete'
  })
}

// excel导入学生信息
export function importStudentInfoByExcel(data) {
  const url = `/admin/StudentInfo/${xqid}/improt/excel`
  return request({
    url: url,
    method: 'post',
    headers: { 'Content-Type': 'multipart/fill_in-data' },
    data
  })
}

// ==================== 总表管理 ==============================

// 获取书籍库书籍列表
export function getBookTotalList(pageStart, pageSize, search, data) {
  const url = `/admin/BookTotal/all/${xqid}/${pageStart}/${pageSize}`
  return request({
    url: url,
    method: 'get',
    params: data
  })
}

// 通过开课计划 生成 总表数据
export function createBookTota(data) {
  return request({
    url: `/admin/BookTotal/insertBookTotalByCurriclumPlan/${xqid}`,
    method: 'post',
    data: qs.stringify(data)
  })
}

// 修改填报的日期
export function changeTime(data) {
  return request({
    url: `/admin/BookTotal/changeTime/${xqid}`,
    method: 'post',
    data: qs.stringify(data)
  })
}

// 下载填报总Excel
export function downloadFillExcel() {
  const url = `/admin/BookTotal/download/${xqid}`
  return request({
    url: url,
    method: 'get',
    responseType: 'blob'
  })
}

// 获取班级订单列表
export function getClaOrder(claName) {
  const url = `/admin/BookTotal/getClaOrder/${xqid}/${claName}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取班级订单列表
export function getClaStudentList(claName) {
  const url = `/admin/StudentInfo/List/${xqid}/${claName}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取出版社报定但
export function getPublishingHouseOrder(phName, phNames) {
  const url = (phNames === '' || phNames === null)
    ? `/admin/BookTotal/getPhOrder/${xqid}/${phName}`
    : `/admin/BookTotal/getPhOrder/${xqid}/${phName}/${phNames}`
  return request({
    url: url,
    method: 'get'
  })
}
