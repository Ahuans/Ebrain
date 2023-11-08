<template>
  <div class="container">
    <div class="sidebar">
      <h2>Menu</h2>
      <div class="search-bar">
        <el-input v-model="searchText" placeholder="typing localhost /port..." />
        <el-button type="primary" @click="searchNodes">Search</el-button>
      </div>
      <div class="node-list">
        <div class="node-item">
          <el-span class="column-name">localhost</el-span>
          <el-span class="column-name">port</el-span>

          <div>
          </div>
        </div>
        <div v-for="node in filteredNodeList" :key="node.id" class="node-item">
          <span @click="selectNode(node)">{{ node.localhost }}</span>
          <span>{{ node.port }}</span>
          <el-button type="primary" @click="deleteNode(node)">Delete</el-button>
        </div>
      </div>
      <div class="add-node">
        <el-input style="margin-bottom: 10px;" v-model="newNodeLocalhost" ref="localhostInput" placeholder="localhost" />
        <el-input style="margin-bottom: 10px;"  v-model="newNodePort"  ref="postInput" placeholder="port" />
        <el-button type="primary"  @click="addNode">Add Node</el-button>
      </div>
    </div>
    <div class="main">
      <h2>Node Infomation</h2>
      <div v-if="selectedNode" class="node-info">
        <p>localhost: {{ isEditing ? editedLocalhost : selectedNode.localhost }}</p>
        <p>port: {{ isEditing ? editedPort : selectedNode.port }}</p>
        <el-button type="primary" style="margin-bottom: 10px;" v-if="!isEditing" @click="editNode">Edit</el-button>
        <el-button type="primary" v-if="isEditing" @click="saveNode">Save</el-button>
        <el-input style="margin-bottom: 10px;" v-model="editedLocalhost" :disabled="!isEditing" />
        <el-input style="margin-bottom: 10px;" v-model="editedPort" :disabled="!isEditing" />
        <el-textarea v-model="selectedNode.text" rows="4" cols="50" placeholder="add some details..."></el-textarea>
<!--        validate the connection -->
        <el-button type="primary" @click="connectNode">Connect</el-button>
<!--        增删副节点？-->
        <el-button type="primary" @click="add_sub">Add</el-button>
        <el-button type="primary" @click="delete_sub">Delete</el-button>
      </div>
      <div v-else>
        <p>please select a node</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      nodeList: [],
      newNodeLocalhost: "",
      newNodePort: "",
      selectedNode: null,
      searchText: "",
      isEditing: false,
      editedLocalhost: "",
      editedPort: ""
    };
  },
  methods: {
    addNode() {
      if (this.newNodeLocalhost && this.newNodePort) {
        const newNode = {
          id: this.nodeList.length + 1,
          localhost: this.newNodeLocalhost,
          port: this.newNodePort,
          text: ''
        };
        this.nodeList.push(newNode);
        this.newNodeLocalhost = '';
        this.newNodePort = '';
        this.$refs.localhostInput.focus();
      }
      // 发送新节点信息到后端
      axios.post('/add/node/API', newNode) // 替换为真实的后端 API 地址和请求方法
          .then(response => {
            // 后端返回成功
            console.log("添加节点成功:", response.data);

            // 将新节点添加到前端列表
            this.nodeList.push(response.data);

            // 清空输入框
            this.newNodeLocalhost = '';
            this.newNodePort = '';

            // 设置焦点到 localhost 输入框
            this.$refs.localhostInput.focus();
          })
          .catch(error => {
            // 后端返回错误
            console.error("添加节点失败:", error);
          });
    },

    selectNode(node) {
      this.selectedNode = node;
      this.editedLocalhost = node.localhost;
      this.editedPort = node.port;
    },
    // deleteNode(node) {
    //   const index = this.nodeList.indexOf(node);
    //   if (index !== -1) {
    //     this.nodeList.splice(index, 1);
    //     if (this.selectedNode === node) {
    //       this.selectedNode = null;
    //     }
    //   }
    // },

    deleteNode(node) {
      const index = this.nodeList.indexOf(node);
      if (index !== -1) {
        // 发送删除节点请求到后端
        axios.delete(`/delete/node/API/${node.id}`) // 替换为真实的后端 API 地址和请求方法
            .then(response => {
              // 后端返回成功
              console.log("删除节点成功:", response.data);
              // 从前端列表中删除节点
              this.nodeList.splice(index, 1);
              // 清空选择的节点
              if (this.selectedNode === node) {
                this.selectedNode = null;
              }
            })
            .catch(error => {
              // 后端返回错误
              console.error("删除节点失败:", error);
            });
      }
    },
    connectNode() {
      if (this.selectedNode) {
        axios.get(`/get/connection/status/API/${this.selectedNode.id}`)
            .then(response => {
              // 后端返回成功
              console.log("Success getting status!", response.data);
              // 更新选中节点的连接状态
              this.selectedNode.isConnected = response.data.isConnected;
              // 根据连接状态进行相应的提示
              if (response.data.isConnected) {
                alert("The node is successfully connected!");
              } else {
                alert("The node fail to connect!");
              }
            })
            .catch(error => {
              // 后端返回错误
              console.error("Error: Fail to obtain the status.", error);
            });
      }
    },
    delete_sub() {
      // const index = this.nodeList.indexOf(this.selectedNode);
      // if (index !== -1) {
      //   this.nodeList.splice(index, 1);
      //   this.selectedNode = null;
      // }
    },
    add_sub() {

    },
    searchNodes() {
      // 执行搜索节点的逻辑
      console.log("搜索节点:", this.searchText);
    },
    editNode() {
      this.isEditing = true;
    },
    saveNode() {
      this.isEditing = false;
      // 保存节点的逻辑
      console.log("保存节点:", this.selectedNode);

      // 更新左边导航栏中的节点信息
      const index = this.nodeList.findIndex(node => node.id === this.selectedNode.id);
      if (index !== -1) {
        this.nodeList[index].localhost = this.editedLocalhost;
        this.nodeList[index].port = this.editedPort;
        this.nodeList[index].name = `${this.editedLocalhost}:${this.editedPort}`;
      }
    }
  },
  computed: {
    filteredNodeList() {
      return this.nodeList.filter(node => {
        const searchString = this.searchText.toLowerCase();
        return (
            node.localhost.toLowerCase().includes(searchString) ||
            node.port.toLowerCase().includes(searchString)
        );
      });
    }
  }
};
</script>

<style>
.container {
  display: flex;
}

.sidebar {
  flex: 1.5;
  margin-right: 20px;
  padding: 0px;

}

.node-list {
  margin-bottom: 10px;
}

.node-item {
  margin-bottom: 10px;
}

.node-list .node-item {
  display: flex;
  align-items:center;
  margin-bottom: 2px;
}

.node-list .column-name {
  flex: 1;
  font-weight: bold;
}

.node-list span {
  flex: 1;
}

.main {
  flex: 2;
  padding: 0px;
}

.node-info textarea {
  margin-bottom: 10px;
}

.search-bar {
  display: flex;
  margin-bottom: 10px;
}

.search-bar input {
  flex: 1;
}

</style>