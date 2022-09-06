<template>
  <div>
    <div>
      <el-button @click="fetchData">刷新</el-button>
      <el-button @click="clickAdd">新增业务管理员</el-button>
    </div>

    <div>
      <el-table
        :data="user"
        border
        style="width: 100%">
        <el-table-column
          prop="userId"
          label="ID"
          width="50">
        </el-table-column>
        <el-table-column
          prop="userName"
          label="用户姓名"
          width="150">
        </el-table-column>
        <el-table-column
          prop="roles[0].roleName"
          label="操作员角色"
          width="100"
        >
        </el-table-column>
        <!--        <el-table-column-->
        <!--          prop="email"-->
        <!--          label="邮箱"-->
        <!--          width="150"-->
        <!--        >-->
        <!--        </el-table-column>-->
        <!--        <el-table-column-->
        <!--          prop="phonenumber"-->
        <!--          label="手机号码"-->
        <!--          width="150"-->
        <!--        >-->
        <!--        </el-table-column>-->
        <!--        <el-table-column-->
        <!--          prop="sex"-->
        <!--          label="性别"-->
        <!--          width="50"-->

        <!--        >-->
        <!--          <template slot-scope="scope">-->
        <!--            <div v-if="scope.row.sex==0">女</div>-->
        <!--            <div v-if="scope.row.sex==1">男</div>-->
        <!--            <div v-if="scope.row.sex==2">未知</div>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column
          prop="status"
          label="标签"
          width="100"
        >
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

          <!--        <el-tag-->
          <!--          :type="user.status === '家' ? 'primary' : 'success'"-->
          <!--          disable-transitions>{{status}}</el-tag>-->

        </el-table-column>
        <el-table-column
          prop="loginDate"
          label="最近登录时间"
          width="200"
        >
        </el-table-column>

        <el-table-column

          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button
              @click="clickGetInfo(scope.row)"
              type="text"
              size="small">
              查看
            </el-button>
            <el-button
              @click="clicktoUpdate(scope.row)"
              type="text"
              size="small">
              修改
            </el-button>
            <el-button
              @click="clickDelete(scope.row)"
              type="text"
              size="small">
              注销
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>



    <div>
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible">

        <el-form ref="form" :model="form" >
          <el-form-item label="用户id: " :label-width="formLabelWidth">
            {{form.userId}}

          </el-form-item>
          <el-form-item label="用户姓名: " :label-width="formLabelWidth">
            {{form.userName}}

          </el-form-item>
          <el-form-item label="用户角色: " :label-width="formLabelWidth">
            {{form.nickName}}
          </el-form-item>
          <el-form-item label="邮箱: " :label-width="formLabelWidth">
            {{form.email}}
          </el-form-item>
          <el-form-item label="手机号码: " :label-width="formLabelWidth">
            {{form.phonenumber}}
          </el-form-item>
          <el-form-item label="性别: " :label-width="formLabelWidth">
            <div v-if="form.sex==0">女</div>
            <div v-if="form.sex==1">男</div>
            <div v-else>未知</div>
          </el-form-item>
          <el-form-item label="用户状态: " :label-width="formLabelWidth">
            <div v-if="form.status==0">正常</div>
            <div v-if="form.status==1">停用</div>
            <div v-if="form.status==2">未授权</div>
          </el-form-item>
          <el-form-item label="最近登录时间: " :label-width="formLabelWidth">
            {{form.loginDate}}
          </el-form-item>
          <el-form-item label="创建时间: " :label-width="formLabelWidth">
            {{form.createTime}}
          </el-form-item>
          <el-form-item label="更新时间: " :label-width="formLabelWidth">
            {{form.updateTime}}
          </el-form-item>
          <el-form-item label="创建者: " :label-width="formLabelWidth">
            {{form.createBy}}
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>

      </el-dialog>
    </div>

    <div>
      <el-dialog class="edit" title="用户信息修改" :visible.sync="dialogEditFormVisible">

        <el-form ref="form" :model="form" >
          <el-form-item label="用户id: " :label-width="formLabelWidth">
            <!--            <el-input v-model="form.userId"></el-input>-->
            {{form.userId}}

          </el-form-item>
          <el-form-item label="用户姓名: " :label-width="formLabelWidth">
            <el-input v-model="form.userName"></el-input>
            <!--            {{form.userName}}-->

          </el-form-item>
          <!--          <el-form-item label="用户角色: " :label-width="formLabelWidth">-->
          <!--&lt;!&ndash;            <el-input v-model="form.userName"></el-input>&ndash;&gt;-->
          <!--            {{form.nickName}}-->
          <!--          </el-form-item>-->
          <el-form-item label="邮箱: " :label-width="formLabelWidth">
            <el-input v-model="form.email"></el-input>
            <!--            {{form.email}}-->
          </el-form-item>
          <el-form-item label="手机号码: " :label-width="formLabelWidth">
            <el-input v-model="form.phonenumber"></el-input>
            <!--            {{form.phonenumber}}-->
          </el-form-item>
          <el-form-item label="性别: " :label-width="formLabelWidth">
            <!--            <div v-if="form.sex==0">女</div>-->
            <!--            <div v-if="form.sex==1">男</div>-->
            <!--            <div v-else>未知</div>-->
            <el-input v-model="form.sex"></el-input>
          </el-form-item>
          <!--          <el-form-item label="用户状态: " :label-width="formLabelWidth">-->
          <!--            <div v-if="form.status==0">正常</div>-->
          <!--            <div v-if="form.status==1">停用</div>-->
          <!--            <div v-if="form.status==2">未授权</div>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="最近登录时间: " :label-width="formLabelWidth">-->
          <!--            {{form.loginDate}}-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="创建时间: " :label-width="formLabelWidth">-->
          <!--            {{form.createTime}}-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="更新时间: " :label-width="formLabelWidth">-->
          <!--            {{form.updateTime}}-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="创建者: " :label-width="formLabelWidth">-->
          <!--            {{form.createBy}}-->
          <!--          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>

      </el-dialog>
    </div>
    <el-dialog class="edit" title="操作员添加" :visible.sync="dialogAddFormVisible">

      <el-form ref="form" :model="form" >

        <el-form-item label="设备名称: " :label-width="formLabelWidth">
          <el-input v-model="addForm.userName"></el-input>


        </el-form-item>

        <!--          <el-form-item label="邮箱: " :label-width="formLabelWidth">-->
        <!--            <el-input v-model="addForm.email"></el-input>-->
        <!--            &lt;!&ndash;            {{form.email}}&ndash;&gt;-->
        <!--          </el-form-item>-->
        <!--          <el-form-item label="手机号码: " :label-width="formLabelWidth">-->
        <!--            <el-input v-model="addForm.phonenumber"></el-input>-->
        <!--            &lt;!&ndash;            {{form.phonenumber}}&ndash;&gt;-->
        <!--          </el-form-item>-->
        <!--          <el-form-item label="性别: " :label-width="formLabelWidth">-->

        <!--            <el-input v-model="addForm.sex"></el-input>-->
        <!--          </el-form-item>-->
        <el-form-item label="PIN码: " :label-width="formLabelWidth">

          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="操作员角色: " :label-width="formLabelWidth">
          业务管理员

        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSubmit()">确 定</el-button>
      </div>

    </el-dialog>
    <div>

    </div>
  </div>



