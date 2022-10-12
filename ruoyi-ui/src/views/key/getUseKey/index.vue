<template>
  <div class="app-container">
    <el-form
      :inline="true"
      :model="queryData"
      class="demo-form-inline"
      style="margin-left: 40px"
    >
      <el-row :gutter="20">
        <el-col :span="8">ID</el-col>
        <el-col :span="8">密钥类型</el-col>
        <el-col :span="8">应用</el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8"><el-input v-model="queryData.id" placeholder="密钥id"></el-input></el-col>
        <el-col :span="8"><el-select v-model="queryData.keyType" placeholder="密钥类型">
          <el-option label="SM2" value="SM2"></el-option>
          <el-option label="RSA" value="RSA"></el-option>
        </el-select></el-col>
        <el-col :span="8"><el-input v-model="queryData.keyUse" placeholder="应用"></el-input></el-col>
      </el-row>
      <br/>
      <el-row :gutter="20">
        <el-col :span="8">生成时间</el-col>
        <el-col :span="8">使用时间</el-col>
        <el-col :span="8">过期时间</el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.createBegin"
            type="datetime"
            placeholder="起"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.useBegin"
            type="datetime"
            placeholder="起"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.expireBegin"
            type="datetime"
            placeholder="起"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.createEnd"
            type="datetime"
            placeholder="止"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.useEnd"
            type="datetime"
            placeholder="止"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
        <el-col :span="8"
          ><el-date-picker
            v-model="queryData.expireEnd"
            type="datetime"
            placeholder="止"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker
        ></el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" prop="id">
      </el-table-column>
      <!-- <el-table-column
        label="证书序列号"
        align="center"
        prop="serialNumber"
      >
      </el-table-column> -->
      <!-- <el-table-column
        label="签发机构"
        width="110"
        align="center"
        prop="issureDn"
      >
      </el-table-column> -->
      <!-- <el-table-column label="签发机构" align="center" prop="caId">
      </el-table-column> -->
      <el-table-column
        label="生成时间"
        align="center"
        prop="createTime"
      >
      </el-table-column>
      <el-table-column
        align="center"
        prop="expireTime"
        label="过期时间"
      >
      </el-table-column>
      <!-- <el-table-column
        label="使用时间"
        align="center"
        prop="useTime"
      >
        <template #default="scope">
          {{ scope.row.useTime == null ? "未使用" : scope.row.useTime }}
        </template>
      </el-table-column> -->
      <el-table-column
        label="密钥算法"
        align="center"
        prop="keyType"
      >
      </el-table-column>
      <el-table-column label="公钥" align="center" width="250px" prop="publicKey">
      </el-table-column>
      <!-- <el-table-column
        label="密钥名称"
        align="center"
        prop="keyName"
      >
      </el-table-column> -->
      <!-- <el-table-column label="是否有效" align="center" prop="valid">
        <template #default="scope">
          <span>{{ scope.row.valid == 1 ? "有效" : "无效" }}</span>
        </template>
      </el-table-column> -->
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :page-size="pageSize"
      :current-page="currentPage"
      background
      layout="prev, pager, next"
      :total="totalCount"
    >
    </el-pagination>
  </div>
</template>

<script>
import { getAllUseKeyPair,getKeyPariByCondition } from "@/api/system/sercetKey/useKey";

export default {
  filters: {},
  data() {
    return {
      list: [],
      listLoading: true,
      // 当前页
      currentPage: 1,
      // 总条数
      totalCount: 100,
      // 每页显示几条
      pageSize: 10,
      queryData: {
        id: "",
        keyType: "",
        keyUse: "",
        createBegin: "",
        createEnd: "",
        useBegin: "",
        useEnd: "",
        expireBegin: "",
        expireEnd: "",
        currentPage: 1,
        pageSize: 10,
      },
      findFlag: false,
    };
  },
  created() {
    this.getAllUseKeyPair(this.currentPage, this.pageSize);
  },

  methods: {
    // fetchData() {
    //   this.listLoading = true;
    //   getList().then((response) => {
    //     this.list = response.data.items;
    //     this.listLoading = false;
    //   });
    // },
    getAllUseKeyPair(n1, n2) {
      let data = {
        currentPage: n1,
        pageSize: n2,
      };
      getAllUseKeyPair(data)
        .then((res) => {
          this.list = res.data.records;
          this.totalCount = res.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryData.currentPage = val;
      if (!this.findFlag) {
        this.getAllUseKeyPair(this.currentPage, this.pageSize);
      } else {
        this.onSubmit();
      }
    },
    onSubmit() {
      this.findFlag = true;
      getKeyPariByCondition(this.queryData)
        .then((res) => {
          this.list = res.data.records;
          this.totalCount = res.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>
