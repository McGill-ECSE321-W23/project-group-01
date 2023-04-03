<template>
  <div id="settings">
    <div id="parkinglot_settings">
      <h2 class="parkinglot section title">Parking Lot Settings</h2>
      <form>
        <label for="opening">Opening Hours</label>
        <input type="text" id="opening" v-model="newOpeningTime"> <br>
        <label for="closing">Closing Hours</label>
        <input type="text" id="closing" v-model="newClosingTime"> <br>
        <label for="smallspot">Small spot Fee</label>
        <input type="text" id="smallspot" v-model="newSmallSpotFee"> <br>
        <label for="largespot">Large spot Fee</label>
        <input type="text" id="largespot" v-model="newLargeSpotFee"> <br>
        <label for="smallmonthly">Small spot monthly flat fee</label>
        <input type="text" id="smallmonthly" v-model="newSmallSpotMonthlyFlatFee"> <br>
        <label for="largemonthly">Large spot monthly flat fee</label>
        <input type="text" id="largemonthly" v-model="newLargeSpotMonthlyFlatFee"> <br>
      </form>
    </div>
    <button v-bind:disabled="createParkingButtonDisabled" @click="createOrUpdateParkingLot()">Confirm</button><br>
    <div id="floor_settings">
      <h2 class="floor section title">Floor Settings</h2>
      <table>
      <tr>
        <th>Floor number</th>
        <th>Small spot capacity</th>
        <th>Large spot capacity</th>
        <th>Member only</th>
      </tr>
      <tr v-for="f in floors">
        <td>{{ f.floorNumber }}</td>
        <td>{{ f.smallSpotCapacity }}</td>
        <td>{{ f.largeSpotCapacity }}</td>
        <td>{{ f.memberOnly }}</td>
        <td><button id="deletefloor" @click="deletefloor(f.floorNumber)">Delete</button></td>
      </tr>
      <tr>
        <td><input type="text" id="floornumber" v-model="newFloorNumber"></td>
        <td><input type="text" id="smallcapacity" v-model="newSmallSpotCapacity"></td>
        <td><input type="text" id="largecapacity" v-model="newLargeSpotCapacity"></td>
        <td><input type="checkbox" id="memberonly" v-model="newMemberOnly"></td>
      </tr>
      <button id="addfloor" @click="addFloor()">Add Floor</button>
    </table>
    </div>
    
    <p class="error">{{ errorMsg }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import config from '../../config';
const axiosClient = axios.create({
  // Note the baseURL, not baseUrl
  baseURL: config.dev.backendBaseUrl
});
export default {
  name: 'ParkingLotSettings',
  data() {
    return {
      floors: [],
      parkinglot: {},
      newOpeningTime: '',
      newClosingTime: '',
      newLargeSpotFee: '',
      newSmallSpotFee: '',
      newSmallSpotMonthlyFlatFee: '',
      newLargeSpotMonthlyFlatFee: '',
      newFloorNumber: '',
      newSmallSpotCapacity: '',
      newLargeSpotCapacity: '',
      newMemberOnly: false,
      errorMsg: '',
    };
  },
  created() {
    axiosClient.get('/floor')
      .then((response) => {
        this.floors = response.data;
        console.log(this.floors);
      })
      .catch((err) => {
        // this.errorMsg = err;
      })
    axiosClient.get('/parkingLot')
    .then((response) => {
      this.parkinglot = response.data;
      this.newOpeningTime = this.parkinglot.openingTime;
      this.newClosingTime = this.parkinglot.closingTime;
      this.newSmallSpotFee = this.parkinglot.smallSpotFee;
      this.newLargeSpotFee = this.parkinglot.largeSpotFee;
      this.newSmallSpotMonthlyFlatFee = this.parkinglot.smallSpotMonthlyFlatFee;
      this.newLargeSpotMonthlyFlatFee = this.parkinglot.largeSpotMonthlyFlatFee;
    })
    .catch((err) => {
      // this.errorMsg = err;
    })
  },
  methods: {
    createOrUpdateParkingLot() {
      const request = {openingTime: this.newOpeningTime, closingTime: this.newClosingTime, largeSpotFee: this.newLargeSpotFee, smallSpotFee: this.newSmallSpotFee, smallSpotMonthlyFlatFee: this.newSmallSpotMonthlyFlatFee, largeSpotMonthlyFlatFee: this.newLargeSpotMonthlyFlatFee};
      axiosClient.put('/parkingLot/update', request)
        .then((response) => {
          const parkingLot = response.data;
          this.errorMsg = '';
        })
        .catch((err) => {
          if(err.response.data === "Parking Lot not found."){
            this.createParkingLot(request);
          } else if(typeof(err.response.data) === 'string'){
            this.errorMsg = `${err.response.data}`;
          } else{
            this.errorMsg = `Opening and closing hours have to be of the format: 'hh:mm:ss'.`;
          }
        })
    },
    createParkingLot(request) {
      axiosClient.post('/parkingLot/creation', request)
        .then((response) => {
          const parkingLot = response.data;
          this.errorMsg = '';
        })
        .catch((err) => {
          this.errorMsg = `Failed to create: ${err.response.data}`;
        })
    },
    addFloor(){
      const request = {floorNumber: this.newFloorNumber, smallSpotCapacity: this.newSmallSpotCapacity, largeSpotCapacity: this.newLargeSpotCapacity, isMemberOnly: this.newMemberOnly};
      axiosClient.post('/floor', request)
        .then((response) => {
          const floor = response.data;
          this.floors.push(floor);
          this.newFloorNumber = '';
          this.newSmallSpotCapacity = '';
          this.newLargeSpotCapacity = '';
          this.newMemberOnly = false;
          this.errorMsg = '';
        })
        .catch((err) => {
          this.errorMsg = `Failed to create: ${err.response.data}`;
        })
    },
    deletefloor(floorNumber){
      axiosClient.delete(`/floor/${floorNumber}`)
      .then((response) => {
          const floor = response.data;
          this.floors.push(floor);
          this.newFloorNumber = '';
          this.newSmallSpotCapacity = '';
          this.newLargeSpotCapacity = '';
          this.newMemberOnly = false;
          this.errorMsg = '';
        })
        .catch((err) => {
          this.errorMsg = `Failed to create: ${err.response.data}`;
        })
    }
  },
  computed: {
    createParkingButtonDisabled() {
      return !this.newClosingTime.trim() || !this.newOpeningTime.trim();
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