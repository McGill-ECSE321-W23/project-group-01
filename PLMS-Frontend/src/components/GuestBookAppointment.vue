<template>
    <div>
        <h1>Book an Appointment</h1>
        <form @submit.prevent="createAppointment">
            <div>
                <label for="date">Date:</label>
                <input type="date" id="date" v-model="date" required>
            </div>
            <div>
                <label for="start-time">Start Time:</label>
                <input type="time" id="start-time" v-model="startTime" required>
            </div>
            <div>
                <label for="service">Service:</label>
                <select id="service" v-model="service" required>
                    <option value="">Select a service</option>
                    <option v-for="service in services" :value="service.serviceName">{{ service.serviceName }}</option>
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
                console.error(response);
            }catch (error) {
                console.error(error);
                this.errorServiceMsg = error.response.data;
            }
        },

        async createAppointment() {
            const appointment = {
                date: this.date,
                startTime: this.startTime,
                serviceName: this.serviceName,
            };

            try {
                const response = await axiosClient.post('/serviceAppointment', appointment);
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