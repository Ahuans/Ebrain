<!--&lt;!&ndash;<template>&ndash;&gt;-->
<!--&lt;!&ndash;  <div class="container">&ndash;&gt;-->
<!--&lt;!&ndash;    <div class="sidebar">&ndash;&gt;-->
<!--&lt;!&ndash;      <h2>Menu</h2>&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="search-bar">&ndash;&gt;-->
<!--&lt;!&ndash;        <el-input v-model="searchText" placeholder="typing localhost /port..." />&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <el-button type="primary" @click="searchNodes">Search</el-button>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="node-list">&ndash;&gt;-->
<!--&lt;!&ndash;        <div class="node-item">&ndash;&gt;-->
<!--&lt;!&ndash;          <p>Add new parent node</p>&ndash;&gt;-->
<!--&lt;!&ndash;          <el-span class="column-name"></el-span> &lt;!&ndash; 备注名 &ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;          <el-span class="column-name"></el-span>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;          <el-span class="column-name"></el-span>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--&lt;!&ndash;        <div v-for="node in filteredNodeList" :key="node.id" class="node-item">&ndash;&gt;-->
<!--&lt;!&ndash;          <span @click="selectNode(node)">{{ node.localhost }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;          <span>{{ node.port }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;          <el-button type="primary" @click="deleteNode(node)">Delete</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="add-node">&ndash;&gt;-->
<!--&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="newNodeRemark" ref="remarkInput" placeholder="Remark" />&ndash;&gt;-->
<!--&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="newNodeLocalhost" ref="localhostInput" placeholder="Parent Address" />&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <el-input style="margin-bottom: 10px;"  v-model="newNodePort"  ref="postInput" placeholder="port" />&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button type="primary"  @click="addNode">Add Node</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;    </div>&ndash;&gt;-->
<!--&lt;!&ndash;    <div class="main">&ndash;&gt;-->
<!--&lt;!&ndash;      <h2>Node Infomation</h2>&ndash;&gt;-->
<!--&lt;!&ndash;      <div v-if="selectedNode" class="node-info">&ndash;&gt;-->
<!--&lt;!&ndash;        <p>Remark: {{ isEditing ? editedRemark : selectedNode.remark }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>localhost: {{ isEditing ? editedLocalhost : selectedNode.localhost }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>port: {{ isEditing ? editedPort : selectedNode.port }}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button type="primary" style="margin-bottom: 10px;" v-if="!isEditing" @click="editNode">Edit</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button type="primary" v-if="isEditing" @click="saveNode">Save</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="editedLocalhost" :disabled="!isEditing" />&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="editedPort" :disabled="!isEditing" />&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;        <el-textarea v-model="selectedNode.text" rows="4" cols="50" placeholder="add some details..."></el-textarea>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        validate the connection &ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;        <br><el-button type="primary" @click="connectNode">Connect</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        增删副节点？&ndash;&gt;&ndash;&gt;-->

<!--&lt;!&ndash;        <el-button type="primary" @click="add_sub">Add</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button type="primary" @click="delete_sub">Delete</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;      <div v-else>&ndash;&gt;-->
<!--&lt;!&ndash;        <p>please select a node</p>&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;    </div>&ndash;&gt;-->
<!--&lt;!&ndash;  </div>&ndash;&gt;-->
<!--&lt;!&ndash;</template>&ndash;&gt;-->

<!--&lt;!&ndash;<script>&ndash;&gt;-->
<!--&lt;!&ndash;import axios from 'axios';&ndash;&gt;-->
<!--&lt;!&ndash;export default {&ndash;&gt;-->
<!--&lt;!&ndash;  // data() {&ndash;&gt;-->
<!--&lt;!&ndash;  //   return {&ndash;&gt;-->
<!--&lt;!&ndash;  //     nodeList: [],&ndash;&gt;-->
<!--&lt;!&ndash;  //     newNodeRemark: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     newNodeLocalhost: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     // newNodePort: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     selectedNode: null,&ndash;&gt;-->
<!--&lt;!&ndash;  //     searchText: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     isEditing: false,&ndash;&gt;-->
<!--&lt;!&ndash;  //     editedLocalhost: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     editedPort: "",&ndash;&gt;-->
<!--&lt;!&ndash;  //     responseData: []&ndash;&gt;-->
<!--&lt;!&ndash;  //   };&ndash;&gt;-->
<!--&lt;!&ndash;  // },&ndash;&gt;-->

<!--&lt;!&ndash;  // for testing&ndash;&gt;-->
<!--&lt;!&ndash;  data() {&ndash;&gt;-->
<!--&lt;!&ndash;    return {&ndash;&gt;-->
<!--&lt;!&ndash;      nodeList: [&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          id: 1,&ndash;&gt;-->
<!--&lt;!&ndash;          localhost: 'localhost1',&ndash;&gt;-->
<!--&lt;!&ndash;          port: '8080',&ndash;&gt;-->
<!--&lt;!&ndash;          text: ''&ndash;&gt;-->
<!--&lt;!&ndash;        },&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          id: 2,&ndash;&gt;-->
<!--&lt;!&ndash;          localhost: 'localhost2',&ndash;&gt;-->
<!--&lt;!&ndash;          port: '8081',&ndash;&gt;-->
<!--&lt;!&ndash;          text: ''&ndash;&gt;-->
<!--&lt;!&ndash;        },&ndash;&gt;-->
<!--&lt;!&ndash;        {&ndash;&gt;-->
<!--&lt;!&ndash;          id: 3,&ndash;&gt;-->
<!--&lt;!&ndash;          localhost: 'localhost3',&ndash;&gt;-->
<!--&lt;!&ndash;          port: '8082',&ndash;&gt;-->
<!--&lt;!&ndash;          text: ''&ndash;&gt;-->
<!--&lt;!&ndash;        }&ndash;&gt;-->
<!--&lt;!&ndash;      ],&ndash;&gt;-->
<!--&lt;!&ndash;      newNodeRemark: "",&ndash;&gt;-->
<!--&lt;!&ndash;      newNodeLocalhost: "",&ndash;&gt;-->
<!--&lt;!&ndash;      // newNodePort: "",&ndash;&gt;-->
<!--&lt;!&ndash;      selectedNode: null,&ndash;&gt;-->
<!--&lt;!&ndash;      searchText: "",&ndash;&gt;-->
<!--&lt;!&ndash;      isEditing: false,&ndash;&gt;-->
<!--&lt;!&ndash;      editedLocalhost: "",&ndash;&gt;-->
<!--&lt;!&ndash;      editedPort: "",&ndash;&gt;-->
<!--&lt;!&ndash;      responseData: []&ndash;&gt;-->
<!--&lt;!&ndash;    };&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->

<!--&lt;!&ndash;  methods: {&ndash;&gt;-->
<!--&lt;!&ndash;    addNode() {&ndash;&gt;-->
<!--&lt;!&ndash;      if (this.newNodeLocalhost && this.newNodePort) {&ndash;&gt;-->
<!--&lt;!&ndash;        const newNode = {&ndash;&gt;-->
<!--&lt;!&ndash;          id: this.nodeList.length + 1,&ndash;&gt;-->
<!--&lt;!&ndash;          localhost: this.newNodeLocalhost,&ndash;&gt;-->
<!--&lt;!&ndash;          // port: this.newNodePort,&ndash;&gt;-->
<!--&lt;!&ndash;          text: ''&ndash;&gt;-->
<!--&lt;!&ndash;        };&ndash;&gt;-->
<!--&lt;!&ndash;        this.nodeList.push(newNode);&ndash;&gt;-->
<!--&lt;!&ndash;        this.newNodeLocalhost = '';&ndash;&gt;-->
<!--&lt;!&ndash;        // this.newNodePort = '';&ndash;&gt;-->
<!--&lt;!&ndash;        this.$refs.localhostInput.focus();&ndash;&gt;-->

<!--&lt;!&ndash;      const formData = new FormData();&ndash;&gt;-->
<!--&lt;!&ndash;      formData.append('localhost', newNode.localhost);&ndash;&gt;-->
<!--&lt;!&ndash;      // formData.append('port', newNode.port);&ndash;&gt;-->

<!--&lt;!&ndash;      fetch('/http://localhost:8081/addParent', {&ndash;&gt;-->
<!--&lt;!&ndash;        method: 'POST',&ndash;&gt;-->
<!--&lt;!&ndash;        body: formData&ndash;&gt;-->
<!--&lt;!&ndash;      })&ndash;&gt;-->
<!--&lt;!&ndash;          .then(response => response.json())&ndash;&gt;-->
<!--&lt;!&ndash;          .then(data => {&ndash;&gt;-->
<!--&lt;!&ndash;            console.log('添加节点成功:', data);&ndash;&gt;-->
<!--&lt;!&ndash;            this.nodeList.push(data);&ndash;&gt;-->
<!--&lt;!&ndash;            this.newNodeLocalhost = '';&ndash;&gt;-->
<!--&lt;!&ndash;            // this.newNodePort = '';&ndash;&gt;-->
<!--&lt;!&ndash;            this.$refs.localhostInput.focus();&ndash;&gt;-->
<!--&lt;!&ndash;          })&ndash;&gt;-->
<!--&lt;!&ndash;          .catch(error => {&ndash;&gt;-->
<!--&lt;!&ndash;            console.error('添加节点失败:', error);&ndash;&gt;-->
<!--&lt;!&ndash;          });&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    selectNode(node) {&ndash;&gt;-->
<!--&lt;!&ndash;      this.selectedNode = node;&ndash;&gt;-->
<!--&lt;!&ndash;      this.editedLocalhost = node.localhost;&ndash;&gt;-->
<!--&lt;!&ndash;      // this.editedPort = node.port;&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    deleteNode(node) {&ndash;&gt;-->
<!--&lt;!&ndash;      const index = this.nodeList.indexOf(node);&ndash;&gt;-->
<!--&lt;!&ndash;      if (index !== -1) {&ndash;&gt;-->
<!--&lt;!&ndash;        const formData = new FormData();&ndash;&gt;-->
<!--&lt;!&ndash;        formData.append('parent', node.id.toString());&ndash;&gt;-->

<!--&lt;!&ndash;        fetch('http://localhost:8081/removeParent', {&ndash;&gt;-->
<!--&lt;!&ndash;          method: 'POST',&ndash;&gt;-->
<!--&lt;!&ndash;          body: formData&ndash;&gt;-->
<!--&lt;!&ndash;        })&ndash;&gt;-->
<!--&lt;!&ndash;            .then(response => response.json())&ndash;&gt;-->
<!--&lt;!&ndash;            .then(data => {&ndash;&gt;-->
<!--&lt;!&ndash;              console.log('成功删除节点:', data);&ndash;&gt;-->
<!--&lt;!&ndash;              this.nodeList.splice(index, 1);&ndash;&gt;-->
<!--&lt;!&ndash;              if (this.selectedNode === node) {&ndash;&gt;-->
<!--&lt;!&ndash;                this.selectedNode = null;&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            })&ndash;&gt;-->
<!--&lt;!&ndash;            .catch(error => {&ndash;&gt;-->
<!--&lt;!&ndash;              console.error('删除节点失败:', error);&ndash;&gt;-->
<!--&lt;!&ndash;            });&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->

<!--&lt;!&ndash;    // 检查该父节点的连接状态，缺少接口&ndash;&gt;-->
<!--&lt;!&ndash;    connectNode() {&ndash;&gt;-->
<!--&lt;!&ndash;      if (this.selectedNode) {&ndash;&gt;-->
<!--&lt;!&ndash;        axios.get(`/get/connection/status/API/${this.selectedNode.id}`)&ndash;&gt;-->
<!--&lt;!&ndash;            .then(response => {&ndash;&gt;-->
<!--&lt;!&ndash;              // 后端返回成功&ndash;&gt;-->
<!--&lt;!&ndash;              console.log("Success getting status!", response.data);&ndash;&gt;-->
<!--&lt;!&ndash;              // 更新选中节点的连接状态&ndash;&gt;-->
<!--&lt;!&ndash;              this.selectedNode.isConnected = response.data.isConnected;&ndash;&gt;-->
<!--&lt;!&ndash;              // 根据连接状态进行相应的提示&ndash;&gt;-->
<!--&lt;!&ndash;              if (response.data.isConnected) {&ndash;&gt;-->
<!--&lt;!&ndash;                alert("The node is successfully connected!");&ndash;&gt;-->
<!--&lt;!&ndash;              } else {&ndash;&gt;-->
<!--&lt;!&ndash;                alert("The node fail to connect!");&ndash;&gt;-->
<!--&lt;!&ndash;              }&ndash;&gt;-->
<!--&lt;!&ndash;            })&ndash;&gt;-->
<!--&lt;!&ndash;            .catch(error => {&ndash;&gt;-->
<!--&lt;!&ndash;              // 后端返回错误&ndash;&gt;-->
<!--&lt;!&ndash;              console.error("Error: Fail to obtain the status.", error);&ndash;&gt;-->
<!--&lt;!&ndash;            });&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->
<!--&lt;!&ndash;    delete_sub() {&ndash;&gt;-->
<!--&lt;!&ndash;      // const index = this.nodeList.indexOf(this.selectedNode);&ndash;&gt;-->
<!--&lt;!&ndash;      // if (index !== -1) {&ndash;&gt;-->
<!--&lt;!&ndash;      //   this.nodeList.splice(index, 1);&ndash;&gt;-->
<!--&lt;!&ndash;      //   this.selectedNode = null;&ndash;&gt;-->
<!--&lt;!&ndash;      // }&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->
<!--&lt;!&ndash;    add_sub() {&ndash;&gt;-->

<!--&lt;!&ndash;    },&ndash;&gt;-->
<!--&lt;!&ndash;    searchNodes() {&ndash;&gt;-->
<!--&lt;!&ndash;      // 执行搜索节点的逻辑&ndash;&gt;-->
<!--&lt;!&ndash;      console.log("搜索节点:", this.searchText);&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->
<!--&lt;!&ndash;    editNode() {&ndash;&gt;-->
<!--&lt;!&ndash;      this.isEditing = true;&ndash;&gt;-->
<!--&lt;!&ndash;    },&ndash;&gt;-->
<!--&lt;!&ndash;    saveNode() {&ndash;&gt;-->
<!--&lt;!&ndash;      this.isEditing = false;&ndash;&gt;-->
<!--&lt;!&ndash;      // 保存节点的逻辑&ndash;&gt;-->
<!--&lt;!&ndash;      console.log("保存节点:", this.selectedNode);&ndash;&gt;-->

<!--&lt;!&ndash;      // 更新左边导航栏中的节点信息&ndash;&gt;-->
<!--&lt;!&ndash;      const index = this.nodeList.findIndex(node => node.id === this.selectedNode.id);&ndash;&gt;-->
<!--&lt;!&ndash;      if (index !== -1) {&ndash;&gt;-->
<!--&lt;!&ndash;        // this.nodeList[index].localhost = this.editedLocalhost;&ndash;&gt;-->
<!--&lt;!&ndash;        // this.nodeList[index].port = this.editedPort;&ndash;&gt;-->
<!--&lt;!&ndash;        this.nodeList[index].name = `${this.editedLocalhost}:${this.editedPort}`;&ndash;&gt;-->
<!--&lt;!&ndash;      }&ndash;&gt;-->
<!--&lt;!&ndash;    }&ndash;&gt;-->
<!--&lt;!&ndash;  },&ndash;&gt;-->
<!--&lt;!&ndash;  computed: {&ndash;&gt;-->
<!--&lt;!&ndash;    filteredNodeList() {&ndash;&gt;-->
<!--&lt;!&ndash;      return this.nodeList.filter(node => {&ndash;&gt;-->
<!--&lt;!&ndash;        const searchString = this.searchText.toLowerCase();&ndash;&gt;-->
<!--&lt;!&ndash;        return (&ndash;&gt;-->
<!--&lt;!&ndash;            node.localhost.toLowerCase().includes(searchString) ||&ndash;&gt;-->
<!--&lt;!&ndash;            node.port.toLowerCase().includes(searchString)&ndash;&gt;-->
<!--&lt;!&ndash;        );&ndash;&gt;-->
<!--&lt;!&ndash;      });&ndash;&gt;-->
<!--&lt;!&ndash;    }&ndash;&gt;-->
<!--&lt;!&ndash;  }&ndash;&gt;-->
<!--&lt;!&ndash;};&ndash;&gt;-->
<!--&lt;!&ndash;</script>&ndash;&gt;-->




<!--&lt;!&ndash;<style>&ndash;&gt;-->
<!--&lt;!&ndash;.container {&ndash;&gt;-->
<!--&lt;!&ndash;  display: flex;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.sidebar {&ndash;&gt;-->
<!--&lt;!&ndash;  flex: 1.5;&ndash;&gt;-->
<!--&lt;!&ndash;  margin-right: 20px;&ndash;&gt;-->
<!--&lt;!&ndash;  padding: 0px;&ndash;&gt;-->

<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-list {&ndash;&gt;-->
<!--&lt;!&ndash;  margin-bottom: 10px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-item {&ndash;&gt;-->
<!--&lt;!&ndash;  margin-bottom: 10px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-list .node-item {&ndash;&gt;-->
<!--&lt;!&ndash;  display: flex;&ndash;&gt;-->
<!--&lt;!&ndash;  align-items:center;&ndash;&gt;-->
<!--&lt;!&ndash;  margin-bottom: 2px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-list .column-name {&ndash;&gt;-->
<!--&lt;!&ndash;  flex: 1;&ndash;&gt;-->
<!--&lt;!&ndash;  font-weight: bold;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-list span {&ndash;&gt;-->
<!--&lt;!&ndash;  flex: 1;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.main {&ndash;&gt;-->
<!--&lt;!&ndash;  flex: 2;&ndash;&gt;-->
<!--&lt;!&ndash;  padding: 0px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.node-info textarea {&ndash;&gt;-->
<!--&lt;!&ndash;  margin-bottom: 10px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.search-bar {&ndash;&gt;-->
<!--&lt;!&ndash;  display: flex;&ndash;&gt;-->
<!--&lt;!&ndash;  margin-bottom: 10px;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;.search-bar input {&ndash;&gt;-->
<!--&lt;!&ndash;  flex: 1;&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->

<!--&lt;!&ndash;</style>&ndash;&gt;-->


<template>
  <div class="container">
    <div class="sidebar">
      <h2>Menu</h2>
      <div class="search-bar">
        <el-input v-model="searchText" placeholder="Type localhost / port..." />
      </div>
      <div class="node-list">
        <div class="node-item" v-for="node in filteredNodeList" :key="node.id">
          <span @click="selectNode(node)">{{ node.remark }} {{ node.localhost }}</span>
          <el-button type="primary" @click="deleteNode(node)">Delete</el-button>
        </div>
      </div>
      <div class="add-node">
        <el-input style="margin-bottom: 10px;" v-model="newNodeRemark" ref="remarkInput" placeholder="Remark" />
        <el-input style="margin-bottom: 10px;" v-model="newNodeLocalhost" ref="localhostInput" placeholder="Localhost" />
        <el-button type="primary" @click="addNode">Add Node</el-button>
      </div>
    </div>
    <div class="main">
      <h2>Node Information</h2>
      <div v-if="selectedNode" class="node-info">
        <p>
          Remark:
          <template v-if="isEditing">
            <el-input v-model="editedRemark" />
          </template>
          <template v-else>
            {{ selectedNode.remark }}
          </template>
        </p>
        <p>Localhost: {{ selectedNode.localhost }}</p>
        <p>Port: {{ selectedNode.port }}</p>
        <el-button type="primary" style="margin-bottom: 10px;" v-if="!isEditing" @click="editNode">Edit</el-button>
        <el-button type="primary" v-if="isEditing" @click="saveNode">Save</el-button>
        <el-textarea v-model="selectedNode.text" rows="4" cols="50" placeholder="Add some details..."></el-textarea>
        <br>
        <el-button type="primary" @click="connectNode">Connect</el-button>
        <el-button type="primary" @click="showAddSubForm = true">Add</el-button>
        <el-button type="primary" @click="delete_sub">Delete</el-button>

        <div v-if="showAddSubForm" class="add-sub-form">
          <el-input v-model="newSubNodeParent" placeholder="Parent" />
          <el-input v-model="newSubNodeIp" placeholder="IP" />
          <el-input v-model="newSubNodePort" placeholder="Port" />
          <el-button type="primary" @click="confirmAddSub">Confirm</el-button>
          <el-button type="text" @click="cancelAddSub">Cancel</el-button>
        </div>

        <div>
          <h3>Node Details</h3>
          <div>
            <el-button type="primary" @click="selectNodeDetails(selectedNode.id)">Refresh Details</el-button>
          </div>
          <div v-if="nodeDetailsLoading">Loading...</div>
          <div v-else>
            <pre>{{ nodeDetails }}</pre>
          </div>
        </div>
      </div>
      <div v-else>
        <p>Please select a node</p>
      </div>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nodeList: [
        {
          id: 1,
          remark: 'Parent Node 1',
          localhost: 'localhost1',
          port: '8080',
          text: ''
        },
        {
          id: 2,
          remark: 'Parent Node 2',
          localhost: 'localhost2',
          port: '8081',
          text: ''
        },
        {
          id: 3,
          remark: 'Parent Node 3',
          localhost: 'localhost3',
          port: '8082',
          text: ''
        }
      ],
      newNodeRemark: '',
      newNodeLocalhost: '',
      selectedNode: null,
      searchText: '',
      isEditing: false,
      editedRemark: '',
      responseData: [],
      nodeDetails: '',
      nodeDetailsLoading: false,
      showAddSubForm: false,
      newSubNodeParent: '',
      newSubNodeIp: '',
      newSubNodePort: '',
    };
  },

  methods: {
    addNode() {
      if (this.newNodeLocalhost) {
        const newNode = {
          id: this.nodeList.length + 1,
          remark: this.newNodeRemark,
          localhost: this.newNodeLocalhost,
          port: 'port', // 使用默认的端口
          text: ''
        };
        this.nodeList.push(newNode);
        this.newNodeRemark = '';
        this.newNodeLocalhost = '';
        this.$refs.localhostInput.focus();

        const formData = new FormData();
        formData.append('localhost', newNode.localhost);

        fetch('http://localhost:8081/addParent', {
          method: 'POST',
          body: formData
        })
            .then(response => response.json())
            .then(data => {
              console.log('添加节点成功:', data);
            })
            .catch(error => {
              console.error('添加节点失败:', error);
            });
      }
    },

    selectNode(node) {
      this.selectedNode = node;
      this.nodeDetails = '';
    },

    selectNodeDetails(nodeId) {
      this.nodeDetailsLoading = true;
      const formData = new FormData();
      formData.append('nodeId', nodeId);

      fetch('http://localhost:8081/getNodeInformation', {
        method: 'POST',
        body: formData
      })
          .then(response => response.text())
          .then(data => {
            this.nodeDetailsLoading = false;
            this.nodeDetails = data;
          })
          .catch(error => {
            console.error(error);
            this.nodeDetailsLoading = false;
          });
    },

    deleteNode(node) {
      const index = this.nodeList.indexOf(node);
      if (index !== -1) {
        const formData = new FormData();
        formData.append('localhost', node.localhost);

        fetch('http://localhost:8081/deleteParent', {
          method: 'POST',
          body: formData
        })
          .then(response => response.json())
          .then(data => {
            console.log('删除节点成功:', data);
          })
          .catch(error => {
            console.error('删除节点失败:', error);
          });

        this.nodeList.splice(index, 1);
        if (this.selectedNode === node) {
          this.selectedNode = null;
        }
      }
    },

    editNode() {
      this.isEditing = true;
    },

    saveNode() {
      this.selectedNode.remark = this.editedRemark;
      this.isEditing = false;
    },

    connectNode() {
      // 连接节点的逻辑
    },

    add_sub() {
      this.showAddSubForm = true;
    },
    confirmAddSub() {
      const formData = new FormData();
      formData.append('parent', this.newSubNodeParent);
      formData.append('ip', this.newSubNodeIp);
      formData.append('port', this.newSubNodePort);

      fetch('http://localhost:8081/addChild', {
        method: 'POST',
        body: formData
      })
          .then(response => response.json())
          .then(data => {
            console.log('添加子节点成功:', data);
            this.nodeDetails();
          })
          .catch(error => {
            console.error('添加子节点失败:', error);
          });

      this.newSubNodeParent = '';
      this.newSubNodeIp = '';
      this.newSubNodePort = '';
      this.showAddSubForm = false;
    },
    cancelAddSub() {
      this.newSubNodeParent = '';
      this.newSubNodeIp = '';
      this.newSubNodePort = '';
      this.showAddSubForm = false;
    },
    delete_sub() {
      // 删除子节点的逻辑
    },

    filterNodes() {
      const searchText = this.searchText.toLowerCase();
      return this.nodeList.filter(node => {
        return node.remark.toLowerCase().includes(searchText) || node.localhost.toLowerCase().includes(searchText);
      });
    }
  },

  computed: {
    filteredNodeList() {
      return this.filterNodes();
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.sidebar {
  flex: 1;
  padding: 20px;
  background-color: #f5f5f5;
}

.sidebar h2 {
  margin-bottom: 20px;
}

.node-list {
  margin-top: 20px;
}

.node-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.main {
  flex: 3;
  padding: 20px;
}

.main h2 {
  margin-bottom: 20px;
}

.node-info p {
  margin-bottom: 10px;
}

.search-bar {
  margin-bottom: 20px;
}

.add-node {
  margin-top: 20px;
}

.add-node el-input {
  margin-bottom: 10px;
}
</style>


