import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import App from './App'
import store from './store'
import router from './router'
import Print from 'vue-print-nb'

import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'

import '@/icons'
import '@/permission'

Vue.use(ViewUI)
Vue.use(ElementUI)
Vue.use(Print)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
