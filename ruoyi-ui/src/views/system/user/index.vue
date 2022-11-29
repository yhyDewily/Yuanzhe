<template>
  <div class="app-container">
      <!--用户数据-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="用户名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
<!--          <el-form-item label="手机号码" prop="phonenumber">-->
<!--            <el-input-->
<!--              v-model="queryParams.phonenumber"-->
<!--              placeholder="请输入手机号码"-->
<!--              clearable-->
<!--              size="small"-->
<!--              style="width: 240px"-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
<!--          </el-form-item>-->
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
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
              v-hasPermi="['system:user:add']"
            >新增</el-button>
          </el-col>
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="success"-->
<!--              plain-->
<!--              icon="el-icon-edit"-->
<!--              size="mini"-->
<!--              :disabled="single"-->
<!--              @click="handleUpdate"-->
<!--              v-hasPermi="['system:user:edit']"-->
<!--            >修改</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="danger"-->
<!--              plain-->
<!--              icon="el-icon-delete"-->
<!--              size="mini"-->
<!--              :disabled="multiple"-->
<!--              @click="handleDelete"-->
<!--              v-hasPermi="['system:user:remove']"-->
<!--            >删除</el-button>-->
<!--          </el-col>-->
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:user:import']"
            >导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading"
                  :data="userList"
                  :border="true"
                  :stripe="true"
                  @selection-change="handleSelectionChange">
<!--          <el-table-column type="selection" width="50" align="center" />-->
          <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="columns[0].visible" sortable/>
          <el-table-column label="用户名称" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
<!--          <el-table-column label="用户昵称" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />-->
          <el-table-column label="设备编号" align="center" key="ukeyId" prop="ukeyId" v-if="columns[2].visible" width="200px"/>
          <el-table-column label="角色" align="center" key="roleId" prop="roleId">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.role" :value="scope.row.roleId"/>
            </template>
          </el-table-column>
<!--          <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />-->
          <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
