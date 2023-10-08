<template>
  <!--Input preview area -->
  <el-row :gutter="20">
    <el-col :span="2">
      <el-select v-model="selected_API_Method" placeholder="method">
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
    <el-col>
      <el-tabs v-model="tabActiveName" class="Params" @tab-click="">
        <el-tab-pane label="Header" name="Header">Header</el-tab-pane>
        <el-tab-pane label="Query" name="Query">
          <!--Query area -->
          <el-button type="primary" @click="AddData(queryTable)">Add</el-button>
          <DataEntryTableComponent :dataArray="queryTable" />
        </el-tab-pane>
        <el-tab-pane label="Body" name="Body">Role</el-tab-pane>
      </el-tabs>
    </el-col>
  </el-row>
  <!--Result preview area -->
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
      >
      </el-input>
    </el-col>
  </el-row>
</template>

<style></style>

<script>
import { ref, reactive } from 'vue'
import { API_METHOD, API_SERVER_URL } from '../constant/constant'
import axios from 'axios'
import DataEntryTableComponent from '../components/ApiViewComponents/DataEntryTableComponent.vue'

export default {
  components: {
    DataEntryTableComponent
  },

  setup(props, ctx) {
    let targetURL = ref('')
    let selected_API_Method = ref('')
    let header = reactive({})
    let result = ref('')
    let tabActiveName = ref('Header')
    let queryTable = ref([
      {
        // test only
        name: 'ask',
        value: '123'
      }
    ])

    async function AccessAPI() {
      let response = await axios({
        headers: {
          'content-type': 'application/json'
        },
        method: 'post',
        url: API_SERVER_URL,
        data: {
          header: header.value,
          url: targetURL.value,
          method: selected_API_Method.value
        }
      })

      result.value = response.data.data
      console.log(response.data.header['content-type'])
      console.log(result.value)
    }

    //@format: string
    function ParseData(format) {
      //todo
      format = format.match('\/{1}([a-zA-Z]*)')
      //html
      //js
    }
    //@dataSource:Array
    function AddData(dataSource) {//todo
      console.log(dataSource)
      dataSource.push({
        name: 'ask123',
        value: '123123'
      })
    }

    return {
      targetURL,
      selected_API_Method,
      AccessAPI,
      result,
      tabActiveName,
      queryTable,
      API_METHOD,
      AddData
    }
  },
  mounted() {
    console.log('Mounted')
    this.selected_API_Method = API_METHOD[0]
  }
}
</script>
