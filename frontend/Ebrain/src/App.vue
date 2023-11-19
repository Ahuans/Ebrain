<script setup>
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <div>
    <template v-if="!isLoggedIn" class="login-container">
      <header>
        <div class="login-box">
          <h1>Login Page</h1>
          <form>
            <div class="form-group">
              <label for="username" class="input-label">Username:</label>
              <input type="text" id="username" class="input-field" required>
            </div>
            <div class="form-group">
              <label for="password" class="input-label">Password:</label>
              <input type="password" id="password" class="input-field" required>
            </div>
            <button type="submit" class="login-button" @click="login">Login</button>
          </form>
        </div>
      </header>
    </template>
    <template v-else>
      <header>
        <div class="common-layout">
          <el-container>
            <el-aside width="200px">
              <h1>Ebrain</h1>
              <el-menu default-active="2" class="el-menu-vertical-demo">
                <el-menu-item index="1" @click="$router.push('/admin')">ZooKeeper</el-menu-item>
                <el-menu-item index="2" @click="$router.push('/ApiTester')">API</el-menu-item>
              </el-menu>
            </el-aside>
            <el-main>
              <RouterView />
            </el-main>
          </el-container>
        </div>
      </header>
    </template>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: true,
      username: '',
      password: ''
    };
  },
  methods: {
    async login() {
      const formData = new FormData();
      formData.append('username', this.username);
      formData.append('password', this.password);

      try {
        const response = await fetch('http://localhost:8081/login', {
          method: 'POST',
          body: formData
        });

        if (response.ok) {
          this.isLoggedIn = true;
          console.log('Login successful');
        } else {
          console.error('Login failed. Please check your credentials');
        }
      } catch (error) {
        console.error('Error:', error);
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: linear-gradient(to bottom, white, lightblue, blue);
}

.login-box {
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  background-color: white;
  color: black;
  width: 400px;
  border: 4px solid rgba(78, 78, 165, 0.82);
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.input-label {
  color: black;
  flex: 0.8;
  display: block;
  text-align: left;
  margin-right: 10px;
}

.input-field {
  padding: 5px 10px;
  border-radius: 5px;
  border: 1px solid black;
  width: 50%;
  color: black;
}

.login-button {
  padding: 8px 16px;
  border-radius: 5px;
  border: none;
  background-color: rgb(78, 78, 165);
  color: white;
  cursor: pointer;
  margin-top: 10px;
}
</style>
