import Vue from 'vue'
import Router from 'vue-router'
import GeneralCreateGuestPass from '@/components/pass/GeneralCreateGuestPass'
import InternalCreateMonthlyPass from '@/components/pass/InternalCreateMonthlyPass'
import Home from '@/components/shared/Home'
import LoginUser from "@/components/shared/LoginUser";
import SignUp from "@/components/shared/SignUp";
import OwnerViewAppointments from "@/components/owner/OwnerViewAppointments"
import OwnerViewServices from "@/components/owner/OwnerViewServices"
import ParkingLotSettings from '@/components/owner/ParkingLotSettings'
import ViewMonthlyCustomer from '@/components/employee/ViewMonthlyCustomer'
import ManageEmployees from "@/components/owner/ManageEmployees"
import ViewGuestPasses from "@/components/pass/ViewGuestPasses"
import BookServiceAppointmentGuest from "@/components/appointments/BookServiceAppointmentGuest"
import ViewMonthlyPasses from '@/components/pass/ViewMonthlyPasses'

import MonthlyCustomerHome from "@/components/customer/MonthlyCustomerHome"
import MonthlyCustomerPasses from "@/components/customer/MonthlyCustomerPasses";
import MonthlyCustomerAppointments from "@/components/customer/MonthlyCustomerAppointments";
import MonthlyCustomerManageAccount from "@/components/customer/MonthlyCustomerManageAccount";
import OwnerHome from "@/components/owner/OwnerHome";
import EmployeeHome from "@/components/employee/EmployeeHome";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/guest/pass/create',
      name: 'GeneralCreateGuestPass',
      component: GeneralCreateGuestPass
    },
    {
      path: '/guest/appointment/book',
      name: 'BookServiceAppointmentGuest',
      component: BookServiceAppointmentGuest
    },
    {
      path: '/owner/home',
      name: 'OwnerHome',
      component: OwnerHome
    },
    {
      path: '/owner/manage/employees',
      name: 'ManageEmployees',
      component: ManageEmployees
    },
    {
      path: '/employee/create/monthly-pass',
      name:'InternalMonthlyPassCreation',
      component: InternalCreateMonthlyPass,
    },
    {
      path: '/owner/create/monthly-pass',
      name:'InternalMonthlyPassCreation',
      component: InternalCreateMonthlyPass,
    },
    {
      path: '/employee/home/:email',
      name: 'EmployeeHome',
      component: EmployeeHome,
      props: true

    },
    {
      path: '/owner/customers',
      name: 'ViewMonthlyCustomer',
      component: ViewMonthlyCustomer
    },
    {
      path: '/employee/customers',
      name: 'ViewMonthlyCustomer',
      component: ViewMonthlyCustomer
    },
    {
      path: '/owner/view/guest-passes',
      name: 'ViewGuestPasses',
      component: ViewGuestPasses
    },
    {
      path: '/employee/view/guest-passes',
      name: 'ViewGuestPasses',
      component: ViewGuestPasses
    },
    {
      path: '/employee/view/monthly-passes',
      name: 'ViewMonthlyPasses',
      component: ViewMonthlyPasses
    },
    {
      path: '/owner/view/monthly-passes',
      name: 'ViewMonthlyPasses',
      component: ViewMonthlyPasses
    },
    {
      path: '/user/login',
      name: 'LoginUser',
      component: LoginUser
    },
    {
      path: '/owner/parking-lot',
      name: 'ParkingLotSettings',
      component: ParkingLotSettings
    },
    {
      path: '/user/signup',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/owner/view/appointments',
      name: 'OwnerViewAppointments',
      component: OwnerViewAppointments,
    },

    {
      path: 'customer/home/:email',
      name: 'MonthlyCustomerHome',
      component: MonthlyCustomerHome,
      props: true

    },
    {
      path: 'customer/pass/:email',
      name: 'MonthlyCustomerPasses',
      component: MonthlyCustomerPasses,
      props: true
    },
    {
      path: 'customer/appointment/:email',
      name: 'MonthlyCustomerAppointments',
      component: MonthlyCustomerAppointments,
      props: true
    },
    {
      path: 'customer/manage/:email',
      name: 'MonthlyCustomerManageAccount',
      component: MonthlyCustomerManageAccount,
      props: true
    },
    {
      path: '/owner/view/services',
      name : 'OwnerViewServices',
      component: OwnerViewServices,
    },
    {
      path: '/product',
      redirect: '/home'
    },

    {
      path: '/pricing',
      redirect: '/home'
    }

  ]
})
