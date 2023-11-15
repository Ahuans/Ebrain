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

        <div v-if="showAddSubForm" class="add-sub-form">
          <el-input v-model="newSubNodeParent" placeholder="Parent" />
          <el-input v-model="newSubNodeIp" placeholder="IP" />
          <el-input v-model="newSubNodePort" placeholder="Port" />
          <el-button type="primary" @click="confirmAddSub">Confirm</el-button>
          <el-button type="text" @click="cancelAddSub">Cancel</el-button>
        </div>

        <div v-if="showDeleteForm" class="delete-form">
          <el-input v-model="deleteForm.parent" placeholder="Parent" />
          <el-input v-model="deleteForm.address" placeholder="Address" />
          <el-button type="primary" @click="confirmDelete">Confirm</el-button>
          <el-button type="text" @click="cancelDelete">Cancel</el-button>
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
      showDeleteForm: false,
      deleteForm: {
        parent: '',
        address: ''
      }
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
      // 没有相关接口
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
            this.showAddSubForm = false;
            this.nodeDetails();
          })
          .catch(error => {
            console.error('添加子节点失败:', error);
          });

    },
    cancelAddSub() {
      this.newSubNodeParent = '';
      this.newSubNodeIp = '';
      this.newSubNodePort = '';
      this.showAddSubForm = false;
    },
    delete_sub() {
      this.showDeleteForm = true;
    },
    confirmDelete() {
      const formData = new FormData();
      formData.append('parent', this.deleteForm.parent);
      formData.append('address', this.deleteForm.address);

      fetch('http://localhost:8081/removeAddress', {
        method: 'POST',
        body: formData
      })
          .then(response => response.json())
          .then(data => {
            console.log('删除地址成功:', data);
            this.showDeleteForm = false;
            this.nodeDetails();
          })
          .catch(error => {
            console.error('删除地址失败:', error);
          });
    },
    cancelDelete() {
      this.showDeleteForm = false;
      this.deleteForm.parent = '';
      this.deleteForm.address = '';
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


