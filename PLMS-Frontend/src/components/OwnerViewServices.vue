<template>
    <div>
      <div>
        <h2>Create a Service</h2>
        <div>
          <label for="serviceName">Service name:</label>
          <input type="text" id="serviceNameCreation" name="serviceNameCreation" v-model="createServiceNameInput">
        </div>
        <div>
          <label for="cost">Cost:</label>
          <input type="text" id="costCreation" name="costCreation" v-model="createCostInput">
        </div>
        <div>
          <label for="lengthInHoursCreation">Length in hours:</label>
          <input type="text" id="lengthInHoursCreation" name="lengthInHoursCreation" v-model="createLengthInput">
        </div>
        <button type="button" :disabled="!createServiceNameInput || !createCostInput || !createLengthInput"
          @click="createService()">Create Service</button>

        <p class="error">{{ errorMsgCreate }}</p>
      </div>

      <div>
        <h2>Edit a Service</h2>
        <div>
        <label for="serviceNameEdit">Service name:</label>
          <input type="text" id="serviceNameEdit" name="serviceNameEdit" v-model="editServiceNameInput">
        </div>
        <div>
          <label for="costEdit">Cost:</label>
          <input type="text" id="costEdit" name="costEdit" v-model="editCostInput">
        </div>
        <div>
          <label for="lengthInHoursEdit">Length in hours:</label>
          <input type="text" id="lengthInHoursEdit" name="lengthInHoursEdit" v-model="editLengthInput">
        </div>

        <button type="button" :disabled="!editServiceNameInput || !editCostInput || !editLengthInput"
          @click="editService()">Edit Service</button>

        <p class="error">{{ errorMsgEdit }}</p>
      </div>
  
      <div>
        <h2>Delete a Service </h2>
        <div>
          <label for="serviceNameDelete">Service name:</label>
          <input type="text" id="serviceNameDelete" name="serviceNameDelete" v-model="deleteServiceInput">
        </div>
        <button type="button" :disabled="!deleteServiceInput" @click="deleteService()">Delete Service</button>

        <p class="error">{{ errorMsgDelete }}</p>
      </div>
  
  
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
    name: 'OwnerViewServices',
    data() {
      return {
        services: [],

        createServiceNameInput: '',
        createCostInput: '',
        createLengthInput: '',

        editServiceNameInput: '',
        editCostInput: '',
        editLengthInput: '',

        deleteServiceInput: '',

        errorMsgCreate: '',
        errorMsgEdit: '',
        errorMsgDelete: '',
        errorMsgAll: ''

      };
    },
    created() {
      axiosClient.get('/service')
        .then((response) => {
          this.services = response.data;
          this.errorMsgAll = '';
        })
        .catch((error) => {
          console.log(error.response.data);
          this.errorMsgAll = error.response.data;
        });
    },

    methods: {

        createService() {
            const request = {
            serviceName: this.createServiceNameInput,
            cost: this.createCostInput,
            lengthInHours: this.createLengthInput
            };
            axiosClient.post("/service/create", request)
            .then((response) => {
                this.createServiceNameInput = '';
                this.createCostInput = '';
                this.createLengthInput = '';
                this.services.push(response.data)
                this.errorMsgCreate = "Service created successfully";
            })
            .catch(error => {
                console.log(error.response.data);
                this.errorMsgCreate = error.response.data;
            })
        },

        editService() {
            const request = {
            serviceName: this.editServiceNameInput,
            cost: this.editCostInput,
            lengthInHours: this.editLengthInput
            };
            axiosClient.put("/service/", request)
            .then((response) => {
                this.editServiceNameInput = '';
                this.editCostInput = '';
                this.editLengthInput = '';
                window.location.reload();
                this.errorMsgEdit = "Service updated successfully";
            })
            .catch((error) => {
                console.log(error.response.data);
                this.errorMsgEdit = error.response.data;
            })
        },
  
        deleteService() {
            axiosClient.delete(`/service/${this.deleteServiceInput}`)
            .then((response) => {
                window.location.reload();
                this.errorMsgDelete = "Service deleted successfully";
            })
            .catch((error) => {
                console.log(error.response.data);
                this.errorMsgDelete = error.response.data;
            })
        }
        
    },
    
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