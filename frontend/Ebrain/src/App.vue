<script setup>
import { RouterLink, RouterView } from 'vue-router'
const isLoggedIn = ref(false)
// for test
// const isLoggedIn = ref(true)
// let username = ''
// let password = ''

const submitForm = async () => {
  const formData = new FormData()
  formData.append('username', username)
  formData.append('password', password)

  try {
    const response = await fetch('http://localhost:8081/login', {
      method: 'POST',
      body: formData
    })

    if (response.ok) {
      isLoggedIn.value = true
    } else {
      console.error('Fail. Please check your entries')
    }
  } catch (error) {
    console.error('Error:', error)
  }
}
</script>

<template>
  <div>
    <template v-if="!isLoggedIn">
      <header>
        <div class="common-layout">
          <h1>Login Page</h1>
          <form @submit.prevent="submitForm">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>
            <br>
            <button type="submit">Login</button>
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

<style scoped></style>
