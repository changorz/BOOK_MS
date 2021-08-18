import request from '@/utils/request'

export function getUserMessage() {
  return request({
    url: '/api/message/User',
    method: 'get'
  })
}

// 修改用户
export function putUserMessage(data) {
  const url = '/api/message/User'
  return request({
    url: url,
    method: 'put',
    data
  })
}
