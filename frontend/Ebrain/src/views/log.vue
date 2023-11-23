<template>
  <div>
    <h1>Log</h1>
    <el-button @click="getLog" >Refresh Log</el-button>
    <el-button @click="clearLog" >Clean Log</el-button>

    <div class="log-content">
      <pre>{{ log }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      log: []
    };
  },
  methods: {
    getLog() {
      fetch('http://localhost:8081/getLog')
          .then(response => response.json())
          .then(data => {
            console.log(data);
            this.log=data;
          })
          .catch(error => {
            console.error('Error:', error);
            this.log = 'Error occurred.'
          });
    },
    clearLog() {
      fetch('http://localhost:8081/clearLog', { method: 'POST' })
          .then(response => {
            if (response.ok) {
              this.log = [];
              console.log('Log Cleaned');
              this.log = 'Log Cleaned.';
            } else {
              console.error('Fail to clean the log, please try again');
              this.log = 'Fail to clean the log, please try again';
            }
          })
          .catch(error => {
            console.error('Error:', error);
            this.log = 'Error occurred.';
          });
    }
  }
};
</script>

<style scoped>
.log-content {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  white-space: pre-wrap;
  font-family: monospace;
  width: 60%;
}
</style>