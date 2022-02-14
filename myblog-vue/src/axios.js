import axios from 'axios'
import Element from 'element-ui'
import router from "./router"
import store from './store'

axios.defaults.baseURL = "http://localhost:8081"

// 前制拦截
axios.interceptors.request.use(config => {
    return config;
})

// 后置拦截
axios.interceptors.response.use(response => {
        console.log(response);
        let res = response.data;
        console.log(res);

        if (res.code === 200) {
            return response;
        } else {
            Element.Message.error(res.data, {duration: 3 * 1000});
            return Promise.reject(res.data);
        }
    },
    error => {
        if(error.response.data) {
            error.message = error.response.data.msg
        }

        if (error.response.status == 401) {
            store.commit("REMOVE_INFO");
            router.push("/login");
            error.message = '请重新登录';
        }
        if (error.response.status === 403) {
            error.message = '权限不足，无法访问';
        }
        Element.Message({
            message: error.message,
            type: 'error',
            duration: 3 * 1000
        })
        return Promise.reject(error)
    })