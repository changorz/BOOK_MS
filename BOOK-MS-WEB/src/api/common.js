import request from '@/utils/request'

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

