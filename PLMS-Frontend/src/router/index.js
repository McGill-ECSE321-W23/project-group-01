import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GeneralCreateGuestPass from '@/components/GeneralCreateGuestPass'
import Home from '@/components/Home'
import LoginUser from "@/components/LoginUser";
import SignUp from "@/components/SignUp";
import OwnerViewAppointments from "@/components/OwnerViewAppointments"
import OwnerViewServices from "@/components/OwnerViewServices"
import ParkingLotSettings from '@/components/ParkingLotSettings'
import ViewMonthlyCustomer from '@/components/owner/ViewMonthlyCustomer'
import OwnerHome from '@/components/owner/OwnerHome'
import MonthlyCustomerHome from "../components/MonthlyCustomerHome"

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
    },
    {
      path: '/owner/home',
      name: 'OwnerHome',
      component: OwnerHome
    },
    {
      path: '/owner/customers',
      name: 'ViewMonthlyCustomer',
      component: ViewMonthlyCustomer
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
      name: 'OwnerViewAppointments',
      component: OwnerViewAppointments,
    },

    {
      path: 'customer/:email',
      name: 'MonthlyCustomerHome',
      component: MonthlyCustomerHome, //
      props: true

    },
    {
      path: '/product',
      redirect: '/home'
    },
    {
      path: '/owner-view-services',
      name : 'OwnerViewServices',
      component: OwnerViewServices,
    },
    {
      path: '/pricing',
      redirect: '/home'
    }

  ]
})
