<template>
  <div>
    <h1>Log</h1>
    <button @click="clearLog">Clean Log</button>
    <div class="log-content">
      <pre>{{ log }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      log: ''
    };
  },
  mounted() {
    this.getLog();
  },
  methods: {
    getLog() {
      fetch('http://localhost:8081/getLog')
          .then(response => response.json())
          .then(data => {
            if (data.success) {
              this.log = data.log;
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    clearLog() {
      fetch('http://localhost:8081/clearLog', { method: 'POST' })
          .then(response => {
            if (response.ok) {
              this.log = '';
              console.log('Log Cleaned');
            } else {
              console.error('Fail to clean the log, please try again');
            }
          })
          .catch(error => {
            console.error('Error:', error);
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