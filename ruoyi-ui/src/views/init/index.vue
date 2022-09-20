<template>
  <div class="app-container">


      <el-button type="primary" @click="dialogForm1Visible = true">
        创建超级管理员
      </el-button>

      <el-button type="primary" @click="dialogForm2Visible = true">
        创建审计管理员
      </el-button>

    <div>
      <el-dialog title="收货地址" :visible.sync="dialogForm1Visible">
        <div>
          <el-form :model="form">
            <el-form-item label="设备序列号" :label-width="formLabelWidth">
              <el-select v-model="form.region" placeholder="请选择驱动">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-button type="success">刷新</el-button>
            </el-form-item>
            <el-form-item label="设备PIN码" :label-width="formLabelWidth" >
              <el-input class="input-reader-name" v-model="form.PIN" autocomplete="off" size="small" show-password></el-input>

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
          <el-button type="primary" @click="dialogForm1Visible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog title="收货地址" :visible.sync="dialogForm2Visible">
        <div>
          <el-form :model="form">
            <el-form-item label="设备序列号" :label-width="formLabelWidth">
              <el-select v-model="form.region" placeholder="请选择驱动">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-button type="success">刷新</el-button>
            </el-form-item>
            <el-form-item label="设备PIN码" :label-width="formLabelWidth" >
              <el-input class="input-reader-name" v-model="form.PIN" autocomplete="off" size="small" show-password></el-input>

            </el-form-item>


          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogForm2Visible = false">取 消</el-button>
          <el-button type="primary" @click="dialogForm2Visible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
    export default {
        name: "index",
      data() {
          return {
            fileList:null,
            dialogForm1Visible: false,
            dialogForm2Visible: false,
            form: {
              name: '',
              region: '',
              date1: '',
              date2: '',
              delivery: false,
              type: [],
              resource: '',
              desc: '',
              PIN:'',
            },
            options:[{
              value:'sadkljl1324038',
              label:'sadkljl1324038'
            }],
            formLabelWidth: '100px'
          };
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
        }
      },
    }
</script>

<style scoped>
.input-reader-name{
  width: 40%;
}
</style>
