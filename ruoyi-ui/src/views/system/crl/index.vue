<template>
  <div class="app-container">
    <el-form :model="queryParamset" ref="queryForm" :inline="true">
      <el-form-item
        label="生效时间："
        prop="time"
      >
        <el-date-picker
          v-model="queryParamset.time"
          type="datetime"
          placeholder="选择日期时间"
          readonly
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="间隔时间" prop="updateSpan">
        <el-select v-model="timeSelect"
                   placeholder="请选择间隔时间"
                   clearable
                   size="small">
          <el-option
            v-for="dict in spanDict"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
    </el-form>


    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" @click="createCrlButton()">产生</el-button>
      </el-col>
      <!--
      <el-col :span="1.5">
        <el-button type="primary" @click="updateSpanButton()" disabled>保存</el-button>
      </el-col>
      -->
    </el-row>

    <div style="margin-top: 15px;" width="30px">
      <el-input v-model="crlInput" class="input-with-button" size="big" readonly>
        <el-button slot="append" icon="el-icon-download" @click="downloadCrlButton()"></el-button>
      </el-input>
    </div>

  </div>
</template>

<script>
import { createCrl, setUpdateSpan, getCrlUrl } from "@/api/system/crl";

export default {
  name: "Crl",

  data() {
    return {
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      crlInput: "",
      queryParamset: {
        order: 1,
        time: undefined,//日期以及时间
        title: undefined,
        content: undefined,
        emotion: undefined,
        emergency: undefined,
        risk: undefined,
        warning_value: undefined,
        warning: undefined,
        rs: undefined,
        area: undefined,
        field: undefined,
        zdtfsj: false,
        gfsj: false,
        qtxsj: false,
        warning_sync: false,
        ds: undefined,
      },
      // 更新间隔下拉列表
      spanDict: [{
        value: '1',
        label: '1天'
      }, {
        value: '3',
        label: '3天'
      }, {
        value: '7',
        label: '7天'
      }, {
        value: '14',
        label: '14天'
      }, {
        value: '30',
        label: '30天'
      }],
      timeSelect:"7",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serialNumber: [
          { required: true, message: "序列号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    setInterval(() => {
      this.getNowTime();
    }, 500);
    getCrlUrl().then((response) => {
      this.crlInput = response.data;
    });
  },

  methods: {

    // 下载crl文件
    downloadCrlButton(){
      let a = document.createElement('a')
      a.href =this.crlInput
      a.click();
    },
    // 新建 crl 按钮
    createCrlButton(){
      const time = this.queryParamset.time;
      const updateSpan = this.timeSelect;
      createCrl({ time: time, updateSpan: updateSpan }).then((response) => {
        this.$modal.msgSuccess("CRL 生成成功");
      });
    },
    // 更新间隔时间按钮
    updateSpanButton(){
      const updateSpan = this.timeSelect;
      setUpdateSpan({updateSpan: updateSpan}).then((response) => {
        this.$modal.msgSuccess("CRL 更新时间设置成功");
      })
    },
    // 实时更新时间
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); //获取年
      var month = now.getMonth();//获取月
      var date = now.getDate();//获取日
      var hours = now.getHours();//获取小时
      var minutes = now.getMinutes();//获取分钟
      var seconds = now.getSeconds();//获取秒
      month = month + 1;
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
      this.$set(this.queryParamset, "time", defaultDate);//赋值给表单里面定义的时间
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        version: null,
        serialNumber: null,
        status: 0
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      genCer().then(response=>{
        console.log(response)
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const version = row.version || this.ids
      getCer(version).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改证书管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.version != null) {
            updateCer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/cer/export', {
        ...this.queryParams
      }, `cer_${new Date().getTime()}.xlsx`)
    },
    /** 根据状态码获取状态信息*/
    getStatusByCode(code) {
      switch (code) {
        case 1:
          return "正常"
        case 2:
          return "撤销"
        case 3:
          return "挂起"
      }
    },
    /** 过滤器标签*/
    filterTag(value, row) {
      return row.tag === value;
    },
    handleType(type) {
      switch (type) {
        case "正常":
          return "success"
        case "撤销":
          return "danger"
        case "挂起":
          return "warning"
      }
    }
  }
};
</script>

<style>
.input-with-button {
  width: 460px;
}
</style>
