const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  activeXqid: state => state.user.activeXqid,
  activeXqidValue: state => state.user.activeXqidValue,
  avatar: state => state.user.avatar,
  name: state => state.user.name
}
export default getters
