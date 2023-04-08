<!--<template>
    <div>  Kimo was here </div>
</template>

  
<script>
        
</script>

<style>

</style>
-->

<template>
  <div class="container">
    <div class="row">
      <div class="col-sm-10">
        <h1>Employees</h1>
        <hr><br><br>
        <button type="button" class="btn btn-success btn-sm" v-b-modal.employee-modal>Create Employee</button>
        <br><br>
        <table class="table table-hover">
          <thead>
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Email</th>
              <th scope="col">JobTitle</th>
              <th scope="col">Hourly Wage</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr  v-for="(employee, index) in employees" :key="index" > 
                <td>{{employee.name}}</td>
                <td>{{employee.email}}</td>
                <td>{{employee.jobTitle}}</td>
                <td>{{employee.hourlyWage}}</td>
                <td>
                <div class="btn-group" role="group">
                  <button type="button" class="btn btn-warning btn-sm">Update</button>
                  <button type="button" class="btn btn-danger btn-sm">Delete</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
<b-modal ref="addEmployeeModal"
         id="employee-modal"
         title="Add a new employee"
         hide-footer>
  <b-form @submit="onSubmit" @reset="onReset" class="w-100">
    <b-form-group id="form-name-group"
                  label="Name:"
                  label-for="form-name-input">
      <b-form-input id="form-name-input"
                    type="text"
                    v-model="addEmployeeForm.name"
                    required
                    placeholder="Enter name">
      </b-form-input>
    </b-form-group>
    <b-form-group id="form-email-group"
                  label="Email:"
                  label-for="form-email-input">
      <b-form-input id="form-email-input"
                    type="email"
                    v-model="addEmployeeForm.email"
                    required
                    placeholder="Enter email">
      </b-form-input>
    </b-form-group>
    <b-form-group id="form-password-group"
                  label="Password:"
                  label-for="form-password-input">
      <b-form-input id="form-password-input"
                    type="password"
                    v-model="addEmployeeForm.password"
                    required
                    placeholder="Enter password">
      </b-form-input>
    </b-form-group>
    <b-form-group id="form-job-title-group"
                  label="Job Title:"
                  label-for="form-job-title-input">
      <b-form-input id="form-job-title-input"
                    type="text"
                    v-model="addEmployeeForm.jobTitle"
                    required
                    placeholder="Enter job title">
      </b-form-input>
    </b-form-group>
    <b-form-group id="form-hourly-wage-group"
                  label="Hourly Wage:"
                  label-for="form-hourly-wage-input">
      <b-form-input id="form-hourly-wage-input"
                    type="number"
                    v-model="addEmployeeForm.hourlyWage"
                    required
                    placeholder="Enter hourly wage">
      </b-form-input>
    </b-form-group>
    <b-button type="submit" variant="primary">Submit</b-button>
    <b-button type="reset" variant="danger">Reset</b-button>
  </b-form>
</b-modal>

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
    data() {
        return {
            employees: [],
            addEmployeeForm: {
                name: '',
                email: '',
                password: '',
                jobTitle: '',
                hourlyWage: '',
            }
        };
    },
    created() {
        // Fetch all customers on component mount
        this.fetchEmployees();
    },
    methods: {
        async fetchCustomers() {
            try {
                const response = await axiosClient.get('/employees');
                this.employees = response.data;
                this.gettingAllEmployeesErrorMsg = '';
            } catch (error) {
                this.gettingAllEmployeesErrorMsg = error.response.data;
            }
        },
        createEmployee(request) {
            
            axiosClient.post("/customer/create", request)
                .then((response) => {
                alert("Employee account with email " + this.email + " has been created successfully")
                this.logged_user = response

                })
                .catch((err) => {
                this.errorMsg = `Failed to create: ${err.response.data}`
                alert(this.errorMsg)

                });
        },

        initForm() {
        this.addEmployeeForm.name = '';
        this.addEmployeeForm.email = '';
        this.addEmployeeForm.password = '';
        this.addEmployeeForm.jobTitle = '';
        this.addEmployeeForm.hourlyWage = '';
        },

        onSubmit(evt) {
        evt.preventDefault();
        this.$refs.addEmployeeModal.hide();
        const request = {
            name: this.addEmployeeForm.name,
            email: this.addEmployeeForm.email,
            password: this.addEmployeeForm.password,
            jobTitle: this.addEmployeeForm.jobTitle,
            hourlyWage: this.addEmployeeForm.hourlyWage,
        };
        this.addEmployee(request);
        this.$refs.addEmployeeModal.hide();
        this.initForm();
        },
        onReset(evt) {
        evt.preventDefault();
        this.$refs.addEmployeeModal.hide();
        this.initForm();
        },

    }
}
</script>