<!--            <template slot-scope="scope">-->
<!--              <el-switch-->
<!--                v-model="scope.row.status"-->
<!--                active-value="0"-->
<!--                inactive-value="1"-->
<!--              @change="handleStatusChange(scope.row)"-->
<!--            ></el-switch>-->
<!--          </template>-->
            <template slot-scope="scope">
              <div v-if="scope.row.status==2">
                <el-tag type="info">未授权</el-tag>
              </div>
              <div v-if="scope.row.status==0">
                <el-tag type='success'>正常</el-tag>
              </div>
              <div v-if="scope.row.status==1">
                <el-tag type='danger'>已注销</el-tag>
              </div>

            </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
          <el-table-column label="生效时间" align="center" prop="effectiveTime"  width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.effectiveTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="失效时间" align="center" prop="expirationTime"  width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.expirationTime) }}</span>
            </template>
          </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope" v-if="scope.row.userId !== 1">
            <el-button
              size="mini"
              type="text"
              v-show="scope.row.menuShow"

              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:user:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              v-show="scope.row.menuShow"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:user:remove']"

            >删除</el-button>
            <el-dropdown  v-show="scope.row.menuShow" size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
              <span class="el-dropdown-link">
                <i class="el-icon-d-arrow-right el-icon--right"></i>更多
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="handleResetPwd" icon="el-icon-key"
                  v-hasPermi="['system:user:resetPwd']">重置密码</el-dropdown-item>
                <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                  v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
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

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="用户昵称" prop="nickName">-->
<!--              <el-input v-model="form.nickName" placeholder="请输入用户昵称" maxlength="30" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      -->
        <el-row>
          <el-col :span="20">
            <el-form-item label="令牌" class="selectForm" prop="ukeyId"  v-if="form.userId == undefined">




              <el-select v-model="form.ukeyId" placeholder="请选择设备编号" :required="true" @change="getname">
                <el-option
                  v-for="item in dev_list"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
                </el-option>
              </el-select>



              <el-button size="mini" type="success" style="margin-left:10px;height:40%" @click="getAllDevice2">刷新</el-button>


              <!--                          </div>-->

            </el-form-item>
          </el-col>
          <el-col :span="12">

          </el-col>

        </el-row>

        <el-row>
          <el-col :span="12">
            <!--            <el-form-item label="用户昵称" prop="nickName">-->
            <!--              <el-input v-model="form.nickName" placeholder="请输入用户昵称" maxlength="30" />-->
            <!--            </el-form-item>-->
          </el-col>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="归属部门" prop="deptId">-->
          <!--              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--        </el-row>-->
          <!--        <el-row>-->
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="手机号码" prop="phonenumber">-->
          <!--              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="邮箱" prop="email">-->
          <!--              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--        </el-row>-->
          <!--        <el-row>-->
          <el-col :span="12">

            <el-form-item v-if="form.userId == undefined" label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password autocomplete="new-password"/>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="12">
            <el-form-item label="角色" v-if="form.userId == undefined">
              <el-select v-model="form.roleIds" multiple placeholder="请选择角色" multiple-limit="1">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-form-item v-if="form.userId == undefined" label="生效时间" prop="effectiveTime">
            <el-date-picker clearable
                            v-model="form.effectiveTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择生效时间">
            </el-date-picker>
          </el-form-item>
        </el-row>
        <el-row>

          <el-form-item v-if="form.userId == undefined" label="失效时间" prop="expirationTime">
            <el-date-picker clearable
                            v-model="form.expirationTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择失效时间">
            </el-date-picker>

          </el-form-item>

        </el-row>
        <div>
          **新增操作员必须已经由外部CA签发过证书**
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {mToken} from '@/assets/js/mToken';
// import {FISECKEY,SKFKEY} from '@/assets/js/fiseckey'

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex','role'],
  components: { Treeselect },
  data() {
    return {
      // 加载 U 盾设备所依赖的环境
      token: null,
      //下属
      subRoleId:null,
      menuShow:true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      dev_list:[],
      outputCertData:[],
      dev_id:'',
      dev_name:'',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {},
      // 角色参数
      roleIds: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
        // userName: [
        //   { required: true, message: "用户名称不能为空", trigger: "blur" },
        //   { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        // ],
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        effectiveTime: [
          { required: true, message: "生效时间不能为空", trigger: "blur" }
        ],
        expirationTime: [
          { required: true, message: "失效时间不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        // role: [
        //
        //   { min: 1, max: 1, message: '只能选一个角色', trigger: 'blur' }
        // ]
      }
    };
  },
  beforeCreate() {

  },
  created() {
    // while (this.subRoleId!==null){
    //
    // }



      // var userName = this.$store.state.user.name;
      // console.log(userName)
      this.getList();
      this.getConfigKey("sys.user.initPassword").then(response => {
        this.initPassword = '';
      });
      this.getAllDevice2();




  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.reset();
      getUser().then(response => {
        // console.log(response)
        this.loading = true;
        this.subRoleId = response.roles[0].roleId;
        listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            // console.log(response.rows)
            this.userList = response.rows;
            this.total = response.total;
            var userName = this.$store.state.user.name;

            for (let i=0;i<this.userList.length;i++) {
              if (userName == 'admin') {
                this.userList[i].menuShow = true
                continue
              }
              if (this.userList[i].roleId != this.subRoleId) {

                this.userList[i].menuShow = false
              }else{
                this.userList[i].menuShow = true

              }

            }
            console.log(this.userList)
            this.loading = false;
          }
        );

      });



    },
    // 获取所有设备
    getAllDevice() {
      // 先清空列表
      this.dev_list = []
      // 获取序列号字符串，以 ‘|’ 分割（根据序列号获取和根据名称获取最终都是获取序列号）
      let id = FISECKEY.EnumBySerial();
      // 检验是否插入令牌
      if (id === '') {
        Message.warning('请插入令牌！');
        return;
      }
      // 获取序列号和名称集合
      let id_list = id.split('|');
      for (let i = 0; i < id_list.length; i++) {
        // 根据句柄来获取 U 盾名称
        try {
          let hDevice = FISECKEY.OpenBySerial(id_list[i], 0);
          let uName = FISECKEY.GetInfo(hDevice, 0).Label;
          this.dev_list.push({id: id_list[i], name: uName});

          //todo此处写死，后面去掉
          this.dev_list.push({id:'admin',name:'admin'});
          this.dev_list.push({id:'business_admin',name:'business_admin'});
          this.dev_list.push({id:'business_operator',name:'business_operator'});
          this.dev_list.push({id:'super_admin',name:'super_admin'});

          console.log(this.dev_list)
        }catch (e) {
          Message.error(e);
        }
      }
    },
    /**获得选中ukeyneme**/
    getname: function(val) {
      for (let i = 0; i < this.dev_list.length; i++) {
        if (this.dev_list[i].id == val) {

          // this.form.userName = this.dev_list[i].name;

          this.dev_id = this.dev_list[i].id
          this.dev_name = this.dev_list[i].name

          console.log("dev_id："+this.dev_id)
          console.log("dev_name："+this.dev_name)
          break;
        }
      }
    },
    getAllDevice2() {
      // 初始化环境
      this.init();
      // 消除历史记录
      this.dev_list = [];
      // 枚举设备
      let id_list = this.token.SOF_EnumDevice();
      if (id_list === null) {
        Message.warning("未找到任何Key！请插入令牌！");
        return;
      }
      // 获取所有设备序列号和名称
      for (let i = 0; i < id_list.length; i++) {
        // 绑定应用
        let ret = this.token.SOF_GetDeviceInstance(id_list[i], "");
        if (ret != 0) {
          Message.error("绑定应用失败，确定是否初始化Key,错误码:" + this.token.SOF_GetLastError());
          return;
        }
        this.dev_list.push({id: id_list[i], name: this.token.SOF_GetDeviceInfo(this.token.SGD_DEVICE_NAME)});

      }
    },
    // 初始化相关环境
    init() {
      this.token = new mToken("mTokenPlugin");
      // 加载相关控件
      let ret = this.token.SOF_LoadLibrary(this.token.GM3000);
      if (ret != 0) {
        Message.error("加载控件失败,错误码:" + this.token.SOF_GetLastError());
        return;
      }
    },
    // 获取签名证书（Base64 字符串）
    exportSignCert(){
      // if(this.container === ''){
      //   Message.warning("请选择容器！");
      //   return;
      // }
      // 绑定应用
      let ret = this.token.SOF_GetDeviceInstance(this.form.ukeyId, "");
      console.log("绑定应用")
      if (ret != 0) {
        Message.error("绑定应用失败，确定是否初始化Key,错误码:" + this.token.SOF_GetLastError());
        return;
      }
      // 容器名称
      let container = 'test'
      //  签名证书为 1 , 加密证书为 0
      let certType = 1
      this.outputCertData = this.token.SOF_ExportUserCert(container, certType);
      console.log("证书base64")
      console.log(this.outputCertData)
      this.form.certSn = this.outputCertData
      if(this.outputCertData === ''){
        Message.error("获取证书信息失败,错误码:" + this.token.SOF_GetLastError());
        return;
      }
    },
    // 修改设备名称
    editDevName() {
      if (this.dev_id == '') {
        Message.warning('请选择设备！');
        return;
      }
      // 绑定实例
      let ret = this.token.SOF_GetDeviceInstance(this.dev_id, "");
      if (ret != 0) {
        Message.error("绑定应用失败，确定是否初始化Key,错误码:" + this.token.SOF_GetLastError());
        return;
      }
      // 修改设备名称
      let ret1 = this.token.SOF_SetLabel(this.dev_name);
      if (ret1 != 0) {
        // Message.error("设置mToken名称失败,错误码：" + this.token.SOF_GetLastError());
        return;
      } else {
        // Message.success("设置mToken名称成功！");
      }
    },
    menuShowMethod(row) {
      console.log(row)
      console.log(this.subRoleId)
      if (row.roleId !== this.subRoleId) {
        return  false
      }
      return true
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function() {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
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
        userId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        roleIds: []
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {

      this.reset();
      getUser().then(response => {
        // console.log(response)

        this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.form.password = this.initPassword;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {

      this.reset();

      const userId = row.userId || this.ids;
      this.form.roleIds = this.roleIds;
      getUser(userId).then(response => {
        // console.log(response)
        console.log("row:")
        console.log(row)
        this.form = response.data;
        this.roleOptions = response.roles;
        this.roleIds = response.roleIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    isSub(row) {

      return true
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
      }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
          });
        }).catch(() => {});
    },
    /** 分配角色操作 */
    handleAuthRole: function(row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** 提交按钮 */
    submitForm: function() {
      console.log(this.form)
      // this.$refs["form"].validate(valid => {
      //   if (valid) {
      //     if (this.form.userId != undefined) {
      //
      //       this.form.roleIds = this.roleIds
      //       updateUser(this.form).then(response => {
      //         this.$modal.msgSuccess("修改成功");
      //         this.open = false;
      //         this.getList();
      //       });
      //     } else {
      //       this.form.roleIds = this.roleIds
      //       // console.log("请选择设备")
      //       if (this.form.userName==null) {
      //         this.$message.error('请选择设备');
      //       }
      //       console.log(this.form)
      //       addUser(this.form).then(response => {
      //         console.log(response)
      //         this.$modal.msgSuccess("新增成功");
      //         this.open = false;
      //         this.getList();
      //       });
      //     }
      //   }
      // });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function() {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
