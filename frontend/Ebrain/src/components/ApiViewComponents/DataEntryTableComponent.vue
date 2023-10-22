<template>
  <el-table :data="dataArray" style="width: 100%" max-height="250">
    <el-table-column fixed prop="name" label="Name">
      <template #default="scope">
        <!--<el-input v-model="scope.row.name"></el-input>-->
        <el-autocomplete
          v-model="scope.row.name"
          :fetch-suggestions="QuerySearch"
          clearable
          placeholder="Please Input"
        />
      </template>
    </el-table-column>

    <el-table-column prop="value" label="Value">
      <template #default="scope">
        <el-input v-model="scope.row.value" ></el-input>
      </template>
    </el-table-column>

    <el-table-column fixed="right" label="Operations" width="120">
      <template #default="scope">
        <!--<el-button link type="primary" size="small" @click.prevent="EditRow(scope)">
          Edit
        </el-button>-->
        <el-button link type="primary" size="small" @click.prevent="DeleteRow(scope.$index)">
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
    dataArray: Array,
    nameSuggestion:Array
  },
  setup(props, ctx) {

    function EditRow(index) {
      console.log(index)
    }

    function QuerySearch(queryString, callback) {
      if( !props.nameSuggestion || props.nameSuggestion.length===0){return;}
      const res = queryString
        ? props.nameSuggestion.filter((suggestion) => {
          suggestion.value.toLowerCase() === queryString.toLowerCase()
          })
        : props.nameSuggestion
      callback(res)
    }

    function DeleteRow(index) {
      props.dataArray.splice(index, 1)
    }
    return {
      DeleteRow,
      EditRow,
      QuerySearch,

    }
  },
  mounted() {}
}
</script>
