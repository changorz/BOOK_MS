import request from '@/utils/request'
import store from '@/store'

const xqid = store.getters.activeXqid

// 下拉列表
export function getSelectorList(type) {
  const url = `/public/selects/${type}`
  return request({
    url: url,
    method: 'get'
  })
}

// 下拉列表添加
export function addSelector(type, value) {
  const url = `/admin/public/Selector/${type}/${value}`
  return request({
    url: url,
    method: 'post'
  })
}

// 下拉列表删除
export function deleteSelector(type, value) {
  const url = `/admin/public/Selector/${type}/${value}`
  return request({
    url: url,
    method: 'delete'
  })
}

// 设置活跃学期
export function setActiveXq(xq) {
  const url = `/admin/public/setActiveXq/${xq}`
  return request({
    url: url,
    method: 'put'
  })
}

// /admin/public/addXq
// 增加一个新学期
export function addXq(xq) {
  const url = `/admin/public/addXq/${xq}`
  return request({
    url: url,
    method: 'post'
  })
}

// 删除学期
export function deleteXq(xq) {
  const url = `/admin/public/deleteXq/${xq}`
  return request({
    url: url,
    method: 'delete'
  })
}

// 获取总表的所有选择值
export function getselectsAllByBookTota() {
  const url = `/public/selects/allByBookTota/${xqid}`
  return request({
    url: url,
    method: 'get'
  })
}
