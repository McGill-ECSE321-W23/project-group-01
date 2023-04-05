import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GeneralCreateGuestPass from '@/components/GeneralCreateGuestPass'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/createGuestPass',
      name: 'GeneralCreateGuestPass',
      component: GeneralCreateGuestPass
    }
  ]
})
