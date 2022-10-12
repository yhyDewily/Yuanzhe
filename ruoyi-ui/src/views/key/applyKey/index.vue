<template>
  <div class="app-container">
    <div style="margin: 20px"></div>
    <el-form
      :inline="true"
      :model="keyPair"
      class="demo-form-inline"
      style="margin-left: 40px"
    >
      <el-form-item label="密钥名称">
        <el-input v-model="keyPair.keyName" placeholder="密钥名称"></el-input>
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
      <el-table-column align="center" label="公钥" width="640" prop="publicKey">
      </el-table-column>
      <el-table-column
        label="私钥"
        width="645"
        align="center"
        prop="privateKey"
      >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {applyKeyPair} from "@/api/system/sercetKey/applyKeyApi";
export default {
  data() {
    return {
      defaultProps: {
        children: "children",
        label: "label",
      },
      keyPair: {
        type: "",
        keyName: "",
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
      console.log(this.keys);
      applyKeyPair(this.keyPair)
        .then((res) => {
          let array = [
            {
              publicKey: res.data.publicKey,
              privateKey: res.data.privateKey,
            },
          ];
          this.keys = array;
          console.log(this.keys);
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

