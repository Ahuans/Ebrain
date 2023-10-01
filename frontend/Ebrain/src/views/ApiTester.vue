<template>
  <el-row :gutter="20">
    <el-col :span="2">
      <el-select v-model="method" placeholder="method">
        <el-option
          v-for="item in API_METHOD"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </el-col>
    <el-col :span="20">
      <el-input v-model="targetURL" placeholder="please enter url"></el-input>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" @Click="AccessAPI">Go</el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col> <p>Result</p></el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-input
        type="textarea"
        v-model="result"
        :autosize="{
          minRows: 40,
          maxRows: 40
        }"
        disabled
      >{{ result }}
      </el-input>
    </el-col>
  </el-row>
</template>

<style></style>

<script>
import { ref, onMounted, reactive } from 'vue'
import { API_METHOD } from '../constant/methods'
import axios from 'axios'
export default {
  setup(props, ctx) {
    let targetURL = ref('')
    let method = ref('')
    let header = reactive({})
    let result = ref('')
    onMounted(() => {
      console.log('Mounted')
      method.value = API_METHOD[0].value
    })

    async function AccessAPI() {
      let response = await axios({
        headers: {
          'content-type': 'application/json'
        },
        method: 'post',
        url: 'http://localhost:3000/api',
        data: {
          header: header.value,
          url: targetURL.value,
          method: method.value
        }
      })
      result.value=response.data.data
    }

    return {
      targetURL,
      method,
      API_METHOD,
      AccessAPI,
      result
    }
  }
}
</script>
