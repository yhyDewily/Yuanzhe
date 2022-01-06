<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="序列号" prop="serialNumber">
        <el-input
          v-model="queryParams.serialNumber"
          placeholder="请输入序列号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="颁发者" prop="issuerDn">
        <el-select v-model="queryParams.issuerDn" placeholder="请选择颁发者" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_issuers"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="生效时间" prop="startDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择生效时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="失效时间" prop="finalDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.finalDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择失效时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="变动时间" prop="finalDate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.finalDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择变动时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="使用者" prop="subjectDn">
        <el-input
          v-model="queryParams.subjectDn"
          placeholder="请输入使用者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签名算法" prop="signatureAlgorithm">
        <el-select v-model="queryParams.signatureAlgorithm" placeholder="请选择签名算法" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_signature_algorithm"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:cer:add']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:cer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:cer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:cer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="序列号" align="center" prop="serialNumber" />
      <el-table-column label="颁发者" align="center" prop="issuerDn">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_issuers" :value="scope.row.issuerDn"/>
        </template>
      </el-table-column>
      <el-table-column label="生效时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失效时间" align="center" prop="finalDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="变动时间" align="center" prop="modifyDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用者" align="center" prop="subjectDn" />
      <el-table-column label="签名算法" align="center" prop="signatureAlgorithm">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_signature_algorithm" :value="scope.row.signatureAlgorithm"/>
        </template>
      </el-table-column>
      <el-table-column label="证书签名" align="center" prop="signature" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="撤销理由" align="center" prop="revokeReason" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:cer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:cer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增证书 -->
    <!--
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-upload
        class="upload-demo"
        drag
        action=""
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </>el-dialog>
    -->
    <!-- 添加或修改证书管理对话框   -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="序列号" prop="serialNumber">
          <el-input v-model="form.serialNumber" placeholder="请输入序列号" />
        </el-form-item>
        <el-form-item label="颁发者" prop="issuerDn">
          <el-select v-model="form.issuerDn" placeholder="请选择颁发者">
            <el-option
              v-for="dict in dict.type.sys_issuers"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生效时间" prop="startDate">
          <el-date-picker clearable size="small"
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生效时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="失效时间" prop="finalDate">
          <el-date-picker clearable size="small"
            v-model="form.finalDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择失效时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="使用者" prop="subjectDn">
          <el-input v-model="form.subjectDn" placeholder="请输入使用者" />
        </el-form-item>
        <el-form-item label="公钥" prop="publicKey">
          <el-input v-model="form.publicKey" placeholder="请输入公钥" />
        </el-form-item>
        <el-form-item label="签名算法" prop="signatureAlgorithm">
          <el-select v-model="form.signatureAlgorithm" placeholder="请选择签名算法">
            <el-option
              v-for="dict in dict.type.sys_signature_algorithm"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证书签名" prop="signature">
          <el-input v-model="form.signature" placeholder="请输入证书签名" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listCer, getCer, delCer, addCer, updateCer } from "@/api/system/cer";

export default {
  name: "Cer",
  dicts: ['sys_issuers', 'sys_signature_algorithm'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 证书管理表格数据
      cerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNumber: null,
        issuerDn: null,
        startDate: null,
        finalDate: null,
        modifyDate: null,
        subjectDn: null,
        signatureAlgorithm: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serialNumber: [
          { required: true, message: "序列号不能为空", trigger: "blur" }
        ],
        issuerDn: [
          { required: true, message: "颁发者不能为空", trigger: "change" }
        ],
        startDate: [
          { required: true, message: "生效时间不能为空", trigger: "blur" }
        ],
        finalDate: [
          { required: true, message: "失效时间不能为空", trigger: "blur" }
        ],
        modifyDate: [
          { required: true, message: "变动时间不能为空", trigger: "blur" }
        ],
        subjectDn: [
          { required: true, message: "使用者不能为空", trigger: "blur" }
        ],
        signatureAlgorithm: [
          { required: true, message: "签名算法不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询证书管理列表 */
    getList() {
      this.loading = true;
      listCer(this.queryParams).then(response => {
        this.cerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        issuerDn: null,
        startDate: null,
        finalDate: null,
        modifyDate: null,
        subjectDn: null,
        publicKey: null,
        signatureAlgorithm: null,
        signature: null,
        status: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.version)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加证书管理";
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const versions = row.version || this.ids;
      this.$modal.confirm('是否确认删除证书管理编号为"' + versions + '"的数据项？').then(function() {
        return delCer(versions);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/cer/export', {
        ...this.queryParams
      }, `cer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
