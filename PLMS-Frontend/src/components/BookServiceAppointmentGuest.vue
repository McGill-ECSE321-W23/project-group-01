<template>
  <div>
  <div>
    <div>
        <h2>All Services</h2>
        <table class="center bordered-table">
          <thead>
            <tr>
              <th>Service Name</th>
              <th>Cost</th>
              <th>Length in Hours</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="service in services" :key="service.serviceName">
              <td>{{ service.serviceName ? service.serviceName : '' }}</td>
              <td>{{ service.cost ? service.cost : '' }}</td>
              <td>{{ service.lengthInHours ? service.lengthInHours : '' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  <div>
    <h2>Book a Service Appointment</h2>
    <div>
      <label for="serviceName">Service Name:</label>
      <select id="serviceName" v-model="serviceName">
        <option v-for="service in servicesDropDown" :value="service">{{ service }}</option>
      </select>
    </div>
    <div>
      <label for="date">Date:</label>
      <input type="date" id="date" name="date" v-model="date">
    </div>
    <div>
      <label for="startTime">Start Time:</label>
      <input type="time" id="startTime" name="startTime" v-model="startTime">
    </div>
    <button type="button" :disabled="!serviceName || !date || !startTime" @click="bookServiceAppointment">Book
      Appointment</button>
    <div>
      <p class="parkingLotHours"> {{ parkingLotHours }}</p>
    </div>
    <p class="success">{{ successMsg }}</p>
    <p class="error">{{ errorMsg }}</p>
  </div>
</div>
</template>

<script>
import axios from 'axios';
const config = require('../../config');
const frontendUrl = config.dev.host + ':' + config.dev.port;
const axiosClient = axios.create({
  // Note the baseURL, not baseUrl
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  data() {
    return {
      services: [],
      servicesDropDown: [],
      serviceName: '',
      date: '',
      startTime: '',
      successMsg: '',
      errorMsg: '',
      parkingLotHours: ''
    };
  },

  created() {
    axiosClient.get('/service')
      .then((response) => {
        this.servicesDropDown = response.data.map(service => service.serviceName);
        this.services = response.data
      })
      .catch((error) => {
      });

      axiosClient.get('/parkingLot')
  .then((response) => {
    // format the parking lots hours of operations so we know when we can book appointments
    console.log(response.data)
    console.log(response.data.openingTime)
    const openingTime = response.data.openingTime;

    // get hours and minutes to know if it is AM or PM
    let openingHour = parseInt(openingTime.substring(0, 2));
    let openingMinute = openingTime.substring(3, 5);
    let ampmOpening = openingHour >= 12 ? 'PM' : 'AM';
    openingHour = openingHour % 12 || 12;
    let formattedOpeningTime = openingHour + ':' + openingMinute + ' ' + ampmOpening;

    console.log(response.data)
    console.log(response.data.closingTime)
    const closingTime = response.data.closingTime;

    let closingHour = parseInt(closingTime.substring(0, 2));
    let closingMinute = closingTime.substring(3, 5);
    let ampmClosing = closingHour >= 12 ? 'PM' : 'AM';
    closingHour = closingHour % 12 || 12;
    let formattedClosingTime = closingHour + ':' + closingMinute + ' ' + ampmClosing;

    console.log(formattedOpeningTime);
    this.parkingLotHours = "Parking lot hours of operation: " + formattedOpeningTime + "-" + formattedClosingTime;
  })
  .catch((error) => {
    this.parkingLotHours = "The parking lot has not been finished yet. Please book appointments at a later date."
  });
  },


  methods: {
    bookServiceAppointment() {
      const formattedTime = this.startTime + ":00"
      const requestBody = {
        date: this.date,
        startTime: formattedTime,
        customerEmail: null, // Replace with customer email if applicable
        serviceName: this.serviceName
      };
      console.log(this.date)
      console.log(this.serviceName)
      console.log(formattedTime)
      axiosClient.post('/serviceAppointment', requestBody)
        .then(response => {
          this.date = '';
          this.startTime = '';
          this.serviceName = '';
          this.successMsg = '';
          this.successMsg = "Appointment booked successfully. Your appointment ID number is: " + response.data.id;
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsg = error.response.data;
        })
    }
  }
};
</script>

<style>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  
  table.center,
  div.center {
    margin: 0 auto;
  }
  
  table {
    padding: 20px;
  }
  
  label {
    padding: 20px;
  }
  
  .bordered-table th,
  .bordered-table td {
    border: 1px solid black;
    padding: 8px;
  }
</style>
