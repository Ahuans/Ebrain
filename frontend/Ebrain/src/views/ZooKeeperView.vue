<!--<template>-->
<!--  <div class="container">-->
<!--    <div class="sidebar">-->
<!--      <h2>Menu</h2>-->
<!--      <div class="search-bar">-->
<!--        <el-input v-model="searchText" placeholder="typing localhost /port..." />-->
<!--&lt;!&ndash;        <el-button type="primary" @click="searchNodes">Search</el-button>&ndash;&gt;-->
<!--      </div>-->
<!--      <div class="node-list">-->
<!--        <div class="node-item">-->
<!--          <p>Add new parent node</p>-->
<!--          <el-span class="column-name"></el-span> &lt;!&ndash; 备注名 &ndash;&gt;-->
<!--          <el-span class="column-name"></el-span>-->
<!--&lt;!&ndash;          <el-span class="column-name"></el-span>&ndash;&gt;-->
<!--        </div>-->
<!--        <div v-for="node in filteredNodeList" :key="node.id" class="node-item">-->
<!--          <span @click="selectNode(node)">{{ node.localhost }}</span>-->
<!--          <span>{{ node.port }}</span>-->
<!--          <el-button type="primary" @click="deleteNode(node)">Delete</el-button>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="add-node">-->
<!--        <el-input style="margin-bottom: 10px;" v-model="newNodeRemark" ref="remarkInput" placeholder="Remark" />-->
<!--        <el-input style="margin-bottom: 10px;" v-model="newNodeLocalhost" ref="localhostInput" placeholder="Parent Address" />-->
<!--&lt;!&ndash;        <el-input style="margin-bottom: 10px;"  v-model="newNodePort"  ref="postInput" placeholder="port" />&ndash;&gt;-->
<!--        <el-button type="primary"  @click="addNode">Add Node</el-button>-->
<!--      </div>-->
<!--    </div>-->
<!--    <div class="main">-->
<!--      <h2>Node Infomation</h2>-->
<!--      <div v-if="selectedNode" class="node-info">-->
<!--        <p>Remark: {{ isEditing ? editedRemark : selectedNode.remark }}</p>-->
<!--        <p>localhost: {{ isEditing ? editedLocalhost : selectedNode.localhost }}</p>-->
<!--        <p>port: {{ isEditing ? editedPort : selectedNode.port }}</p>-->
<!--        <el-button type="primary" style="margin-bottom: 10px;" v-if="!isEditing" @click="editNode">Edit</el-button>-->
<!--        <el-button type="primary" v-if="isEditing" @click="saveNode">Save</el-button>-->
<!--&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="editedLocalhost" :disabled="!isEditing" />&ndash;&gt;-->
<!--&lt;!&ndash;        <el-input style="margin-bottom: 10px;" v-model="editedPort" :disabled="!isEditing" />&ndash;&gt;-->
<!--        <el-textarea v-model="selectedNode.text" rows="4" cols="50" placeholder="add some details..."></el-textarea>-->
<!--&lt;!&ndash;        validate the connection &ndash;&gt;-->
<!--        <br><el-button type="primary" @click="connectNode">Connect</el-button>-->
<!--&lt;!&ndash;        增删副节点？&ndash;&gt;-->

