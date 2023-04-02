<template>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <form>
    <!-- Email input -->
    <div class="form-signin">
      <input type="email" id="email" class="form-control" placeholder="john.doe@address.com"/>
    </div>

    <!-- Password input -->
    <div class="form-signin">
      <input type="password" id="password" class="form-control" placeholder="*********"/>
    </div>

    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="user" id="owner" value="Owner">
      <label class="form-check-label" for="owner">Owner</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="user" id="customer" value="Customer">
      <label class="form-check-label" for="customer">Customer</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="user" id="employee" value="Employee">
      <label class="form-check-label" for="employee">Employee</label>
    </div>

    <!-- Submit button -->
    <button v-bind:disabled="createUserButtonDisabled" @click="LoginUser()" type="button" class="btn btn-primary btn-block mb-4">Sign in</button>

  </form>
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
      UserEmail: document.getElementById("email").value,
      UserPassword: document.getElementById("password").value,
      UserType: document.querySelector('input[name="user"]:checked').value
    };
  },
  methods: {
    getUser() {

    }

  },
  computed: {
    createUserButtonDisabled() {
      return !this.UserEmail.trim() || !this.UserPassword.trim() || !this.UserType.trim()
    }
  }
}
</script>

<style scoped>

</style>
