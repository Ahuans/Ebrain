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
      <el-button type="primary" @Click="AccessAPI(currentTab)">Go</el-button>
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
    <el-col :span="2">
      <p>Result</p>
    </el-col>
    <el-col :span="2" :offset="18">
      <p>Time : {{ requestTime }} ms</p>
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
    // variable related to api tabs
    let tabs = ref([])
    let currentTabValue = ref()
    let currentTab = ref()

    //varibles related to api
    let targetURL = ref('')
    let selected_API_Method = ref('')
    let headerTable = ref([])
    let queryTable = ref([])
    let bodyTable = ref([])
    let result = ref('')
    let requestTime = ref(0)

    // varibles related to the page components
    let tabActiveName = ref('Header')
    let headerNameSuggestion = ref([])

    //functions
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
    async function AccessAPI(tabInfo) {
      let header = TableToJson(tabInfo.headers)
      let body = TableToJson(tabInfo.body)
      // add queryparams to url todo later
      let paramsJson = TableToJson(tabInfo.query)
      let urlParams = ''
      if (Object.keys(paramsJson).length !== 0) {
        urlParams = '?' + new URLSearchParams(paramsJson).toString()
      }
      // send request to backend
      let startTime = performance.now()
      try {
        let response = await axios({
          headers: {
            'content-type': 'application/json'
          },
          method: 'post',
          url: API_SERVER_URL,
          data: {
            header: header,
            url: tabInfo.url + urlParams,
            method: tabInfo.api_method,
            restBody: body
          }
        })
        tabInfo.result = response.data.data
        //result.value = response.data.data
        console.log(response.data.header['content-type'])
        console.log(tabInfo.result)
      } catch (error) {
        console.log(error)
      }
      let endTime = performance.now()
      //requestTime.value =Math.round(endTime - startTime);
      tabInfo.requestTime = Math.round(endTime - startTime)
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
      let val = tabsArray.length
      if (tabsArray.length !== 0) {
        // index val based the index of last element in the array
        val = tabsArray[val - 1].index + 1
      }
      tabsArray.push({
        index: val,
        url: '',
        api_method: API_METHOD[0],
        headers: [],
        query: [],
        body: [],
        requestTime: 0,
        result: ''
      })
    }

    /**
     * add empty tab
     * @param {json} tabsInfo
     */
    function SetupTab(tabInfo) {
      console.log('currenttab', tabInfo)
      currentTab.value = tabInfo

      // apply the setting
      targetURL.value = currentTab.value.url
      selected_API_Method.value = currentTab.value.api_method
      headerTable.value = currentTab.value.headers
      queryTable.value = currentTab.value.query
      bodyTable.value = currentTab.value.body
      result.value = currentTab.value.result
      requestTime.value = currentTab.value.requestTime
    }

    /**
     * handle add and remove
     * @param {'TabPaneName | undefined'} targetname
     * @param {'add'|'remove'} action
     */
    function HandleTabsEdit(targetName, action) {
      console.log('action:', action, ' targetname : ', targetName)
      if (action === 'add') {
        //add tab
        SpawnEmptyTab(tabs.value)
        console.log(tabs.value)
      } else if (action === 'remove') {
        //remove tab
        console.log(tabs.value)
        if (tabs.value.length <= 1) {
          console.log('Cant remove tab as there is only one')
          return
        }
        // case : target = current
        let nextTabValue = 0
        if (targetName === currentTabValue.value) {
          tabs.value.forEach((tab, index) => {
            if (tab.index === targetName) {
              const nextTab = tabs.value[index + 1] || tabs.value[index - 1]
              if (nextTab) {
                nextTabValue = nextTab.index
                SetupTab(nextTab)
              }
            }
          })
          currentTabValue.value = nextTabValue
          tabs.value = tabs.value.filter((tab) => tab.index !== targetName)
        }
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
      let targetTab = tabs.value.find((tab) => tab.index === currentTabValue.value)
      SetupTab(targetTab)
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
      currentTab,
      requestTime
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
  },
  watch: {
    //keep track
    targetURL(newVal, oldVal) {
      this.currentTab.url = newVal
    },
    selected_API_Method(newVal, oldVal) {
      this.currentTab.api_method = newVal
    },

    currentTab: {
      handler(newVal, oldVal) {
        this.result = this.currentTab.result
        this.requestTime = this.currentTab.requestTime
      },
      deep:true
    }
  }
}
</script>
