<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">密钥管理系统</h3>
<!--      <el-form-item prop="username">-->
<!--        <el-input-->
<!--          v-model="loginForm.username"-->
<!--          type="text"-->
<!--          auto-complete="off"-->
<!--          placeholder="账号"-->
<!--        >-->
<!--          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--      </el-form-item>-->
      <el-form-item prop="secretKey" style="width: 350px" >
        令牌
        <div class="selectForm">
          <el-select v-model="loginForm.username" placeholder="请选择" :required="true">
            <el-option
              v-for="item in dev_list"
              :key="item.id"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
          <el-button size="mini" type="success" style="margin-left:10px;height:40%" @click="getAllDevice2">刷新</el-button>
        </div>


          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />

      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
<!--      <el-form-item prop="code" v-if="captchaOnOff">-->
<!--        <el-input-->
<!--          v-model="loginForm.code"-->
<!--          auto-complete="off"-->
<!--          placeholder="验证码"-->
<!--          style="width: 63%"-->
<!--          @keyup.enter.native="handleLogin"-->
<!--        >-->
<!--          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--        <div class="login-code">-->
<!--          <img :src="codeUrl" @click="getCode" class="login-code-img"/>-->
<!--        </div>-->
<!--      </el-form-item>-->
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2021 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import {mToken} from '@/assets/js/mToken';
import { encrypt, decrypt } from '@/utils/jsencrypt'
import {FISECKEY,SKFKEY} from '@/assets/js/fiseckey'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      // 设备环境
      token: null,
      dev_list:[
        {

        }
      ],
      secretKey:'',
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: false,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
    this.getAllDevice2();
  },
  methods: {
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
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
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
          this.dev_list.push({id:'audit_operator',name:'audit_operator'});

          console.log(this.dev_list)
        }catch (e) {
          Message.error(e);
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
      this.dev_list.push({id:'admin',name:'admin'});
      this.dev_list.push({id:'business_admin',name:'business_admin'});
      this.dev_list.push({id:'business_operator',name:'business_operator'});
      this.dev_list.push({id:'super_admin',name:'super_admin'});
      this.dev_list.push({id:'audit_operator',name:'audit_operator'});
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaOnOff) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
.selectForm{
  .el-input{
    width:280px!important;
    /*margin-left: 4px;*/
    height:38px;
  }
  .el-input__inner{
    width:280px!important;
    /*margin-left: 4px;*/
    height:38px;
  }
  .el-input--suffix .el-input__inner{
    width:280px!important;
    /*margin-left: 4px;*/
    height:38px;
  }
  .el-input__icon{
    height:116%;
  }
}
</style>
