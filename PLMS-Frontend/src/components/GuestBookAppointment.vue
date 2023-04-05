<template>
    <div>
        <h1>Book an Appointment</h1>
        <form @submit.prevent="createAppointment">
            <div>
                <label>Date:</label>
                <input type="date" id="date" v-model="date" required>
            </div>
            <div>
                <label>Start Time:</label>
                <input type="time" id="start-time" v-model="startTime" required>
            </div>
            <div>
                <label>Service:</label>
                <select id="service" v-model="serviceName" required>
                    <option value="">Select a service</option>
                    <option v-if="services" v-for="service in services" :value="service.serviceName">{{ service.serviceName }}</option>
                </select>
                <p class="error">{{ errorServiceMsg }}</p>
            </div>
            <button type="submit">Create</button>
        </form>
        <p class="error">{{ errorCreateMsg }}</p>
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
        date: '',
        startTime: '',
        serviceName: '',
        services: [],
        errorServiceMsg: '',
        errorCreateMsg: '',
      };
    },
    created() {
        this.fetchServices();
    },
    methods: {

        async fetchServices(){
            try {
                const response = await axiosClient.get('/service')
                this.services = response.data;
                console.log(response);
            }catch (error) {
                console.error(error);
                this.errorServiceMsg = error.response.data;
            }
        },

        async createAppointment() {
            const appointment = {
                date: this.date,
                startTime: this.startTime + ':00',
                serviceName: this.serviceName,
            };

            try {
                console.log(appointment);
                const response = await axiosClient.post('/serviceAppointment', appointment);
                this.date = '';
                this.startTime = '';
                this.serviceName = '';
            }catch (error) {
                this.errorCreateMsg = error.response.data;
                console.error(error);
            }

        },
    },
};
</script>

<style>
.error{
  color: red;
}
</style>