<!--        <el-button type="primary" @click="add_sub">Add</el-button>-->
<!--        <el-button type="primary" @click="delete_sub">Delete</el-button>-->
<!--      </div>-->
<!--      <div v-else>-->
<!--        <p>please select a node</p>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import axios from 'axios';-->
<!--export default {-->
<!--  // data() {-->
<!--  //   return {-->
<!--  //     nodeList: [],-->
<!--  //     newNodeRemark: "",-->
<!--  //     newNodeLocalhost: "",-->
<!--  //     // newNodePort: "",-->
<!--  //     selectedNode: null,-->
<!--  //     searchText: "",-->
<!--  //     isEditing: false,-->
<!--  //     editedLocalhost: "",-->
<!--  //     editedPort: "",-->
<!--  //     responseData: []-->
<!--  //   };-->
<!--  // },-->

<!--  // for testing-->
<!--  data() {-->
<!--    return {-->
<!--      nodeList: [-->
<!--        {-->
<!--          id: 1,-->
<!--          localhost: 'localhost1',-->
<!--          port: '8080',-->
<!--          text: ''-->
<!--        },-->
<!--        {-->
<!--          id: 2,-->
<!--          localhost: 'localhost2',-->
<!--          port: '8081',-->
<!--          text: ''-->
<!--        },-->
<!--        {-->
<!--          id: 3,-->
<!--          localhost: 'localhost3',-->
<!--          port: '8082',-->
<!--          text: ''-->
<!--        }-->
<!--      ],-->
<!--      newNodeRemark: "",-->
<!--      newNodeLocalhost: "",-->
<!--      // newNodePort: "",-->
<!--      selectedNode: null,-->
<!--      searchText: "",-->
<!--      isEditing: false,-->
<!--      editedLocalhost: "",-->
<!--      editedPort: "",-->
<!--      responseData: []-->
<!--    };-->
<!--  },-->

<!--  methods: {-->
<!--    addNode() {-->
<!--      if (this.newNodeLocalhost && this.newNodePort) {-->
<!--        const newNode = {-->
<!--          id: this.nodeList.length + 1,-->
<!--          localhost: this.newNodeLocalhost,-->
<!--          // port: this.newNodePort,-->
<!--          text: ''-->
<!--        };-->
<!--        this.nodeList.push(newNode);-->
<!--        this.newNodeLocalhost = '';-->
<!--        // this.newNodePort = '';-->
<!--        this.$refs.localhostInput.focus();-->

<!--      const formData = new FormData();-->
<!--      formData.append('localhost', newNode.localhost);-->
<!--      // formData.append('port', newNode.port);-->

<!--      fetch('/http://localhost:8081/addParent', {-->
<!--        method: 'POST',-->
<!--        body: formData-->
<!--      })-->
<!--          .then(response => response.json())-->
<!--          .then(data => {-->
<!--            console.log('添加节点成功:', data);-->
<!--            this.nodeList.push(data);-->
<!--            this.newNodeLocalhost = '';-->
<!--            // this.newNodePort = '';-->
<!--            this.$refs.localhostInput.focus();-->
<!--          })-->
<!--          .catch(error => {-->
<!--            console.error('添加节点失败:', error);-->
<!--          });-->
<!--      }-->
<!--    },-->

<!--    selectNode(node) {-->
<!--      this.selectedNode = node;-->
<!--      this.editedLocalhost = node.localhost;-->
<!--      // this.editedPort = node.port;-->
<!--    },-->

<!--    deleteNode(node) {-->
<!--      const index = this.nodeList.indexOf(node);-->
<!--      if (index !== -1) {-->
<!--        const formData = new FormData();-->
<!--        formData.append('parent', node.id.toString());-->

<!--        fetch('http://localhost:8081/removeParent', {-->
<!--          method: 'POST',-->
<!--          body: formData-->
<!--        })-->
<!--            .then(response => response.json())-->
<!--            .then(data => {-->
<!--              console.log('成功删除节点:', data);-->
<!--              this.nodeList.splice(index, 1);-->
<!--              if (this.selectedNode === node) {-->
<!--                this.selectedNode = null;-->
<!--              }-->
<!--            })-->
<!--            .catch(error => {-->
<!--              console.error('删除节点失败:', error);-->
<!--            });-->
<!--      }-->
<!--    },-->

<!--    // 检查该父节点的连接状态，缺少接口-->
<!--    connectNode() {-->
<!--      if (this.selectedNode) {-->
<!--        axios.get(`/get/connection/status/API/${this.selectedNode.id}`)-->
<!--            .then(response => {-->
<!--              // 后端返回成功-->
<!--              console.log("Success getting status!", response.data);-->
<!--              // 更新选中节点的连接状态-->
<!--              this.selectedNode.isConnected = response.data.isConnected;-->
<!--              // 根据连接状态进行相应的提示-->
<!--              if (response.data.isConnected) {-->
<!--                alert("The node is successfully connected!");-->
<!--              } else {-->
<!--                alert("The node fail to connect!");-->
<!--              }-->
<!--            })-->
<!--            .catch(error => {-->
<!--              // 后端返回错误-->
<!--              console.error("Error: Fail to obtain the status.", error);-->
<!--            });-->
<!--      }-->
<!--    },-->
<!--    delete_sub() {-->
<!--      // const index = this.nodeList.indexOf(this.selectedNode);-->
<!--      // if (index !== -1) {-->
<!--      //   this.nodeList.splice(index, 1);-->
<!--      //   this.selectedNode = null;-->
<!--      // }-->
<!--    },-->
<!--    add_sub() {-->

<!--    },-->
<!--    searchNodes() {-->
<!--      // 执行搜索节点的逻辑-->
<!--      console.log("搜索节点:", this.searchText);-->
<!--    },-->
<!--    editNode() {-->
<!--      this.isEditing = true;-->
<!--    },-->
<!--    saveNode() {-->
<!--      this.isEditing = false;-->
<!--      // 保存节点的逻辑-->
<!--      console.log("保存节点:", this.selectedNode);-->

<!--      // 更新左边导航栏中的节点信息-->
<!--      const index = this.nodeList.findIndex(node => node.id === this.selectedNode.id);-->
<!--      if (index !== -1) {-->
<!--        // this.nodeList[index].localhost = this.editedLocalhost;-->
<!--        // this.nodeList[index].port = this.editedPort;-->
<!--        this.nodeList[index].name = `${this.editedLocalhost}:${this.editedPort}`;-->
<!--      }-->
<!--    }-->
<!--  },-->
<!--  computed: {-->
<!--    filteredNodeList() {-->
<!--      return this.nodeList.filter(node => {-->
<!--        const searchString = this.searchText.toLowerCase();-->
<!--        return (-->
<!--            node.localhost.toLowerCase().includes(searchString) ||-->
<!--            node.port.toLowerCase().includes(searchString)-->
<!--        );-->
<!--      });-->
<!--    }-->
<!--  }-->
<!--};-->
<!--</script>-->




