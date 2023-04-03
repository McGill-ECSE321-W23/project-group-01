<template>
  <div>
  <div>
    <h2>Service Appointments</h2>
    <table class="center bordered-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Date</th>
          <th>Start Time</th>
          <th>End Time</th>
          <th>Customer Email</th>
          <th>Employee Email</th>
          <th>Service Name</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="appointment in serviceAppointments" :key="appointment.id">
          <td>{{ appointment.id }}</td>
          <td>{{ appointment.date }}</td>
          <td>{{ appointment.startTime }}</td>
          <td>{{ appointment.endTime }}</td>
          <td>{{ appointment.customerEmail }}</td>
          <td>{{ appointment.employeeEmail }}</td>
          <td>{{ appointment.serviceName }}</td>
        </tr>
      </tbody>
    </table>
    <p class="error">{{ errorMsgAll }}</p>

    <div>
      <h2>Find Service Appointments By Date</h2>
      <div>
        <label for="date">Select Date:</label>
        <input type="date" id="date" name="date" v-model="selectedDate">
        <button type="button" @click="getServiceAppointmentsByDate">Get Appointments</button>
      </div>
      <table class="center bordered-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Customer Email</th>
            <th>Employee Email</th>
            <th>Service Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appointment in serviceAppointmentsByDate" :key="appointment.id">
            <td>{{ appointment.id }}</td>
            <td>{{ appointment.date }}</td>
            <td>{{ appointment.startTime }}</td>
            <td>{{ appointment.endTime }}</td>
            <td>{{ appointment.customerEmail }}</td>
            <td>{{ appointment.employeeEmail }}</td>
            <td>{{ appointment.serviceName }}</td>
          </tr>
        </tbody>
      </table>
      <p class="error">{{ errorMsgDate }}</p>
    </div>

    <h2>Find Appointment by ID</h2>
    <div>
      <label for="id">Enter ID:</label>
      <input type="text" id="id" name="id" v-model="appointmentId">
      <button type="button" @click="getServiceAppointmentById">Find Appointment</button>
    </div>
    <table class="center bordered-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Date</th>
          <th>Start Time</th>
          <th>End Time</th>
          <th>Customer Email</th>
          <th>Employee Email</th>
          <th>Service Name</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{{ appointment.id }}</td>
          <td>{{ appointment.date }}</td>
          <td>{{ appointment.startTime }}</td>
          <td>{{ appointment.endTime }}</td>
          <td>{{ appointment.customerEmail }}</td>
          <td>{{ appointment.employeeEmail }}</td>
          <td>{{ appointment.serviceName }}</td>
        </tr>
      </tbody>
    </table>
    <p class="error">{{ errorMsgId }}</p>
  </div>

  <div>
      <h2>Find Service Appointments By Employee Email</h2>
      <div>
        <label for="email">Enter Employee Email:</label>
      <input type="text" id="email" name="email" v-model="employeeEmail">
      <button type="button" @click="getServiceAppointmentsByEmployee">Find Appointment</button>
      </div>
      <table class="center bordered-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Customer Email</th>
            <th>Employee Email</th>
            <th>Service Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appointment in serviceAppointmentsByEmployee" :key="appointment.id">
            <td>{{ appointment.id }}</td>
            <td>{{ appointment.date }}</td>
            <td>{{ appointment.startTime }}</td>
            <td>{{ appointment.endTime }}</td>
            <td>{{ appointment.customerEmail }}</td>
            <td>{{ appointment.employeeEmail }}</td>
            <td>{{ appointment.serviceName }}</td>
          </tr>
        </tbody>
      </table>
      <p class="error">{{ errorMsgEmployee }}</p>
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
name: 'OwnerViewAppointments',
data() {
  return {
    serviceAppointments: [],
    serviceAppointmentsByDate: [],
    serviceAppointmentsByEmployee: [],
    appointment: {},
    errorMsgAll: '',
    errorMsgDate: '',
    selectedDate: '',
    appointmentId: '',
    errorMsgId: '',
    errorMsgEmployee: '',
    employeeEmail: '', 
    errorMsgEmployee: ''
  }
},
created() {
    axiosClient
      .get('/serviceAppointment')
      .then((response) => {
        this.appointments = response.data;
      })
      .catch((error) => {
        console.log(error.response.data);
        this.errorMsgAll = error.response.data;
      });
  },

methods: {
  
      getServiceAppointmentsByDate() {
  axiosClient.get(`/serviceAppointment/date/${this.selectedDate}`)
    .then(response => {
      this.serviceAppointments = response.data
    })
    .catch(error => {
      console.log(error.response.data);
      this.errorMsgDate = error.response.data;
    });
  },

    getServiceAppointmentById(){
      axiosClient.get(`/serviceAppointment/${this.appointmentId}`)
    .then(response => {
      this.appointment = response.data
    })
    .catch(error => {
      console.log(error.response.data);
      this.errorMsgId = error.response.data;
    });
  },

    getServiceAppointmentsByEmployee(){
      axiosClient.get(`/serviceAppointment/employee/${this.employeeEmail}`)
    .then(response => {
      this.appointment = response.data
    })
    .catch(error => {
      console.log(error.response.data);
      this.errorMsgEmployee = error.response.data;
    })
    }
},

  computed: {
    createUserButtonDisabled() {
      return !this.email.trim() || !this.password.trim() || !this.name.trim()
    }
  }
}

</script>


<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

table.center, div.center {
    margin: 0 auto;
  }

table{
  padding: 20px;
}
label{
  padding: 20px;
}

.bordered-table th,
.bordered-table td {
  border: 1px solid black;
  padding: 8px;
}

</style>