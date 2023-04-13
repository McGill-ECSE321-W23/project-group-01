<template>
  <div id="guestpass_creation_search">
    <div id="guestpasscreation">
      <h2>Create a Guest Pass</h2>
      <table align = center>
        <tr>
          <td>Spot number</td>
          <td>Confirmation code</td>
          <td>License plate</td>
        </tr>
        <tr>
          <td>
              <input type="text" placeholder="spot number" id="spot" v-model="spotNumber">
          </td>
          <td>
              <input type="text" placeholder="confirmation code" id="code" v-model="confirmationCodeCreation">
          </td>
          <td>
              <input type="text" placeholder="license plate" id="plate" v-model="licensePlate">
          </td>
        </tr>
        <tr>
          <td>Floor number</td>
          <td>15 minute increments</td>
          <td>Large</td>
        </tr>
        <tr>
          <td>
              <input type="text" placeholder="floor number" id="floor" v-model="floorNumber">
          </td>
          <td>
              <input type="text" placeholder="15 minute increments" id="increments" v-model="numberOfFifteenMinuteIncrements">
          </td>
          <td>
              <input type="checkBox" id="isLarge" v-model="isLarge">
          </td>
        </tr>
      </table>
    </div>

    <br>

    <p>
      <button v-bind:disabled="createGuestPassButtonDisabled" @click="createGuestPass()">Create Pass</button><br>
    </p>

    <p class="error">{{ errorMsgCreation }}</p>

    <div id="guestpass_search">
      <h2>Find Guest Pass</h2>
      <table align = center>
        <tr>
          <td>Guest Pass id</td>
          <td>Confirmation code</td>
        </tr>
        <tr>
          <td>
            <input type="text" placeholder="guest pass id" v-model="id">
          </td>
          <td>
              <input type="text" placeholder="confirmation code" v-model="confirmationCodeIdentification">
          </td>
        </tr>
      </table>

    <br>
    
    <p>
      <button v-bind:disabled="findGuestPassButtonDisabled" @click="findGuestPass()">Find Pass</button>
    </p>

    <p class="error">{{ errorMsgIdentification }}</p>

    </div>


  </div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')

const AXIOS = axios.create({
  baseURL: config.dev.backendBaseUrl
})

export default {
  name: 'GuestPassCreationAndSearch',
  data(){
    return{
      spotNumber: '',
      confirmationCodeCreation: '',
      licensePlate: '',
      floorNumber: '',
      numberOfFifteenMinuteIncrements: '',
      errorMsgCreation: '',
      errorMsgIdentification: '',

      confirmationCodeIdentification: '',
      id: '',

    };
  },

  created(){
    AXIOS.get('/guestPass')
    .then((response) => {
      console.log(response.data);
    })
    .catch((err) => {
      console.log(err);
    });

  },

  methods: {
    createGuestPass(){
      const request = {spotNumber: this.spotNumber, confirmationCode: this.confirmationCodeCreation, licensePlate: this.licensePlate,
      floorNumber: this.floorNumber, numberOfFifteenMinuteIncrements: this.numberOfFifteenMinuteIncrements,
    isLarge: document.getElementById(`isLarge`).checked};
    AXIOS.post('/guestPass', request)
    .then((response) => {
      this.errorMsgCreation = "Guest Pass created!"
    })
    .catch((err) => {
      this.errorMsgCreation = `Failed to create pass: ${err.response.data}`;
    })
    },

    findGuestPass(){
      AXIOS.get(`/guestPass/${this.id}`)
      .then((response) => {
        const pass = response.data
        if(pass.confirmationCode === this.confirmationCodeIdentification){
          this.errorMsgIdentification = `Guest Pass found!: ${pass}`
        }
        else{
          console.error("The confirmation code entered is incorrect")
        }
        
      })
      .catch((err) => {
        this.errorMsgIdentification = `Failed to find pass: ${err.response.data}`;
      })

    },

    onIsLargeChange() {
      this.isLarge = !this.isLarge;
      this.createSpotNumbers();
    },
    createSpotNumbers(){
      const isLarge = this.isLarge
      this.spotNumber = ''
      this.spotNumbers  = []
      const floor = null

      console.log(isLarge)
      
      axiosClient.get("/floor/" + this.floorNumber).then((response) => {
                    const numSpots = isLarge ? response.data.largeSpotCapacity : response.data.smallSpotCapacity;
                    const spotType = isLarge ? 'L' : 'S';
                    for (let i = 1; i <= numSpots; i++) {
                      this.spotNumbers.push(`${this.floorNumber}${spotType}${i}`);
                    }
                    console.log(this.spotNumbers)

                }).catch((err) => {
                
                alert(err.response.data)
                })
    },
  },

  computed: {
    createGuestPassButtonDisabled() {
      return !this.spotNumber.trim() || !this.confirmationCodeCreation.trim() || !this.licensePlate.trim() || !this.floorNumber.trim() || !this.numberOfFifteenMinuteIncrements.trim();
    },

    findGuestPassButtonDisabled(){
      return !this.id.trim() || !this.confirmationCodeIdentification.trim();
    }
  
  }
  
}


</script>



<style>
    td, th {
  border: 1px solid black;
  padding: 0.5em;
}
#home-page {
  display: flex;
  flex-direction: column;
  padding: 5%;
  align-items: stretch;
}
.error {
  color: red;
}
label {
        display: inline-block;
        width: 150px;
        text-align: right;
      }
</style>