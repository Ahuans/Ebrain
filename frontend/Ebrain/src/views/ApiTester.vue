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
        <el-tab-pane label="Header" name="Header">
          <el-button type="primary" @click="AddData(headerTable)">Add</el-button>
          <DataEntryTableComponent
            :nameSuggestion="headerNameSuggestion"
            :dataArray="headerTable"
          />
        </el-tab-pane>
        <el-tab-pane label="Query" name="Query">
          <!--Query area -->
          <el-button type="primary" @click="AddData(queryTable)">Add</el-button>
          <DataEntryTableComponent :dataArray="queryTable" />
        </el-tab-pane>
        <el-tab-pane label="Body" name="Body">
          <el-button type="primary" @click="AddData(bodyTable)">Add</el-button>
          <DataEntryTableComponent :dataArray="bodyTable" />
        </el-tab-pane>
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
import { API_METHOD, API_SERVER_URL, HEADER_NAMES } from '../constant/constant'
import axios from 'axios'
import DataEntryTableComponent from '../components/ApiViewComponents/DataEntryTableComponent.vue'

export default {
  components: {
    DataEntryTableComponent
  },

  setup(props, ctx) {
    let targetURL = ref('')
    let selected_API_Method = ref('')
    let result = ref('')
    let tabActiveName = ref('Header')
    let headerTable = ref([
      {
        name: 'content-type',
        value: 'application/json'
      }
    ])
    let headerNameSuggestion = ref([])
    let queryTable = ref([
      {
        // test only
        name: 'ask',
        value: '123'
      }
    ])
    let bodyTable = ref([])
    /*
    @table is json
    @return a json object 
    */
    function TableToJson(table) {
      let res = {}
      table.forEach((record) => {
        let recordName = record.name
        let recordValue = record.value
        res[recordName] = recordValue
        console.log(res)
      })
      return res
    }

    async function AccessAPI() {
      let header = TableToJson(headerTable.value)
      let body = TableToJson(bodyTable.value)
      // add queryparams to url todo later
      let ParamsJson = TableToJson(queryTable.value)
      const urlParams = new URLSearchParams(ParamsJson).toString()
      console.log(targetURL.value+"?"+urlParams)
      //

      try {
        let response = await axios({
          headers: {
            'content-type': 'application/json'
          },
          method: 'post',
          url: API_SERVER_URL,
          data: {
            header: header,
            url: targetURL.value+"?"+urlParams,
            method: selected_API_Method.value['value'],
            restBody: body
          }
        })

        result.value = response.data.data
        console.log(response.data.header['content-type'])
        console.log(result.value)
      } catch (error) {
        console.log(error)
      }
    }

    //@format: string
    function ParseData(format) {
      //todo
      format = format.match('\/{1}([a-zA-Z]*)')
      //html
      //js
    }
    //@dataSource:Array
    function AddData(dataSource) {
      //todo
      console.log(dataSource)
      dataSource.push({
        name: '',
        value: ''
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
      AddData,
      headerNameSuggestion,
      headerTable,
      bodyTable,
      TableToJson
    }
  },
  mounted() {
    console.log('Mounted')
    this.selected_API_Method = API_METHOD[0]
    this.headerNameSuggestion = HEADER_NAMES
  }
}
</script>
