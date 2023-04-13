<template>
  <div class="signup">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <img class="img-fluid mt-5 mb-3"  width="30%" src="@/assets/logo-png.png">

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

      <div class="form-outline mb-4">
        <label class="form-label" for="name">Name</label>
        <input v-model="name" type="text" id="name" class="form-control" style="width: 50%; margin-left: 25%" placeholder="John Doe"/>
      </div>
    </form>

    <button v-bind:disabled="createUserButtonDisabled" style="width: 50%; margin-top: 1%; margin-left: 25%" @click="createUser()" type="button" class="btn btn-primary btn-block mb-4"> Sign up </button>

    <div class="text-center">
      <p>Already have an account? <a href="http://localhost:8087/#/login-user">Login</a></p>
    </div>

  </div>
</template>


<script>
import axios from 'axios';
const config = require('../../../config');
const frontendUrl = config.dev.host + ':' + config.dev.port;
const axiosClient = axios.create({
  // Note the baseURL, not baseUrl
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  name: 'SignUp',
  data() {
    return {
      email: '',
      password: '',
      name: '',
      errorMsg: '',
      logged_user: []
    };
  },
  methods: {
    createUser() {
      this.email = document.getElementById("email").value;
      this.password = document.getElementById("password").value;
      this.name = document.getElementById("name").value;
      const request =  {email: this.email, password: this.password, name: this.name};
      axiosClient.post("/customer/create", request)
        .then((response) => {
          alert("Your account with email " + this.email + " has been created successfully")
          this.logged_user = response
          window.location.href = "http://localhost:8087/#/login-user";

        })
        .catch((err) => {
          this.errorMsg = `Failed to create: ${err.response.data}`
          alert(this.errorMsg)

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


</style>
