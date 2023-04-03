<template>
  <div class="login">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <img class="img-fluid mt-5 mb-3"  width="50%" src="@/assets/logo-png.png">

    <form style="margin-top: 2%">
      <!-- Email input -->
      <div class="form-outline mb-4">
          <label class="form-label" for="email">Email</label>
          <input v-model="email" type="email" id="email" class="form-control" style="width: 50%; margin-left: 25%" placeholder="john.doe@address.com"/>

      </div>


        <!-- Password input -->
      <div class="form-outline mb-4">
        <label class="form-label" for="password">Password</label>
        <input v-model="password" type="password" id="password" class="form-control"  style="width: 50%; margin-left: 25%" placeholder="*********"/>
      </div>

      <div class="form-check form-check-inline">
        <input v-model="user" class="form-check-input" type="radio" name="user" id="owner" value="Owner">
        <label class="form-check-label" for="owner">Owner</label>
      </div>

      <div class="form-check form-check-inline">
        <input v-model="user" class="form-check-input" type="radio" name="user" id="customer" value="Customer">
        <label class="form-check-label" for="customer">Customer</label>
      </div>
      <div class="form-check form-check-inline">
        <input v-model="user" class="form-check-input" type="radio" name="user" id="employee" value="Employee">
        <label class="form-check-label" for="employee">Employee</label>
      </div>
    </form>


    <!-- Submit button -->
    <button v-bind:disabled="createUserButtonDisabled" style="width: 50%; margin-top: 1%; margin-left: 25%" @click="getUser()" type="button" class="btn btn-primary btn-block mb-4">Sign in</button>

    <div class="text-center">
      <p>Not a customer? <a href="http://localhost:8087/#/create-customer">Sign up</a></p>
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
  name: 'LoginUser',
  data() {
    return {
      email: '',
      password: '',
      user: '',
      errorMsg: '',
      logged_user: []
    };
  },
  methods: {
    getUser() {
      this.email = document.getElementById("email").value;
      this.password = document.getElementById("password").value;
      this.user = document.querySelector('input[name="user"]:checked').value
      const request =  {email: this.email, password: this.password, user: this.user};
      axiosClient.get("/login", request)
      .then((response) => {
        alert("Success now logged in as " + this.user)
        this.logged_user = response
      })
        .catch((err) => {
          console.log(err)
          this.errorMsg = err
        })
    }

  },
  computed: {
    createUserButtonDisabled() {
      return !this.email.trim() || !this.password.trim() || !this.user.trim()
    }
  }
}
</script>

<style>


</style>
