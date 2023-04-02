<template>
  <div>
    <h1>Booking Form</h1>
    <form>
      <select id="services" v-model="service"></select>
      <label for="date">Date:</label>
      <input type="date" id="date" name="date" v-model="date"><br>

      <label for="start-time">Start Time:</label>
      <input type="time" id="start-time" name="start-time" v-model="startTime"><br>

      <button type="submit" v-bind:disabled="bookAppointmentButtonDisabled" @click="bookAppointment()">Book</button>
      <p class="error">{{ errorMsg }}</p>
    </form>
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
  name: 'book-guest-appointment',
  // Your component options go here

  data() {
    return {
      date: '',
      startTime: '',
      service: '',
      errorMsg: '',
    }
  },
created() {
  axiosClient.get('/service')
    .then((response) => {
      console.log(response);
    })
    .catch((err) => {
      console.log(err);
      this.errorMsg = err;
    });
},

  methods: {
    bookAppointment(){
      const request = {service: "blah", date: this.date, time: this.startTime};
      axiosClient.post('/serviceAppointment', request)
        .then((response) => {
          this.errorMsg = "Appointment booked! Service"
        })
        .catch((err) => {
          this.errorMsg = `Failed to create: ${err.response.data}`;
        })
    }
  },

    
  computed:{
    bookAppointmentButtonDisabled(){
      return !this.startTime || !this.date;
    }
  }
}

</script>

<style>


</style>