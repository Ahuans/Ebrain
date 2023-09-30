<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="common-layout">
      <el-container>
        <el-aside width="200px">
          <h1>Ebrain</h1>
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            
          >
            <el-menu-item index="1"  @click="$router.push('/')">ZooKeeper</el-menu-item>
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

<style scoped></style>
