import Vue from 'vue'
import Router from 'vue-router'
import GeneralCreateGuestPass from '@/components/GeneralCreateGuestPass'
import InternalCreateMonthlyPass from '@/components/InternalCreateMonthlyPass'
import Home from '@/components/Home'
import LoginUser from "@/components/LoginUser";
import SignUp from "@/components/SignUp";
import OwnerViewAppointments from "@/components/owner/OwnerViewAppointments"
import OwnerViewServices from "@/components/owner/OwnerViewServices"
import ParkingLotSettings from '@/components/owner/ParkingLotSettings'
import ViewMonthlyCustomer from '@/components/owner/ViewMonthlyCustomer'
import ManageEmployees from "@/components/owner/ManageEmployees"
import ViewGuestPasses from "@/components/owner/ViewGuestPasses"
import BookServiceAppointmentGuest from "@/components/BookServiceAppointmentGuest"
import ViewMonthlyPasses from '@/components/owner/ViewMonthlyPasses'
import EmployeeHome from '@/components/employee/EmployeeHome'
import MonthlyCustomerHome from "@/components/MonthlyCustomerHome"
import MonthlyCustomerPasses from "@/components/MonthlyCustomerPasses";
import MonthlyCustomerAppointments from "@/components/MonthlyCustomerAppointments";
import MonthlyCustomerManageAccount from "@/components/MonthlyCustomerManageAccount";
import OwnerHome from "@/components/owner/OwnerHome"; 
import ViewPasses from "@/components/ViewPasses";
import OwnerPasses from "@/components/owner/OwnerPasses";
import EmployeePasses from "@/components/employee/EmployeePasses";


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/passes',
      name: 'Pass',
      component: ViewPasses
    },
    {
      path: '/createGuestPass',
      name: 'GeneralCreateGuestPass',
      component: GeneralCreateGuestPass
    },
    {
      path: '/book-service-appointment',
      name: 'BookServiceAppointmentGuest',
      component: BookServiceAppointmentGuest
    },
    {
      path: '/owner/home',
      name: 'OwnerHome',
      component: OwnerHome
    },
    {
      path: '/owner/pass',
      name: 'OwnerPasses',
      component: OwnerPasses,
    },
    {
      path: '/employee/pass',
      name: 'EmployeePasses',
      component: EmployeePasses,
    },
    {
      path: '/owner/manage-employees',
      name: 'ManageEmployees',
      component: ManageEmployees
    },
    {
      path: '/employee/create-monthly-pass',
      name:'InternalMonthlyPassCreation',
      component: InternalCreateMonthlyPass,
    },
    {
      path: '/employee/:email',
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
      path: '/owner/guest-passes',
      name: 'ViewGuestPasses',
      component: ViewGuestPasses
    },
    {
      path: '/owner/monthly-passes',
      name: 'ViewMonthlyPasses',
      component: ViewMonthlyPasses
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
      component: MonthlyCustomerHome,
      props: true

    },
    {
      path: 'customer/pass:email',
      name: 'MonthlyCustomerPasses',
      component: MonthlyCustomerPasses,
      props: true
    },
    {
      path: 'customer/appointment:email',
      name: 'MonthlyCustomerAppointments',
      component: MonthlyCustomerAppointments,
      props: true
    },
    {
      path: 'customer/manage:email',
      name: 'MonthlyCustomerManageAccount',
      component: MonthlyCustomerManageAccount,
      props: true
    },
    {
      path: '/owner-view-services',
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
