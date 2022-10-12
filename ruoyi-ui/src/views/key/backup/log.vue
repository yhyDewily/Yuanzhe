<template>
  <div class="app-container">
    <span>开始时间：</span>
    <el-date-picker
      v-model="beginDate"
      type="datetime"
      placeholder="选择日期时间"
      value-format="yyyy-MM-dd HH:mm:ss">
    </el-date-picker>
    <span style="margin-left:50px">结束时间：</span>
    <el-date-picker
      v-model="endDate"
      type="datetime"
      placeholder="选择日期时间"
      value-format="yyyy-MM-dd HH:mm:ss">
    </el-date-picker>
    <el-button style="margin-left:30px" type="primary" @click="getLogs">查询</el-button>
    <div style="margin:30px"></div>
    <el-table
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="200" prop="id">
      </el-table-column>
      <el-table-column
        label="密钥算法"
        width="200"
        align="center"
        prop="keyType"
      >
      </el-table-column>
      <el-table-column label="生成密钥数量" width="400" align="center" prop="keyNum">
      </el-table-column>
      <el-table-column
        label="操作时间"
        width="485"
        align="center"
        prop="createTime"
      >
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
import {getAllBackupLog} from "@/api/system/sercetKey/backupApi";

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
      beginDate:"",
      endDate:""
    };
  },
  created() {
    this.getAllBackupKeyPair(this.currentPage, this.pageSize,this.beginDate,this.endDate);
  },

  methods: {
    // fetchData() {
    //   this.listLoading = true;
    //   getList().then((response) => {
    //     this.list = response.data.items;
    //     this.listLoading = false;
    //   });
    // },
    getAllBackupKeyPair(n1, n2,begin,end) {
      let data = {
        currentPage: n1,
        pageSize: n2,
        beginDate:begin,
        endDate:end
      };
      getAllBackupLog(data)
        .then((res) => {
          this.list = res.data.data.records;
          this.totalCount = res.data.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getAllBackupKeyPair(this.currentPage, this.pageSize,this.beginDate,this.endDate);
    },
    getLogs(){
        console.log(this.beginDate);
       this.getAllBackupKeyPair(this.currentPage, this.pageSize,this.beginDate,this.endDate);
    }
  },
};
</script>