</template>

<script>
  import request from '@/utils/request'
  import axios from 'axios'
  export default {
    data() {
      return {
        queryParams: {
          pageNum: 1,
          pageSize: 10
        },
        user:[{
          // userId:'',
          // userName:'',
          // roles:[{
          //   roleId:'',
          //   roleName:'',
          //   roleKey:'',
          // }],
          // email:'',
          // phonenumber:'',
          // sex:'',
          // loginDate:'',
          // status:'',
          // delFlag:'',
          // createTime:'',
          // updateTime:'',
          // updateBy:'',
        }],
        dialogTableVisible: false,
        dialogFormVisible: false,
        dialogEditFormVisible:false,
        dialogAddFormVisible:false,
        formLabelWidth: '200px',
        form: {

        },
        addForm: {},
        listLoading: true,

      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        console.log("获得数据")

        request({
          url: 'superadmin/list',
          method: 'get',

        })
        .then((res)=>{
          console.log(res)
          if (res.code==200) {
            this.user = res.rows
            console.log(res)

          }
          else {
            console.log("连接失败")
          }

        })
        this.listLoading = false
      },
      clickGetInfo(row) {
        this.dialogFormVisible = true
        console.log(row)
        this.form = row
      },
      clicktoUpdate(row) {

        this.dialogEditFormVisible = true
        console.log(row)
        this.form = row
        // let updateForm = {
        //   userId: this.form.userId,
        //   userName: this.form.userName,
        //   email: this.form.email,
        //   phonenumber: this
        //
        // }
        // axios.post("http://localhost:8080/superadmin/update",{
        //
        // })
      },
      clickDelete(row) {
        let userId = row.userId
        console.log(row)
        console.log(userId)

        request({
          url: '/superadmin/delete/'+userId,
          method: 'delete',

        }).then((res)=>{
          if (res.code==200) {
            console.log(res.msg)
          }
          else {
            console.log(res.msg)
          }
        })
      },
      submit() {
        this.dialogEditFormVisible = false
        let updateForm = {
          userId: this.form.userId,
          userName: this.form.userName,
          email: this.form.email,
          phonenumber: this.form.phonenumber,
          sex:this.form.sex

        }
        console.log(updateForm)

        request({
          url: '/superadmin/update',
          method: 'put',
          data: updateForm
        }).then((res)=>{
          if (res.code==200) {
            console.log("修改成功")
          }
          else {
            console.log(res.msg)
          }
        })
      },
      clickAdd() {
        this.dialogAddFormVisible = true

      },
      addSubmit() {
        console.log(this.addForm)
        this.dialogAddFormVisible = false
        let addData = {
          userName:this.addForm.userName,
          email:this.addForm.email,
          phonenumber: this.addForm.phonenumber,
          sex: this.addForm.sex,
          password: this.addForm.password


        }

        request({
          url: 'superadmin/addBusinessAdmin',
          method: 'post',
          data:addData

        }).then((res)=>{
          if (res.code == 200) {
            console.log("新增成功  "+res)
          }
          else {
            console.log(res.msg)
          }
        })
      }
    }
  }
</script>
