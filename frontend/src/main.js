import './assets/main.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "bootstrap/dist/css/bootstrap-utilities.css"

createApp(App)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
