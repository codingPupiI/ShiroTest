import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import axios from 'axios'
import mavonEditor from 'mavon-editor'

import "element-ui/lib/theme-chalk/index.css"
import "./axios"
import 'mavon-editor/dist/css/index.css'
import './permission'

Vue.prototype.$axios = axios
Vue.use(Element)
Vue.use(mavonEditor)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
