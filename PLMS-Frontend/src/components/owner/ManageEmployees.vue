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
        <button type="button" class="btn btn-success btn-sm">Create Employee</button>
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
              <td>foo</td>
              <td>bar</td>
              <td>foobar</td>
              <td>$$</td>
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
  </div>
</template>

<script>

export default {
    data() {
        return {
            employees: [],
        
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
    }
}
</script>

