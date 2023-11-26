<template>
  <header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
    <div class="monitor-container">
      <div class="monitor-item">
        <i :class="iconTypeC" style="font-size: 70px;"></i>
        <div style="display: flex; flex-direction: column; align-items: flex-start; margin-top: 10px;">
          <el-input v-model="connectPort" @focus="iconTypeC = 'bi bi-cloud-arrow-up'" placeholder="Enter port No." style="width: 200px;" />
          <br />
          <el-button @click="connectNode" type="primary">Connect</el-button>
        </div>
      </div>
      <div class="monitor-item">
        <i :class="iconTypeAdd" style="font-size: 70px;"></i>
        <div style="display: flex; flex-direction: column; align-items: flex-start; margin-top: 10px;">
          <el-input v-model="newNodeRemark" @focus="iconTypeAdd = 'bi bi-plus-circle'" ref="remarkInput" placeholder="Enter parent name" style="width: 200px;" />
          <br />
          <el-button @click="addNode" type="primary">Add</el-button>
        </div>
      </div>
      <div class="monitor-item">
<!--        <i :class="bi bi-cloud-check" style="font-size: 70px;"></i>-->
        <i :class="iconTypeCS" style="font-size: 70px;"></i>
        <div style="display: flex; flex-direction: column; align-items: flex-start; margin-top: 10px;">
          <el-input v-model="connectionIP" @focus="iconTypeCS = 'bi bi-cloud'" placeholder="Enter ip address" style="width: 200px;" />
          <el-input v-model="connectionPort"  @focus="iconTypeCS = 'bi bi-cloud'" placeholder="Enter port no." style="width: 200px;" />
          <br />
          <el-button @click="checkConnectionStatus" type="primary">Check</el-button>
        </div>
      </div>
    </div>
  </header>
  <div class="container" :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
    <div class="sidebar">
      <h2>Menu</h2>
      <div class="search-bar">
        <el-input v-model="searchText" placeholder="Type names..." />
      </div>
      <div class="node-list">
        <div class="node-item" v-for="node in filteredNodeList" :key="node.id">
          <span @click="selectNode(node)">{{ node.remark }}</span>
          <el-button type="primary" @click="deleteNode(node)" style="display: flex">Delete</el-button>
<!--          <el-button type="primary" @click="showDrawer=true">Edit</el-button>-->
<!--          <el-drawer title="Edit Node" v-model="showDrawer" @close="closeDrawer" direction="ttb">-->
<!--            <el-form ref="editForm" :model="editForm" label-width="120px">-->
<!--              <el-form-item label="Address Before">-->
<!--                <el-input v-model="editForm.addressBefore"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="Address After">-->
<!--                <el-input v-model="editForm.addressAfter"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item>-->
<!--                <el-button @click="cancelEdit">Cancel</el-button>-->
<!--                <el-button type="primary" @click="editAddress(node)">Submit</el-button>-->
<!--              </el-form-item>-->
<!--            </el-form>-->
<!--          </el-drawer>-->
        </div>
      </div>
    </div>

    <div class="main">

<!--      <h2>Node Information</h2>-->
      <div v-if="selectedNode" class="node-info">
        <h2>
          {{ selectedNode.remark }}
<!--          <template v-if="isEditing">-->
<!--            <el-input v-model="editedRemark" />-->
<!--          </template>-->
<!--          <template v-else>-->
<!--            {{ selectedNode.remark }}-->
<!--          </template>-->
        </h2>
<!--        <p>Localhost: {{ selectedNode.localhost }}</p>-->
<!--        <p>Port: {{ selectedNode.port }}</p>-->
<!--        <el-button type="primary" style="margin-bottom: 10px;" v-if="!isEditing" @click="editNode">Edit</el-button>-->
        <div class="sub-nodes">
          <h3>Sub Nodes Information:</h3>
          <el-collapse v-if="subNodes.length > 0">
          <el-collapse-item v-for="subNode in subNodes" :key="subNode" :title="subNode">
            <el-button type="primary" @click="showDrawer=true">Edit</el-button>
            <el-drawer title="Edit Node" v-model="showDrawer" @close="closeDrawer" direction="ttb">
              <el-form ref="editForm" :model="editForm" label-width="120px">
                <el-form-item label="Address Before">
                  <el-input v-model="editForm.addressBefore"></el-input>
                </el-form-item>
                <el-form-item label="Address After">
                  <el-input v-model="editForm.addressAfter"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="cancelEdit">Cancel</el-button>
                  <el-button type="primary" @click="editAddress(node)">Submit</el-button>
                </el-form-item>
              </el-form>
            </el-drawer>
          </el-collapse-item>
          </el-collapse>
