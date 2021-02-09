import request from '@/utils/request'
import store from '@/store'

const xqid = store.getters.activeXqid

// 获取书籍库书籍列表
export function getBookTotalList(pageStart, pageSize, data) {
  const url = `/fill/BookTotal/all/${xqid}/${pageStart}/${pageSize}`
  return request({
    url: url,
    method: 'get',
    params: data
  })
}

// 获取填报时的展示信息
export function getFillInfo(uuid) {
  const url = `/fill/BookTotal/${xqid}/${uuid}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取书籍库书籍列表前10匹配
export function getBookStoreListTop10(search) {
  const url = `/fill/BookStore/findByBook/${xqid}/${search}`
  return request({
    url: url,
    method: 'get'
  })
}

// 获取书籍库书详细信息
export function getBookOne(uuid) {
  const url = `/fill/BookStore/findBook/${uuid}`
  return request({
    url: url,
    method: 'get'
  })
}
