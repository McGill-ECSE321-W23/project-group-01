import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginUser from "@/components/LoginUser";
import SignUp from "@/components/SignUp";
import OwnerViewAppointments from "@/components/OwnerViewAppointments"
import GuestBookAppointment from "@/components/GuestBookAppointment"

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
    },
    {
      path: '/owner-view-appointments',
      name : 'OwnerViewAppointments',
      component: OwnerViewAppointments
    },
    {
      path: '/guest/book-appointment',
      name : 'GuestBookAppointment',
      component: GuestBookAppointment
    }
  ]
})
