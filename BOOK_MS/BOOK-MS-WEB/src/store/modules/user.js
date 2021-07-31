import { getInfo, getXqidInfo, login, logout } from '@/api/user'
import { getToken, removeToken, setToken } from '@/utils/auth'
// import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    activeXqid: '',
    activeXqidValue: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ActiveXqid: (state, activeXqid) => {
    state.activeXqid = activeXqid
  },
  SET_ActiveXqidValue: (state, activeXqidValue) => {
    state.activeXqidValue = activeXqidValue
  }
}

const actions = {
  // 登录
  login({ commit }, userInfo) {
    const { username, pwd } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), pwd: pwd }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取登录信息
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('验证失败，请重新登录。')
        }
        const { alias, img } = data
        commit('SET_NAME', alias)
        commit('SET_AVATAR', img != null ? img : 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 刷新学期ID信息
  getXqidInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getXqidInfo().then(response => {
        const { data } = response
        const { activeXqid, activeXqidValue } = data
        commit('SET_ActiveXqid', activeXqid)
        commit('SET_ActiveXqidValue', activeXqidValue)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken()
        // resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

