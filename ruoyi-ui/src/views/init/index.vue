<template>
  <div class="app-container">


      <el-button type="primary" @click="dialogForm1Visible = true">
        创建超级管理员
      </el-button>

      <el-button type="primary" @click="dialogForm2Visible = true">
        创建审计管理员
      </el-button>

    <div>
      <el-dialog title="创建超级管理员" :visible.sync="dialogForm1Visible">
        <div>
          <el-form ref="form" :model="form" :rules="rules">
            <el-form-item label="设备序列号" :label-width="formLabelWidth">
              <el-select v-model="form.userName" @change="selectModel" placeholder="请选择驱动" :required="true">
                <el-option
                  v-for="item in dev_list"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-button size="mini" type="success" style="margin-left:10px;height:40%" @click="getAllDevice">刷新</el-button>            </el-form-item>
            <el-form-item label="设备PIN码" :label-width="formLabelWidth" prop="password">
              <el-input class="input-reader-name"  v-model="form.password" autocomplete="off" size="small" show-password></el-input>

            </el-form-item>
            <el-form-item label="CA证书"  :label-width="formLabelWidth" >
<!--              <el-input class="input-reader-name" v-model="form.PIN" autocomplete="off" size="small"></el-input>-->
              <el-upload
                class="upload-demo"
                ref="upload"
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                multiple
                :limit="3"
                :on-exceed="handleExceed"
                :auto-upload="false"
                :file-list="fileList">
                <el-button type="success">点击上传</el-button>
              </el-upload>
            </el-form-item>

          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogForm1Visible = false">取 消</el-button>
          <el-button type="primary"
                      @click="submitFormSA">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog title="创建审计管理员" :visible.sync="dialogForm2Visible">
        <div>
          <el-form ref="form" :model="form" :rules="rules">
            <el-form-item label="设备序列号" :label-width="formLabelWidth">
              <el-select v-model="form.userName" @change="selectModel" placeholder="请选择驱动">
                <el-option
                  v-for="item in dev_list"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-button size="mini" type="success" style="margin-left:10px;height:40%" @click="getAllDevice">刷新</el-button>
            </el-form-item>
            <el-form-item label="设备PIN码" :label-width="formLabelWidth" prop="password">
              <el-input class="input-reader-name" v-model="form.password" autocomplete="off" size="small" show-password></el-input>

            </el-form-item>


          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogForm2Visible = false">取 消</el-button>
          <el-button type="primary" @click="submitFormAA">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
  import {FISECKEY,SKFKEY} from '@/assets/js/fiseckey'
  import { listUser, addUserSA, addUserAA, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";

  export default {
        name: "index",
      data() {
          return {
            fileList:null,
            dev_list:[],
            currentDev:[],
            dialogForm1Visible: false,
            dialogForm2Visible: false,
            form: {

            },
            options:[{
              value:'sadkljl1324038',
              label:'sadkljl1324038'
            }],
            formLabelWidth: '100px',
            rules: {
              password: [
                { required: true, message: "用户密码不能为空", trigger: "blur" },
                { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
              ],
            },
          };
      },
      created() {
          this.getAllDevice()
      },
      methods: {
        submitUpload() {
          this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },
        selectModel(name) {
          this.form.ukeyId = this.dev_list.find(item => item.name == name).id
          console.log(this.form)
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
        /** 提交按钮SA */
        submitFormSA: function() {
          // console.log(this.form)
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.userId != undefined) {

                this.$alert("无法修改")
              } else {
                // this.form.roleIds = this.roleIds
                // console.log("请选择设备")
                if (this.currentDev==null) {
                  this.$message.error('请选择设备');
                }
                console.log(this.form)

                addUserSA(this.form).then(response => {
                  console.log(response)
                  this.$modal.msgSuccess("新增成功");
                  this.dialogForm1Visible = false

                  // this.getList();
                });
              }
            }
          });
        },

        /** 提交按钮AA */
        submitFormAA: function() {
          // console.log(this.form)
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.userId != undefined) {

                this.$alert("无法修改")
              } else {
                // this.form.roleIds = this.roleIds
                // console.log("请选择设备")
                if (this.currentDev==null) {
                  this.$message.error('请选择设备');
                }
                console.log(this.form)

                addUserAA(this.form).then(response => {
                  console.log(response)
                  this.$modal.msgSuccess("新增审计管理员成功");
                  this.dialogForm2Visible = false

                  // this.getList();
                });
              }
            }
          });
        },
      },
    }
</script>

<style scoped>
.input-reader-name{
  width: 40%;
}
</style>