<!--<style>-->
<!--.container {-->
<!--  display: flex;-->
<!--}-->

<!--.sidebar {-->
<!--  flex: 1.5;-->
<!--  margin-right: 20px;-->
<!--  padding: 0px;-->

<!--}-->

<!--.node-list {-->
<!--  margin-bottom: 10px;-->
<!--}-->

<!--.node-item {-->
<!--  margin-bottom: 10px;-->
<!--}-->

<!--.node-list .node-item {-->
<!--  display: flex;-->
<!--  align-items:center;-->
<!--  margin-bottom: 2px;-->
<!--}-->

<!--.node-list .column-name {-->
<!--  flex: 1;-->
<!--  font-weight: bold;-->
<!--}-->

<!--.node-list span {-->
<!--  flex: 1;-->
<!--}-->

<!--.main {-->
<!--  flex: 2;-->
<!--  padding: 0px;-->
<!--}-->

<!--.node-info textarea {-->
<!--  margin-bottom: 10px;-->
<!--}-->

<!--.search-bar {-->
<!--  display: flex;-->
<!--  margin-bottom: 10px;-->
<!--}-->

<!--.search-bar input {-->
<!--  flex: 1;-->
<!--}-->

<!--</style>-->


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
        <el-button type="primary" @click="add_sub">Add</el-button>
        <el-button type="primary" @click="delete_sub">Delete</el-button>
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
      responseData: []
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
      this.editedRemark = node.remark;
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
      // 添加子节点的逻辑
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