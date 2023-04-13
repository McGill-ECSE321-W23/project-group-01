<template>
  <div id="guestpass_creation_search">


    <div id="guestpasscreation">
      <h2>Create a Guest Pass</h2>
      <table align="center" style="border-collapse: collapse;">
        <tr>
          <td style="border: none;"></td>
          <td style="border: none;">Floor number</td>
          <td style="border: none;">Large</td>
          <td style="border: none;">License plate</td>
          <td style="border: none;"></td>
        </tr>
        <tr>
          <td style="border: none;"></td>
          <td style="border: none;">
            <!-- <input type="number" placeholder="floor number" id="floor" v-model="floorNumber"> -->
            <select class="custom-select" required v-model="floorNumber" @change="getSpotNumbers()">
              <!-- <option v-for="floor in floors" :key="floor.floorNumber" :value="floor.floorNumber">{{floor.floorNumber}}</option> -->
              <option v-for="(floorNumber, index) in floorNumbers.sort((a, b) => a - b)" :key="index">{{ floorNumber }}
              </option>
            </select>
          </td>
          <td style="border: none;">
            <input type="checkBox" id="isLarge" v-model="isLarge" @click="onIsLargeChange()">
          </td>
          <td style="border: none;">
            <!-- <input type="text" placeholder="spot number" id="spot" v-model="spotNumber" v-bind:disabled="createGuestPassButtonDisabled"> -->
            <select v-model="spotNumber" class="form-control" :disabled="selectSpotDisabled" @click="getSpotNumbers">
              <option value="" disabled>Select a spot</option>
              <option v-for="(spot, index) in spotNumbers" :key="index">{{ spot }}</option>
            </select>
          </td>
          <td style="border: none;"></td>
        </tr>
        <tr>
          <td style="border: none;"></td>
          <td style="border: none;">License Plate</td>
          <td colspan="2" style="border: none;">15 minute increments</td>
          <td style="border: none;"></td>
        </tr>
        <tr>
          <td style="border: none;"></td>
          <td style="border: none;">
            <input type="text" placeholder="license plate" id="plate" v-model="licensePlate">
          </td>
          <td style="border: none;" colspan="2">
            <input type="number" placeholder="15 minute increments" id="increments"
              v-model="numberOfFifteenMinuteIncrements">
          </td>
          <td style="border: none;"></td>
        </tr>
        <tr>
          <td style="border: none;"></td>
          <td style="border: none;"></td>
          <td style="border: none;"></td>
          <td style="border: none;"></td>
          <td style="border: none;"></td>
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
      <table align=center>
        <tr>
          <td>Guest Pass ID</td>
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
const frontendUrl = config.dev.host + ':' + config.dev.port;

const AXIOS = axios.create({
  baseURL: config.dev.backendBaseUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl },
})

