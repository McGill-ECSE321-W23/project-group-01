import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
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
      path: '/parking-lot-settings',
      name: 'ParkingLotSettings',
      component: ParkingLotSettings
    }
  ]
})
