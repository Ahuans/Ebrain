<template>
  <el-table :data="dataArray" style="width: 100%" max-height="250">
    <el-table-column fixed prop="name" label="Name" />
    <el-table-column prop="value" label="Value" />
    <el-table-column fixed="right" label="Operations" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click.prevent="deleteRow(scope.$index)">
          Remove
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { ref } from 'vue'

export default {
  props: {
    dataArray:Array
  },
  setup(props, ctx) {

    function deleteRow(index) {
      props.dataArray.splice(index, 1)
    }
    return {
      deleteRow
    }
  },
  mounted() {

  }
}
</script>
