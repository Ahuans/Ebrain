<template>
  <el-tabs
    v-model="currentTabValue"
    type="card"
    editable
    class="tabs"
    @edit="HandleTabsEdit"
    @click="SwtichTab"
  >
    <el-tab-pane v-for="tab in tabs" :key="tab.index" :label="tab.url" :name="tab.index">
    </el-tab-pane>
  </el-tabs>

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
    <el-col>
      <p>Result</p>
    </el-col>
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
    let tabs = ref([])
    let currentTabValue = ref()

    let currentTab = ref()

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
    let queryTable = ref([])
    let bodyTable = ref([])

    let headerNameSuggestion = ref([])

    /**
     * @param {json} table [{name:"abc ",value "123"}]
     * @return {json} transformed Json [{"abc ":"123"}]
     */
    function TableToJson(table) {
      let res = {}
      table.forEach((record) => {
        if (record.name !== '') {
          let recordName = record.name
          let recordValue = record.value
          res[recordName] = recordValue
          console.log(res)
        }
      })
      return res
    }

    /**
     * Send the api request to own backend and get the result in Json
     */
    async function AccessAPI() {
      let header = TableToJson(headerTable.value)
      let body = TableToJson(bodyTable.value)
      // add queryparams to url todo later
      let paramsJson = TableToJson(queryTable.value)
      let urlParams = ''
      if (Object.keys(paramsJson).length !== 0) {
        urlParams = '?' + new URLSearchParams(paramsJson).toString()
      }

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
            url: targetURL.value + urlParams,
            method: selected_API_Method.value,
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

    /**
     * @param {string} format
     */
    function ParseData(format) {
      //todo
      format = format.match('\/{1}([a-zA-Z]*)')
      //html
      //js
    }

    /**
     * add empty name value pair json to the array
     * @param {arr} dataSource
     */
    function AddData(dataSource) {
      //todo
      console.log(dataSource)
      dataSource.push({
        name: '',
        value: ''
      })
    }

    /**
     * add empty tab
     * @param {arr} tabsArray
     */
    function SpawnEmptyTab(tabsArray) {
      let length = tabsArray.length
      tabsArray.push({
        index: length,
        url: '',
        api_method: API_METHOD[0],
        headers: [],
        query: [],
        body: []
      })
    }

    /**
     * add empty tab
     * @param {json} tabsInfo
     */
    function SetupTab(tabInfo) {
      
      currentTab = tabInfo
      // apply the setting
      targetURL.value = currentTab.url
      selected_API_Method.value = currentTab.api_method
      headerTable.value = currentTab.headers
      queryTable.value = currentTab.query
      bodyTable.value = currentTab.body

      // connection
      currentTab.url = targetURL
      currentTab.api_method = selected_API_Method
      currentTab.headers = headerTable
      currentTab.query = queryTable
      currentTab.body = bodyTable
    }
    /**
     * handle add and remove
     * @param {'TabPaneName | undefined'} targetname
     * @param {'add'|'remove'} action
     */
    function HandleTabsEdit(targetName, action) {
      console.log(action)
      console.log(targetName)
      if (action === 'add') {
        SpawnEmptyTab(tabs.value)
      } else if (action === 'remove') {
      }
    }

    /**
     *
     * @param {TabsPaneContext} pane
     * @param {Event} ev
     */
    function SwtichTab(pane, ev) {
      console.log('Swtich Tab')
      console.log(currentTabValue.value)
      //apply tab
      SetupTab(tabs.value[currentTabValue.value])
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
      TableToJson,
      currentTabValue,
      tabs,
      SpawnEmptyTab,
      SetupTab,
      HandleTabsEdit,
      SwtichTab,
      currentTab
    }
  },
  mounted() {
    console.log('Mounted')
    this.selected_API_Method = API_METHOD[0]
    this.headerNameSuggestion = HEADER_NAMES

    // init
    // spawn empty tab
    this.SpawnEmptyTab(this.tabs)
    this.currentTabValue = this.tabs[0].index
    this.SetupTab(this.tabs[0])
  }
}
</script>