export default {
  name: 'GuestPassCreationAndSearch',
  data() {
    return {
      floors: [],
      floorNumbers: [],
      spotNumber: '',
      confirmationCodeCreation: '',
      licensePlate: '',
      floorNumber: '',
      numberOfFifteenMinuteIncrements: '',
      errorMsgCreation: '',
      errorMsgIdentification: '',
      isLarge: false,
      confirmationCodeIdentification: '',
      id: '',

      spotNumbersMap: {},
      spotNumbers: []

    };
  },

  created() {
    // get all the guest passes
    AXIOS.get('/guestPass')
      .then((response) => {
        console.log(response.data);
      })
      .catch((err) => {
        console.log(err);
      });

    // get all the floors
    AXIOS.get("/floor")
      .then(response => {
        this.floors = response.data
        // this.floorNumbers = response.data.map((floor) => floor.floorNumber)
        this.floorNumbers = []

        // Get all floors and subsequent floor numbers 
        for (const floor of this.floors) {

          if (!floor.memberOnly) {
            const floorNumber = floor.floorNumber;
            const largeSpotCapacity = floor.largeSpotCapacity;
            const smallSpotCapacity = floor.smallSpotCapacity;

            // Generate spot numbers for large and for small spots
            const largeSpots = [];
            for (let i = 1; i <= largeSpotCapacity; i++) {
              largeSpots.push(`${floorNumber}L${i}`);
            }

            const smallSpots = [];
            for (let i = 1; i <= smallSpotCapacity; i++) {
              smallSpots.push(`${floorNumber}S${i}`);
            }

            this.floorNumbers.push(floorNumber);

            // Add the spot numbers to the hashmap
            this.spotNumbersMap[floorNumber] = {
              large: largeSpots,
              small: smallSpots
            };
          }
        }
        console.log('spotnumbermap', this.spotNumbersMap)
      })
      .catch(error => {

        alert(error.data)
      })
  },

  methods: {
    // book a guest pass
    createGuestPass() {
      this.confirmationCode = this.generateConfirmationCode()
      const request = {
        spotNumber: this.spotNumber, confirmationCode: this.confirmationCodeCreation, licensePlate: this.licensePlate,
        floorNumber: this.floorNumber, numberOfFifteenMinuteIncrements: this.numberOfFifteenMinuteIncrements,
        isLarge: document.getElementById(`isLarge`).checked
      };
      AXIOS.post('/guestPass', request)
        .then((response) => {
          // on success
          this.errorMsgCreation = `Guest Pass Created!  \t ID: ${response.data.id}  \n Confirmation Code: ${this.confirmationCode} `
        })
        .catch((err) => {
          // on failure
          this.errorMsgCreation = `Failed to create pass: ${err.response.data}`;
        })
    },

    // find a guest pass. Need to enter bot ID and confirmation code
    findGuestPass() {
      AXIOS.get(`/guestPass/${this.id}`)
        .then((response) => {
          const pass = response.data
          if (pass.confirmationCode === this.confirmationCodeIdentification) {
            this.errorMsgIdentification = `Guest Pass found!: ${pass}`
          }
          else {
            console.error("The confirmation code entered is incorrect")
          }

        })
        .catch((err) => {
          this.errorMsgIdentification = `Failed to find pass: ${err.response.data}`;
        })

    },

    // load spot numbers based on selection of large or small spots
    onIsLargeChange() {
      this.isLarge = !this.isLarge;
      console.log('changed', this.isLarge)
      this.getSpotNumbers();
    },

    // generate the spot numbers based on the selected spot size
    getSpotNumbers() {
      this.spotNumber = ''
      const floorNumber = this.floorNumber.toString()
      const spotType = this.isLarge ? "large" : "small"
      if (this.spotNumbersMap[floorNumber] && this.spotNumbersMap[floorNumber][spotType]) {
        // display the corresponding list of spot numbers for the selected size
        this.spotNumbers = this.spotNumbersMap[floorNumber][spotType]
        console.log(this.spotNumbers)
      } else {
        console.log(`Spot numbers not found for floor ${floorNumber} and spot type ${spotType}.`)
      }
    },

    // random generation of confirmation code logic
    generateConfirmationCode() {
      console.log('test')
      const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      let code = '';

      // Generate the first two letters
      for (let i = 0; i < 2; i++) {
        code += letters.charAt(Math.floor(Math.random() * letters.length));
      }

      // Add the underscore
      code += '_';

      // Generate the six numbers
      for (let i = 0; i < 6; i++) {
        code += Math.floor(Math.random() * 10);
      }
      console.log(code)
      return code;
    },
  },

  computed: {
    createGuestPassButtonDisabled() {
      return !this.spotNumber.trim() || !this.licensePlate.trim() || !this.floorNumber.trim() || !this.numberOfFifteenMinuteIncrements.trim();
    },

    findGuestPassButtonDisabled() {
      return !this.id.trim() || !this.confirmationCodeIdentification.trim();
    },
    selectSpotDisabled() {
      return (this.floorNumber == '')
    },

  }

}


</script>



<style>
td,
th {
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
}</style>