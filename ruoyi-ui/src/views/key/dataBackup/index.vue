<template>
  <div class="app-container">
    <div style="margin: 20px"></div>
    <el-form
      :inline="true"
      :model="dataMessage"
      class="demo-form-inline"
      style="margin-left: 40px"
    >
      <el-form-item label="主机地址">
        <el-select
          v-model="dataMessage.checkedIp"
          placeholder="主机地址"
          @change="ipChange"
        >
          <el-option
            v-for="(item, index) in ip"
            :key="index"
            :label="item.ip"
            :value="item.ip"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据库">
        <el-select
          v-model="dataMessage.checkedDatabase"
          placeholder="数据库"
          :disabled="databaseAble"
          @change="databaseChange"
        >
          <el-option
            v-for="(item, index) in database"
            :key="index"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据表">
        <el-select
          v-model="dataMessage.checkedDatatable"
          placeholder="数据表"
          :disabled="datatableAble"
          @change="datatableChange"
        >
          <el-option
            v-for="(item, index) in datatable"
            :key="index"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">备份</el-button>
        <a href=""></a>
      </el-form-item>
      <el-form-item>
        <el-upload
          class="inline-block"
          ref="upload"
          action="#"
          :on-change="uploadFile"
          :auto-upload="false"
          :show-file-list="false"
        >
          <el-button type="primary">选取文件</el-button>
        </el-upload>
        <el-button
          style="margin-left: 10px"
          type="success"
          @click="submitUpload"
          >数据恢复</el-button
        ></el-form-item
      >
    </el-form>

    <el-table
      v-fit-columns
      :data="backupRecords"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="id" prop="id"> </el-table-column>
      <el-table-column label="操作员" align="center" prop="operater">
      </el-table-column>
      <el-table-column label="主机地址" align="center" prop="ip">
      </el-table-column>
      <el-table-column label="数据库" align="center" prop="databaseName">
      </el-table-column>
      <el-table-column label="数据表" align="center" prop="tableName">
      </el-table-column>
      <el-table-column label="时间" align="center" prop="opTime">
      </el-table-column>
      <el-table-column label="下载链接" align="center" prop="href">
        <template slot-scope="scope">
          <a :href="scope.row.href" target="_blank">下载</a>
        </template>
      </el-table-column>
      <el-table-column label="恢复" align="center" prop="id">
        <template slot-scope="scope">
          <el-button
            @click="restore(scope.row.id)"
            target="_blank"
            type="primary"
            icon="el-icon-folder"
            >恢复</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-table-column label="备注" align="center" prop="note"> </el-table-column>
    <el-dialog title="选择ip" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="主机地址" :label-width="formLabelWidth">
          <el-select v-model="dataMessage.checkedIp" placeholder="主机地址">
            <el-option
              v-for="(item, index) in ip"
              :key="index"
              :label="item.ip"
              :value="item.ip"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doRestore">确 定</el-button>
      </div>
    </el-dialog>
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
import {doBackupData,getDatabaseByIp,getDatatableByBase,getAllData,getDataBackupRecords,restoreData,restoreDataById} from "@/api/system/sercetKey/dataBackupApi"
export default {
  data() {
    return {
      defaultProps: {
        children: "children",
        label: "label",
      },
      ip: [
        { ip: "192.168.8.167", value: "slave-db-dev" },
        { ip: "192.168.8.169", value: "master-db-dev" },
        { ip: "192.168.8.168", value: "backup-db-dev" },
      ],
      database: [],
      datatable: [],
      dataMessage: {
        checkedIp: "",
        checkedDatabase: "",
        checkedDatatable: "",
        savePath: "",
      },
      databaseAble: true,
      datatableAble: true,
      backupRecords: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 10,
      token: {},
      fileData: {
        ip: "",
        file: "",
      },
      file: null,
      dialogFormVisible: false,
      dataId: 0,
    };
  },
  created() {
    this.getDataBackupRecords(this.currentPage);
  },
  watch: {},

  methods: {
    // 执行备份操作，会把sql文件上传到服务器上
    onSubmit() {
      if (
        this.dataMessage.checkedIp == "" ||
        this.dataMessage.checkedIp == null
      ) {
        this.$message({
          message: "请输入ip地址！",
          type: "error",
        });
      } else if (
        this.dataMessage.checkedDatabase == "" ||
        this.dataMessage.checkedDatabase == null
      ) {
        this.$message({
          message: "请选择数据库！",
          type: "error",
        });
      } else {
        const loading = this.$loading({
          lock: true,
          text: "导出中，请稍等...",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.7)",
        });
        let data = {
          ip: this.dataMessage.checkedIp,
          database: this.dataMessage.checkedDatabase,
          datatable: this.dataMessage.checkedDatatable,
        };
        doBackupData(data)
          .then((res) => {
            loading.close();
            this.$message({
              message: "导出成功！",
              type: "success",
            });
            // 刷新页面
            this.$router.go(0);
          })
          .catch((err) => {
            this.$message({
              message: err,
              type: "error",
            });
          });
      }
    },
    // 当选择ip时，设置数据库下拉框可选
    ipChange(ip) {
      this.dataMessage.checkedIp = ip;
      let data = {
        ip: ip,
      };
      getDatabaseByIp(data)
        .then((res) => {
          this.database = res.data;
          this.databaseAble = false;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 当选择数据库时，设置数据表可选
    databaseChange(database) {
      this.dataMessage.checkedDatabase = database;
      let data = {
        ip: this.dataMessage.checkedIp,
        database: database,
      };
      getDatatableByBase(data)
        .then((res) => {
          this.datatable = res.data;
          this.datatableAble = false;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 当数据表下拉框选择后，记录当前选择的表为checkedDatatable
    datatableChange(datatable) {
      this.dataMessage.checkedDatatable = datatable;
    },
    // TODO 暂时还没有用到
    getAllData() {
      getAllData(data)
        .then((res) => {
          console.log(res.data);
          this.list = res.data.records;
          this.totalCount = res.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 当用户点击页面使，val为点击的第几页，然后再调用分页获取备份记录方法
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getDataBackupRecords(this.currentPage);
    },
    // 分页获取备份记录，传入当前页面，该方法第一次调用时val为1
    getDataBackupRecords(val) {
      let data = {
        currentPage: val,
        pageSize: this.pageSize,
      };
      getDataBackupRecords(data)
        .then((res) => {
          this.backupRecords = res.data.records;
          this.totalCount = res.data.total;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 点击上传sql文件执行恢复事件，会对选择的ip先进行判断，然后对选择的文件进行判断
    submitUpload() {
      // 如果ip没有选择，那会先弹出错误框，让用户选择ip
      if (
        this.dataMessage.checkedIp == "" ||
        this.dataMessage.checkedIp == null
      ) {
        this.$message({
          message: "请输入ip地址！",
          type: "error",
        });
      }
      // 再对是否选择文件进行判断
      else if (this.file == "" || this.file == null) {
        this.$message({
          message: "请上传文件！",
          type: "error",
        });
      }
      // 如果都选择了，那就执行上传操作，将数据库下拉框和数据表下拉框清空
      else {
        this.$confirm("确定执行恢复吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.dataMessage.checkedDatabase = "";
            this.dataMessage.checkedDatatable = "";
            // 可能会有比较大的sql恢复，所以弹出隐藏框，等到恢复完成后再关闭
            const loading = this.$loading({
              lock: true,
              text: "恢复中，请稍等...",
              spinner: "el-icon-loading",
              background: "rgba(0, 0, 0, 0.7)",
            });
            restoreData(this.fileData)
              .then((res) => {
                // 此时数据已经恢复成功了，关闭隐藏层
                loading.close();
                this.$message({
                  message: "数据恢复成功！",
                  type: "success",
                });
                (this.file = ""), (this.fileData = {});
                console.log(res);
              })
              .catch((err) => {
                console.error(err);
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消恢复",
            });
          });
      }
    },
    // 上传文件事件，选择文件，将选择的ip和file封装成为formData对象然后传递给this.fileData
    uploadFile(item) {
      let data = new FormData();
      let file = item.raw;
      this.file = file;
      data.append("ip", this.dataMessage.checkedIp);
      data.append("file", file);
      this.fileData = data;
    },
    // 恢复sql数据事件
    restore(id) {
      this.dialogFormVisible = true;
      this.dataId = id;
    },
    doRestore() {
      // 如果ip没有选择，那会先弹出错误框，让用户选择ip
      if (
        this.dataMessage.checkedIp == "" ||
        this.dataMessage.checkedIp == null
      ) {
        this.$message({
          message: "请输入ip地址！",
          type: "error",
        });
      } else {
        this.$confirm("确定执行恢复吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            const loading = this.$loading({
              lock: true,
              text: "恢复中，请稍等...",
              spinner: "el-icon-loading",
              background: "rgba(0, 0, 0, 0.7)",
            });
            let data = new FormData();
            data.append("id", this.dataId);
            data.append("ip", this.dataMessage.checkedIp);
            restoreDataById(data)
              .then((res) => {
                loading.close();
                this.dialogFormVisible = false
                this.dataMessage.checkedIp = ""
                this.$message({
                  message: "恢复成功！",
                  type: "success",
                });
              })
              .catch((err) => {
                console.error(err);
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消恢复",
            });
          });
      }
    },
  },
};
</script>
<style>
.inline-block {
  display: inline-block;
}
</style>

