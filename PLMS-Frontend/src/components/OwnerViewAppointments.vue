<template>
  <div>
    <div>

      <div>
        <h2>Search for Service Appointments on a Date</h2>
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
        <h2>Search for a Service Appointment by ID</h2>
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
      <h2>Search for Service Appointments Assigned to an Employee</h2>
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
      <h2>Search for Service Appointments for a Monthly Customer</h2>
      <div>
        <label for="email">Enter Customer Email:</label>
        <input type="text" id="email" name="email" v-model="findByCustomerEmailInput">
        <button type="button" @click="getServiceAppointmentsByCustomer">Find Appointment</button>
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
          <tr v-for="appointment in serviceAppointmentsByCustomer" :key="appointment.id">
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
      <p class="error">{{ errorMsgCustomer }}</p>
    </div>


    <div>
      <h2>Assign an Employee to an Appointment</h2>
      <div>
        <label for="appointment-id">Appointment ID:</label>
        <input type="text" id="appointment-id" name="appointment-id" v-model="editByEmployeeEmailIdInput">
      </div>
      <div>
        <label for="employee-email">Employee Email:</label>
        <input type="email" id="employee-email" name="employee-email" v-model="editByEmployeeEmailInput">
      </div>
      <button type="button" @click="updateServiceAppointmentWithNewEmployee">Update Appointment's Employee</button>
      <p class="success">{{ successMsgEditWithEmployeeEmail }}</p>
      <p class="error">{{ errorMsgEditWithEmployeeEmail }}</p>
    </div>

    <div>
      <h2>Update an Existing Service Appointment's details</h2>
      <div>
        <label for="id">ID:</label>
        <input type="text" id="id" name="id" v-model="editIdInput">
      </div>
      <div>
        <label for="date">Date:</label>
        <input type="date" id="date" name="date" v-model="editDateInput">
      </div>
      <div>
        <label for="startTime">Start Time:</label>
        <input type="time" id="startTime" name="startTime" v-model="editStartTimeInput">
      </div>
      <div>
        <label for="serviceName">Service Name:</label>
        <input type="text" id="serviceName" name="serviceName" v-model="editServiceNameInput">
      </div>
      <button type="button" :disabled="!editIdInput || !editDateInput || !editStartTimeInput || !editServiceNameInput"
        @click="editServiceAppointment">Edit Appointment</button>
      <p class="success">{{ successMsgEdit }}</p>
      <p class="error">{{ errorMsgEdit }}</p>
    </div>

    <div>
      <h2>Cancel a Service Appointment</h2>
      <div>
        <label for="cancelId">ID:</label>
        <input type="text" id="cancelId" name="cancelId" v-model="cancelIdInput">
      </div>
      <button type="button" :disabled="!cancelIdInput" @click="cancelServiceAppointment">Cancel Appointment</button>
      <p class="success">{{ successMsgCancel }}</p>
      <p class="error">{{ errorMsgCancel }}</p>
    </div>


    <div>
      <h2>All Service Appointments</h2>
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
      editIdInput: '',
      editDateInput: '',
      editStartTimeInput: '',
      editCustomerEmailInput: '',
      editServiceNameInput: '',
      errorMsgDate: '',
      selectedDate: '',
      appointmentId: '',
      errorMsgId: '',
      errorMsgEmployee: '',
      errorMsgCustomer: '',
      cancelIdInput: '',
      successMsgCancel: '',
      errorMsgCancel: '',
      successMsgEdit: '',
      errorMsgEdit: '',
      findByEmployeeEmailInput: '',
      findByCustomerEmailInput: '',
      successMsgEditWithEmployeeEmail: '',
      errorMsgEditWithEmployeeEmail: '',
      editByEmployeeEmailInput: '',
      editByEmployeeEmailIdInput: ''
    }
  },
  created() {
    axiosClient
      .get('/serviceAppointment')
      .then((response) => {
        this.appointments = response.data;
        this.errorMsgAll = ''
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
          this.serviceAppointmentsByDate = response.data
          this.errorMsgDate = ''
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgDate = error.response.data;
        });
    },

    getServiceAppointmentById() {
      if (Number.isInteger(this.editIdInput)) {
        this.errorMsgEdit = "Please enter an integer id"
        return
      }
      axiosClient.get(`/serviceAppointment/${this.appointmentId}`)
        .then(response => {
          this.appointment = response.data
          this.errorMsgId = ''
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgId = error.response.data;
        });
    },

    getServiceAppointmentsByCustomer() {
      axiosClient.get(`/serviceAppointment/customer/${this.findByCustomerEmailInput}`)
        .then(response => {
          this.serviceAppointmentsByCustomer = response.data
          this.errorMsgCustomer = ''
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgCustomer = error.response.data;
        })
    },

    getServiceAppointmentsByEmployee() {
      axiosClient.get(`/serviceAppointment/employee/${this.findByEmployeeEmailInput}`)
        .then(response => {
          this.serviceAppointmentsByEmployee = response.data
          this.errorMsgEmployee = ''
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgEmployee = error.response.data;
        })
    },

    updateServiceAppointmentWithNewEmployee() {
      if (!/^\d+$/.test(this.editByEmployeeEmailIdInput)) {
        this.errorMsgEditWithEmployeeEmail = "Please enter an integer id"
        return
      }
      axiosClient.put(`/serviceAppointment/employeeEmail/${this.editByEmployeeEmailIdInput}?employeeEmail=${this.editByEmployeeEmailInput}`)
        .then(response => {
          this.successMsgEditWithEmployeeEmail = "Appointment updated successfully"
          this.errorMsgEditWithEmployeeEmail = ''
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgEditWithEmployeeEmail = error.response.data;
        })
    },

    editServiceAppointment() {
      // Format the time as HH:mm:ss
      const formattedTime = this.editStartTimeInput + ":00";
      console.log(formattedTime)
      if (!/^\d+$/.test(this.editIdInput)) {
        this.errorMsgEdit = "Please enter an integer id"
        return
      }
      const requestBody = {
        date: this.editDateInput,
        startTime: formattedTime,
        customerEmail: '',
        serviceName: this.editServiceNameInput
      };
      axiosClient.put(`/serviceAppointment/${this.editIdInput}`, requestBody)
        .then(response => {
          this.editIdInput = '';
          this.editDateInput = '';
          this.editStartTimeInput = '';
          this.editServiceNameInput = '';
          this.disableEditButton = false;
          this.successMsgEdit = "Appointment updated successfully";
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgEdit = error.response.data;
          this.disableEditButton = false;
        })
    },

    cancelServiceAppointment() {
      if (!/^\d+$/.test(this.cancelIdInput)) {
        this.errorMsgCancel = "Please enter an integer id"
        return
      }

      axiosClient.delete(`/serviceAppointment/${this.cancelIdInput}`)
        .then(response => {
          this.editMsgCancel = '';
          this.successMsgCancel = "Appointment cancelled successfully";
        })
        .catch(error => {
          console.log(error.response.data);
          this.errorMsgCancel = error.response.data;
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