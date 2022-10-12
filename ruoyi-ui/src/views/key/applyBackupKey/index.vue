<template>
  <div class="app-container">
    <div style="margin: 20px"></div>
    <el-form :inline="true" :model="keyPair" class="demo-form-inline" style="margin-left:40px">
      <el-form-item label="密钥数量">
        <el-input v-model="keyPair.keyNum" placeholder="密钥数量"></el-input>
      </el-form-item>
      <el-form-item label="加密算法">
        <el-select v-model="keyPair.type" placeholder="加密算法">
          <el-option label="SM2" value="SM2"></el-option>
          <el-option label="RSA" value="RSA"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">生成</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="keys"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="公钥" width="645" prop="publicKey">
      </el-table-column>
      <el-table-column
        label="私钥"
        width="640"
        align="center"
        prop="privateKey"
      >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getKeyPairList} from "@/api/system/sercetKey/applyBackupKeyApi";
export default {
  data() {
    return {
      defaultProps: {
        children: "children",
        label: "label",
      },
      keyPair: {
        type: "",
        keyNum: 0,
      },
      keys: [],
    };
  },
  watch: {
    filterText(val) {
      // this.$refs.tree2.filter(val);
    },
  },

  methods: {
    onSubmit() {
      let data = new FormData()
      data.append('type',this.keyPair.type)
      data.append('keyNum',this.keyPair.keyNum)
      getKeyPairList(data)
        .then((res) => {
          console.log("data:",res.data);
          this.keys=res.data
          console.log("keys:",this.keys);
          this.$message({
            message: "密钥生成成功！",
            type: "success",
          });
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

