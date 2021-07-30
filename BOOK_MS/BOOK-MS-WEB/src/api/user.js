import request from '@/utils/request'
import qs from 'qs'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function getXqidInfo() {
  return request({
    url: '/public/xqInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function getmassage(value) {

  const url = `/massage/getUserbyid/${value}`
  return request({
    url: url,
    method: 'get'
  })
}
