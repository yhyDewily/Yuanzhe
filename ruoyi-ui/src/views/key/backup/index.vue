<template>
  <div class="app-container">
    <el-table
      v-fit-columns
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" prop="id">
      </el-table-column>
      <el-table-column
        label="密钥算法"
        width="120"
        align="center"
        prop="keyType"
      >
      </el-table-column>
      <el-table-column label="公钥" align="center" prop="publicKey">
      </el-table-column>
      <!-- <el-table-column
        label="私钥"
        width="307"
        align="center"
        prop="privateKey"
      >
      </el-table-column> -->
      <el-table-column
        label="密钥名称"
        align="center"
        prop="keyName"
      >
      </el-table-column>
      <el-table-column
        label="是否对称"
        align="center"
        prop="keySymmetry"
      >
        <template #default="scope">
          <span>{{ scope.row.keySymmetry == 1 ? "对称" : "非对称" }}</span>
        </template>
      </el-table-column>
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
import {getAllBackupKey} from "@/api/system/sercetKey/backupApi";

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
    };
  },
  created() {
    this.getAllBackupKeyPair(this.currentPage, this.pageSize);
  },

  methods: {
    // fetchData() {
    //   this.listLoading = true;
    //   getList().then((response) => {
    //     this.list = response.data.items;
    //     this.listLoading = false;
    //   });
    // },
    getAllBackupKeyPair(n1, n2) {
      let data = {
        currentPage: n1,
        pageSize: n2,
      };
      console.log(data);
      getAllBackupKey(data)
        .then((res) => {
          console.log(res.data);
          this.list = res.data.data.records;
          this.totalCount = res.data.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getAllBackupKeyPair(this.currentPage, this.pageSize);
    },
  },
};
</script>
