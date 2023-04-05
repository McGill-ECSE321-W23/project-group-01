<template>
    <div>
      <h1>Customer Information</h1>
  
      <form @submit.prevent="searchCustomer">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="searchEmail">
        <button type="submit">Search</button>
      </form>
      <p class="error">{{ gettingCustomerErrorMsg }}</p>
  
      <h2>All Customers</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(customer, index) in customers" :key="index" 
          @click="handleRowClick(customer)" @mouseover="handleRowHover(customer)" 
          @mouseout="handleRowHover(null)" :class="{ 'row-highlighted': customer === hoveredCustomer, 'row-selected': customer === selectedCustomer }">
            <td>{{ customer.name }}</td>
            <td>{{ customer.email }}</td>
          </tr>
        </tbody>
      </table>
      <p class="error">{{ gettingAllCustomerErrorMsg }}</p>

      <div v-if="selectedCustomer">
        <h2>Selected Customer</h2>
        <p>Name: {{ selectedCustomer.name }}</p>
        <p>Email: {{ selectedCustomer.email }}</p>
        <p>Passes:</p>
        <table v-if="selectedCustomer.passes">
          <thead>
            <tr>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Floor Number</th>
              <th>Spot Number</th>
              <th>Large Spot</th>
              <th>License Plate</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(pass, index) in selectedCustomer.passes" :key="index">
              <td>{{ pass.startDate }}</td>
              <td>{{ pass.endDate }}</td>
              <td>{{ pass.floorNumber }}</td>
              <td>{{ pass.spotNumber }}</td>
              <td>{{ pass.large }}</td>
              <td>{{ pass.licensePlate }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <p class="error">{{ gettingPassErrorMsg }}</p>
    </div>
  </template>
  
<script>
import axios from 'axios';
const config = require('../../../config');
const frontendUrl = config.dev.host + ':' + config.dev.port;
const axiosClient = axios.create({
  // Note the baseURL, not baseUrl
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
    data() {
        return {
            customers: [],
            searchEmail: '',
            gettingCustomerErrorMsg: '',
            gettingAllCustomerErrorMsg: '',
            gettingPassErrorMsg: '',
            hoveredCustomer: null,
            selectedCustomer: null
        };
    },
    created() {
        // Fetch all customers on component mount
        this.fetchCustomers();
    },
    methods: {
        async fetchCustomers() {
            try {
                const response = await axiosClient.get('/customers');
                this.customers = response.data;
                this.gettingAllCustomerErrorMsg = '';
            } catch (error) {
                this.gettingAllCustomerErrorMsg = error.response.data;
            }
        },
        async fetchMonthlyCustomer(email) {
          try {
            const response = await axiosClient.get(`/customer?email=${email}`);
            this.updateSelectedMonthlyCustomer(response.data);
            this.gettingCustomerErrorMsg = '';
          }catch (error) {
            this.gettingCustomerErrorMsg = error.response.data;
          }
        },
        async updateSelectedMonthlyCustomer(monthlyCustomer){
          try {
            this.selectedCustomer = monthlyCustomer;
            const response = await axiosClient.get(`/monthlypass/customer/${monthlyCustomer.email}`);
            this.selectedCustomer.passes = response.data;
            this.gettingPassErrorMsg = '';
          }catch (error) {
            console.log(error);
            this.gettingPassErrorMsg = error.response.data;
          }
        },
        searchCustomer() {
          this.fetchMonthlyCustomer(this.searchEmail);
        },
        handleRowClick(customer) {
          this.updateSelectedMonthlyCustomer(customer);
        },
        handleRowHover(customer) {
          this.hoveredCustomer = customer;
        }
    }
};
</script>
<style>
.row-highlighted {
  background-color: #cbcbcb;
  cursor: pointer;
}
.row-selected{
  background-color: #8e8e8e;
}
.error{
  color: red;
}
</style>