<!--          <el-button type="primary" v-if="isEditing" @click="saveNode">Save</el-button>-->
<!--          <el-textarea v-model="selectedNode.text" rows="4" cols="50" placeholder="Add some details..."></el-textarea>-->
<!--          <br>-->
  <!--        <el-button type="primary" @click="connectNode">Connect</el-button>-->
          <el-button type="primary" @click="add_sub">Add Sub Node</el-button>
          <el-button type="primary" @click="delete_sub">Delete Sub Node</el-button>
          <el-button type="primary" @click="clear_sub">Delete All Sub Node</el-button>
          <br><br>
          <div v-if="showAddSubForm" class="add-sub-form">
  <!--          <el-input v-model="newSubNodeParent" placeholder="Parent" :style="{ width: '50%' }"/><br>-->
            <el-input v-model="newSubNodeIp" placeholder="IP" :style="{ width: '50%' }"/><br>
            <el-input v-model="newSubNodePort" placeholder="Port" :style="{ width: '50%' }"/><br><br>
            <el-button type="primary" @click="confirmAddSub">Confirm</el-button>
            <el-button type="text" @click="cancelAddSub">Cancel</el-button>
          </div>
          <br>
          <div v-if="showDeleteForm" class="delete-form">
  <!--          <el-input v-model="deleteForm.parent" placeholder="Parent" :style="{ width: '50%' }"/><br>-->
            <el-input v-model="deleteAddress" placeholder="Address" :style="{ width: '50%' }"/><br><br>
            <el-button type="primary" @click="confirmDelete">Confirm</el-button>
            <el-button type="text" @click="cancelDelete">Cancel</el-button>
          </div>
        </div>
        <div>
          <h3>Node Details</h3>
          <div>
            <el-button type="primary" @click="refresh">Refresh Details</el-button>
            <div v-if="showReForm" class="add-sub-form">
              <el-input v-model="reIP" placeholder="IP" :style="{ width: '50%' }"/><br>
              <el-input v-model="rePort" placeholder="Port" :style="{ width: '50%' }"/><br><br>
              <el-button type="primary" @click="selectNodeDetails">Confirm</el-button>
              <el-button type="text" @click="cancelRefresh">Cancel</el-button>
            </div>
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
      nodeList: [],
      newNodeRemark: '',
      selectedNode: null,
      searchText: '',
      isEditing: false,
      responseData: [],
      nodeDetails: [],
      nodeDetailsLoading: false,
      showAddSubForm: false,
      newSubNodeParent: '',
      newSubNodeIp: '',
      newSubNodePort: '',
      showDeleteForm: false,
      deleteAddress: '',
      connectionStatus: '',
      connectionIP:'',
      connectionPort:'',
      iconTypeCS:'bi bi-cloud',
      iconTypeC:'bi bi-cloud-arrow-up',
      iconTypeAdd:'bi bi-plus-circle',
      connectPort:'',
      loading: true,
      success: false,
      error: false,
      subNodes: [],
      showDrawer: false,
      editForm: {
        addressBefore: '',
        addressAfter: ''
      },
      reIP:'',
      rePort:'',
      showReForm: false
    };
  },
  // created() {
  //   this.checkConnectionStatus();
  //   setInterval(() => {
  //     this.checkConnectionStatus();
  //   }, 5000);
  // },
  // mounted() {
  //   this.fetchNodeList();
  // },
  methods: {
    addNode() {
      if (this.newNodeRemark) {
        const newNode = {
          id: this.nodeList.length + 1,
          remark: this.newNodeRemark,
          text: ''
        };
        console.log('push done');
        const formData = new FormData();
        formData.append('parent', newNode.remark);
        console.log('append done');
        fetch('http://localhost:8081/addParent', {
          method: 'POST',
          body: formData
        })
            .then(response => {
              if (response.ok) {
                console.log('Success Add Node');
                this.nodeList.push(newNode);
                this.$refs.remarkInput.focus();
                this.newNodeRemark = '';
              } else {
                console.error('Fail');
                this.iconTypeAdd='bi bi-x-circle-fill';
              }
            })
            .catch(error => {
              console.error('Error:', error);
              this.iconTypeAdd='bi bi-question-circle-fill';
            });
        console.log('send done');
      }
    },
    closeDrawer() {
      this.showDrawer = false;
      this.clearForm();
    },
    clearForm() {
      this.editForm.addressBefore = '';
      this.editForm.addressAfter = '';
    },
    cancelEdit() {
      this.closeDrawer();
    },
    editAddress(node){
      const formData = new FormData();
      formData.append('parent', node.remark);
      formData.append('addressBefore', this.editForm.addressBefore);
      formData.append('addressAfter', this.editForm.addressAfter);


      fetch('http://localhost:8081/updateNode', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              console.log('Update successful');
            } else {
              console.error('Update failed');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });

      this.closeDrawer();
    },
    selectNode(node) {
      this.selectedNode = node;
      this.getSubNodes(node.remark);
      this.nodeDetails = '';
    },
    refresh(){
      this.showReForm=true;
    },
    cancelRefresh() {
      this.reIP = '';
      this.rePort = '';
      this.showReForm = false;
    },
    getSubNodes(remark) {
      const formData = new FormData();
      formData.append('parent', remark);
      formData.append('address', 'all');

      fetch('http://localhost:8081/getNodeInformation', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error('Failed to retrieve sub nodes.');
            }
          })
          .then(data => {
            this.subNodes = data;
          })
          .catch(error => {
            console.error(error);
          });
    },
    selectNodeDetails() {
      this.nodeDetailsLoading = true;
      const formData = new FormData();
      formData.append('parent', this.selectedNode.remark);
      formData.append('ip', this.reIP);
      formData.append('port', this.rePort);

      fetch('http://localhost:8081/getDetailNodeInformation', {
        method: 'POST',
        body: formData
      })
          .then(response => response.json())
          .then(data => {
            this.showReForm = false;
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
        formData.append('parent', node.remark);

        fetch('http://localhost:8081/removeParent', {
          method: 'POST',
          body: formData
        })
          .then(response => {
            if (response.ok) {
              console.log('Success');
            } else {
              console.error('Fail, try again');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });

        this.nodeList.splice(index, 1);
        if (this.selectedNode === node) {
          this.selectedNode = null;
        }
      }
    },

    // editNode() {
    //   this.isEditing = true;
    // },
    //
    // saveNode() {
    //   this.selectedNode.remark = this.editedRemark;
    //   this.isEditing = false;
    // },

    connectNode() {
      const formData = new FormData();
      formData.append('port', this.connectPort);
      fetch('http://localhost:8081/connectServer', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              this.success = true;
              this.iconTypeC='bi bi-cloud-check-fill';
              this.fetchNodeList();
              this.connectPort = '';
              console.log('Connected');
            } else {
              this.error = true;
              this.iconTypeC='bi bi-x-circle-fill';
              console.log('The connection is down.');
            }
          })
          .catch(error => {
            this.iconTypeC='bi bi-cloud-slash'
            console.error('Error:', error);
          });
    },
    checkConnectionStatus() {
      const formData = new FormData();
      formData.append('ip', this.connectionIP);
      formData.append('port', this.connectionPort);

      fetch('http://localhost:8081/socketTest', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              this.connectionStatus = 'Connected';
              this.iconTypeCS='bi bi-cloud-check';
              this.connectionIP = '';
              this.connectionPort = '';
            } else {
              this.connectionStatus = 'Down';
              this.iconTypeCS='bi bi-cloud-minus'
            }
          })
          .catch(error => {
            this.connectionStatus = 'Error';
            this.iconTypeCS='bi bi-cloud-slash'
            console.error('Error:', error);
          });
    },

    add_sub() {
      this.showAddSubForm = true;
    },
    confirmAddSub() {
      const formData = new FormData();
      formData.append('parent', this.selectedNode.remark);
      formData.append('ip', this.newSubNodeIp);
      formData.append('port', this.newSubNodePort);

      fetch('http://localhost:8081/addChild', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              console.log('Success adding new sub node');
              this.showAddSubForm = false;
              this.getSubNodes(this.selectedNode.remark);
              this.nodeDetails();
            } else {
              console.error('Failed.');
            }
          })
          .catch(error => {
            console.error('Error:', error);
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
      formData.append('parent', this.selectedNode.remark);
      formData.append('address', this.deleteAddress);

      fetch('http://localhost:8081/removeAddress', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              console.log('Success delete');
              this.showDeleteForm = false;
              this.getSubNodes(this.selectedNode.remark);
              this.nodeDetails();

            } else {
              console.error('Failed.');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    cancelDelete() {
      this.showDeleteForm = false;
      this.deleteAddress = '';
    },
    clear_sub(){
      const formData = new FormData();
      formData.append('parent', this.selectedNode.remark);
      fetch('http://localhost:8081/removeChildren', {
        method: 'POST',
        body: formData
      })
          .then(response => {
            if (response.ok) {
              console.log('Children removed successfully.');
              this.getSubNodes(this.selectedNode.remark);
              this.nodeDetails();
            } else {
              console.error('Failed to remove children.');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    fetchNodeList() {
      fetch('http://localhost:8081/getAllParentNodes',{
        method: 'POST'
      })
          .then(response => response.json())
          .then(data => {
            this.nodeList = data.map(nodeName => ({ remark: nodeName }));
          })
          .catch(error => {
            console.error('Failed to fetch parent nodes:', error);
          });
    },
    filterNodes() {
      const searchText = this.searchText.toLowerCase();
      return this.nodeList.filter(node => {
        return node.remark.toLowerCase().includes(searchText);
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
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css");
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

.add-sub-form > el-input {
  width: 60%;
}

.delete-form > el-input {
  width: 60%;
}

.monitor-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  padding: 10px;
}

.monitor-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 2px solid #cbcbcb;
  padding: 10px;
  text-align: center;
}

</style>


