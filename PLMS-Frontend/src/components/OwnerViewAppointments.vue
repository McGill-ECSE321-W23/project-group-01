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
          <label for="id">Select Appointment ID:</label>
          <select id="id" name="id" v-model="appointmentId">
            <option value="">-- Select an ID --</option>
            <option v-for="id in appointmentIds" :key="id" :value="id">{{ id }}</option>
          </select>
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
        <label for="employee">Select Employee:</label>
        <select id="employee" name="employee" v-model="selectedEmployee">
          <option value="">-- Select an employee --</option>
          <option v-for="employee in employees" :value="employee.email">{{ employee.name }}</option>
        </select>
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
      <h2>Search for Service Appointments by Customer Email</h2>
      <div>
        <label for="email">Select Customer Email:</label>
        <select id="email" name="email" v-model="findByCustomerEmailInput">
          <option disabled value="">Please select an email</option>
          <option v-for="email in customerEmails" :key="email" :value="email">{{ email }}</option>
        </select>
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
        <select id="appointment-id" name="appointment-id" v-model="editByEmployeeEmailIdInput">
          <option disabled value="">Please select an ID</option>
          <option v-for="id in appointmentIds" :key="id" :value="id">{{ id }}</option>
        </select>
      </div>
      <div>
        <label for="employee-email">Employee Email:</label>
        <select id="employee-email" name="employee-email" v-model="editByEmployeeEmailInput">
          <option disabled value="">Please select an email</option>
          <option v-for="email in employees" :key="email" :value="email">{{ email }}</option>
        </select>
      </div>
      <button type="button" @click="updateServiceAppointmentWithNewEmployee">Update Appointment's Employee</button>
      <p class="success">{{ successMsgEditWithEmployeeEmail }}</p>
      <p class="error">{{ errorMsgEditWithEmployeeEmail }}</p>
    </div>

    <div>
      <h2>Update an Existing Service Appointment's details</h2>
      <div>
        <div>
          <label for="id">Appointment ID:</label>
          <select id="id" v-model="editIdInput">
            <option v-for="id in appointmentIds" :value="id">{{ id }}</option>
          </select>
        </div>
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
        <select id="serviceName" name="serviceName" v-model="editServiceNameInput">
          <option v-for="service in serviceNames" :key="service" :value="service">{{ service }}</option>
        </select>
      </div>
      <button type="button" :disabled="!editIdInput || !editDateInput || !editStartTimeInput || !editServiceNameInput"
        @click="editServiceAppointment">Edit Appointment</button>
      <p class="success">{{ successMsgEdit }}</p>
      <p class="error">{{ errorMsgEdit }}</p>
    </div>


    <div>
      <h2>Cancel a Service Appointment</h2>
      <div>
        <label for="cancelId">Appointment ID:</label>
        <select id="cancelId" v-model="cancelIdInput">
          <option v-for="id in appointmentIds" :value="id">{{ id }}</option>
        </select>
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


<script type="module">
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
      appointmentIds: [],
      appointment: {},
      selectedEmployee: '',
      employees: [],
      customerEmails: [],
      serviceNames: [],
      selectedId: '',
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
        this.serviceAppointments = response.data;
        this.appointmentIds = response.data.map((appointment) => appointment.id);
        this.errorMsgAll = ''
      })
      .catch((error) => {
        console.log(error.response.data);
        this.errorMsgAll = error.response.data;
      });

      axiosClient
      .get('/employees')
      .then((response) => {
        this.employees = response.data.map((employee) => employee.email);
        this.errorMsgAll = ''
      })
      .catch((error) => {
        console.log(error.response.data)
      });

      axiosClient
      .get('/customers')
      .then((response) => {
        this.customers = response.data.map((customer) => customer.email);
        this.errorMsgAll = ''
      })
      .catch((error) => {
        console.log(error.response.data)
      });

      axiosClient
      .get('/service')
      .then((response) => {
        this.serviceNames = response.data.map((service) => service.serviceName);
        this.errorMsgAll = ''
      })
      .catch((error) => {
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