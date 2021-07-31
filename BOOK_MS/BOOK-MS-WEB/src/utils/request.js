import axios from 'axios'

import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

axios.defaults.withCredentials = true

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 60000
})

// request interceptor
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    // 文件过滤
    if (response.headers.requesttype === 'file') {
      return response
    }
    const res = response.data
    if (!res.success) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      //    UNAUTHENTICATED(false, 10002, "用户权限不足！"),   展示页面
      //
      //     Login_NO(false, 10003, "无权访问，请登录！"),
      //     Login_err_UsernameNotFoundException_BadCredentialsException(false, 10004, "登录失败，用户名或密码错误"),
      //     Login_err_DisabledException(false, 10005, "登录失败，用户已被禁用"),
      //     下面的没写
      //     Login_err_LockedException(false, 10006, "登录失败，账户锁定"),
      //     Login_err_AccountExpiredException(false, 10007, "登录失败，账户过期"),
      //     Login_err_CredentialsExpiredException(false, 10008, "登录失败，证书过期"),
      if (res.code === 10003) {
        // to re-login
        MessageBox.confirm('登录过期，请登录！', '去登录', {
          confirmButtonText: '登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    // 状态码不是200就会走这里
    console.log(error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)
export default service
