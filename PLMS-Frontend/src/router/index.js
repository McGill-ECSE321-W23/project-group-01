import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home'
import LoginUser from "@/components/LoginUser";
import SignUp from "@/components/SignUp";
import OwnerViewAppointments from "@/components/OwnerViewAppointments"
import ParkingLotSettings from '@/components/ParkingLotSettings'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },

    {
      path: '/home',
      name: 'Home',
      component: Home
    },

    {
      path: '/login-user',
      name: 'LoginUser',
      component: LoginUser
    },
    {
      path: '/parking-lot-settings',
      name: 'ParkingLotSettings',
      component: ParkingLotSettings
    },
    {
      path: '/create-customer',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/owner-view-appointments',
      name : 'OwnerViewAppointments',
      component: OwnerViewAppointments,
      
      path: '/product',
      redirect: '/home'
    },
    {
      path: '/pricing',
      redirect: '/home'
    }
    
  ]
})
