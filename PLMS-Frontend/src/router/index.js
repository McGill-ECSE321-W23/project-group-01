import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginUser from "@/components/LoginUser";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login-user',
      name: 'LoginUser',
      component: LoginUser
    },
    {
      path: '/create-customer',
      name: 'SignUp',
      component: SignUp
    }
  ]
})
