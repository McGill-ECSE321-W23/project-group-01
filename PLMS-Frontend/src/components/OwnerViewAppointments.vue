<template>
  <div>
    <div>

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
              <td>{{ appointment.id ? appointment.id : '' }}</td>
              <td>{{ appointment.date ? appointment.date : '' }}</td>
              <td>{{ appointment.startTime ? appointment.startTime : '' }}</td>
              <td>{{ appointment.endTime ? appointment.endTime : '' }}</td>
              <td>{{ appointment.customerEmail ? appointment.customerEmail : '' }}</td>
              <td>{{ appointment.employeeEmail ? appointment.employeeEmail : '' }}</td>
              <td>{{ appointment.serviceName ? appointment.serviceName : '' }}</td>
            </tr>
          </tbody>
        </table>
        <p class="error">{{ errorMsgDate }}</p>
      </div>
      <div>
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
              <td>{{ appointment.id ? appointment.id : '' }}</td>
              <td>{{ appointment.date ? appointment.date : '' }}</td>
              <td>{{ appointment.startTime ? appointment.startTime : '' }}</td>
              <td>{{ appointment.endTime ? appointment.endTime : '' }}</td>
              <td>{{ appointment.customerEmail ? appointment.customerEmail : '' }}</td>
              <td>{{ appointment.employeeEmail ? appointment.employeeEmail : '' }}</td>
              <td>{{ appointment.serviceName ? appointment.serviceName : '' }}</td>
            </tr>
          </tbody>
        </table>
        <p class="error">{{ errorMsgId }}</p>
      </div>
    </div>

    <div>
      <h2>Find Service Appointments By Employee Email</h2>
      <div>
        <label for="email">Enter Employee Email:</label>
        <input type="text" id="email" name="email" v-model="findByEmployeeEmailInput">
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
            <td>{{ appointment.id ? appointment.id : '' }}</td>
            <td>{{ appointment.date ? appointment.date : '' }}</td>
            <td>{{ appointment.startTime ? appointment.startTime : '' }}</td>
            <td>{{ appointment.endTime ? appointment.endTime : '' }}</td>
            <td>{{ appointment.customerEmail ? appointment.customerEmail : '' }}</td>
            <td>{{ appointment.employeeEmail ? appointment.employeeEmail : '' }}</td>
            <td>{{ appointment.serviceName ? appointment.serviceName : '' }}</td>
          </tr>
        </tbody>
      </table>
      <p class="error">{{ errorMsgEmployee }}</p>
    </div>

    <div>
      <h2>Update Appointment Employee</h2>
      <div>
        <label for="appointment-id">Appointment ID:</label>
        <input type="text" id="appointment-id" name="appointment-id" v-model="editByEmployeeEmailIdInput">
      </div>
      <div>
        <label for="employee-email">Employee Email:</label>
        <input type="email" id="employee-email" name="employee-email" v-model="editByEmployeeEmailInput">
      </div>
      <button type="button" @click="updateServiceAppointmentWithNewEmployee">Update Employee</button>
      <p class="success">{{ successMsgEditWithEmployeeEmail }}</p>
      <p class="error">{{ errorMsgEditWithEmployeeEmail }}</p>
    </div>

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
            <td>{{ appointment.id ? appointment.id : '' }}</td>
            <td>{{ appointment.date ? appointment.date : '' }}</td>
            <td>{{ appointment.startTime ? appointment.startTime : '' }}</td>
            <td>{{ appointment.endTime ? appointment.endTime : '' }}</td>
            <td>{{ appointment.customerEmail ? appointment.customerEmail : '' }}</td>
            <td>{{ appointment.employeeEmail ? appointment.employeeEmail : '' }}</td>
            <td>{{ appointment.serviceName ? appointment.serviceName : '' }}</td>
          </tr>
        </tbody>
      </table>
      <p class="error">{{ errorMsgAll }}</p>
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
      serviceAppointmentsByCustomer: [],
      appointment: {},
      errorMsgAll: '',
      errorMsgDate: '',
      selectedDate: '',
      appointmentId: '',
      errorMsgId: '',
      errorMsgEmployee: '',
      findByEmployeeEmailInput: '',
      successMsgEditWithEmployeeEmail: '',
      errorMsgEditWithEmployeeEmail: '',
      editByEmployeeEmailInput: ''
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

    getServiceAppointmentById() {
      axiosClient.get(`/serviceAppointment/${this.appointmentId}`)
        .then(response => {
          this.appointment = response.data
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgId = error.response.data;
        });
    },

    getServiceAppointmentsByCustomer() {
      axiosClient.get(`/serviceAppointment/employee/${this.findByEmployeeEmailInput}`)
        .then(response => {
          this.appointment = response.data
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgEmployee = error.response.data;
        })
    },

    getServiceAppointmentsByEmployee() {
      axiosClient.get(`/serviceAppointment/employee/${this.findByEmployeeEmailInput}`)
        .then(response => {
          this.appointment = response.data
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgEmployee = error.response.data;
        })
    },

    updateServiceAppointmentWithNewEmployee() {
      axiosClient.get(`/serviceAppointment/employeeEmail/${this.editByEmployeeEmailIdInput}"`, {
        employeeEmail: this.editByEmployeeEmailInput
      })
        .then(response => {
          this.successMsgEditWithEmployeeEmail = "Appointment updated successfully"
          // get the index of the service appointment
          const index = appointments.findIndex(appointment => appointment.id === editedAppointment.id);

          // Remove the appointment from the list
          appointments.splice(index, 1);

          // Insert the updated appointment at the same index
          appointments.splice(index, 0, editedAppointment);
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