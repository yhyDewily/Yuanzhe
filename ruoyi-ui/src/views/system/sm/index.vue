<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <div class="front_back_interaction">
          <span>系统操作处</span>
          <div style="margin: 20px;"></div>
          <el-form label-position="top" label-width="80px" :model="dataFormSystem" style="width:50%">
            <el-form-item label="密钥（使用 SM4_CBC 算法时填写）">
              <el-input v-model="dataFormSystem.key" placeholder="请输入十六位字符"
                        :disabled="dataFormSystem.algorithm == 'SM3'? true: false"/>
            </el-form-item>
            <el-form-item label="IV（使用 SM4_CBC 算法时填写）">
              <el-input v-model="dataFormSystem.iv" placeholder="请输入十六位字符"
                        :disabled="dataFormSystem.algorithm == 'SM3'? true: false"/>
            </el-form-item>
            <el-form-item label="待加密/解密/摘要内容">
              <el-input placeholder="请输入待加密/解密/摘要内容" v-model="dataFormSystem.text" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"/>
            </el-form-item>
            <el-form-item label="算法选择">
              <el-row :gutter="10">
                <el-col :span="12">
                  <el-select v-model="dataFormSystem.algorithm" placeholder="请选择算法">
                    <el-option label="SM4_CBC" value="SM4_CBC"></el-option>
                    <el-option label="SM3" value="SM3"></el-option>
                  </el-select>
                </el-col>
                <el-col :span="7">
                  <el-button type="primary" round @click="encryptOrAbstarctBySys">加密/摘要</el-button>
                </el-col>
                <el-col :span="5">
                  <el-button type="success" round @click="decryptBySys"
                             :disabled="dataFormSystem.algorithm == 'SM3'? true: false">解密
                  </el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item label="加密/解密/摘要后内容">
              <el-input v-model="resultTextFromSystem" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"/>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="front">
          <span>U 盾操作处</span>
          <div style="margin: 20px;"></div>
          <el-form label-position="top" label-width="80px" :model="dataFormUkey" style="width:50%">
            <el-form-item label="设备">
              <el-row :gutter="10">
                <el-col :span="18">
                  <el-select v-model="dev_id" placeholder="请选择设备" clearable>
                    <el-option
                      v-for="item in dev_list"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="6">
                  <el-button type="success" @click="getAllDevice">刷新</el-button>
                </el-col>
              </el-row>


            </el-form-item>
            <el-form-item label="密钥（使用 SM4_CBC 算法时填写）">
              <el-input v-model="dataFormUkey.key" placeholder="请输入十六位字符" :disabled="dataFormUkey.algorithm == 'SM3'"/>
            </el-form-item>
            <el-form-item label="IV（使用 SM4_CBC 算法时填写）">
              <el-input v-model="dataFormUkey.iv" placeholder="请输入十六位字符" :disabled="dataFormUkey.algorithm == 'SM3'"/>
            </el-form-item>
            <el-form-item label="待加密/解密/摘要内容">
              <el-input placeholder="请输入待加密/解密/摘要内容"
                        v-model="dataFormUkey.text" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"/>
            </el-form-item>
            <el-form-item label="算法选择">
              <el-row :gutter="10">
                <el-col :span="12">
                  <el-select v-model="dataFormUkey.algorithm" placeholder="请选择算法">
                    <el-option label="SM4_CBC" value="SM4_CBC"></el-option>
                    <el-option label="SM3" value="SM3"></el-option>
                  </el-select>
                </el-col>
                <el-col :span="7">
                  <el-button type="primary" round @click="encryptOrAbstarctByUkey">加密/摘要</el-button>
                </el-col>
                <el-col :span="5">
                  <el-button type="success" round @click="decryptByUkey"
                             :disabled="dataFormUkey.algorithm == 'SM3'? true: false">解密
                  </el-button>
                </el-col>
              </el-row>

            </el-form-item>
            <el-form-item label="加密/解密/摘要后内容">
              <el-input v-model="resultTextFromUkey" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"/>
            </el-form-item>

          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {encryptWithSM4CBCPadding, decryptWithSM4CBCPadding} from "@/api/system/sm";
import {mToken} from '@/assets/js/mToken';
import {_Base64encode, _Base64decode} from '@/assets/js/base64';
import {Message} from "element-ui";

export default {
  name: 'SM',
  data() {
    return {
      // 设备环境
      token: null,
      // 设备号
      dev_id: '',
      dev_list: [],
      // 表单
      // 前后端交互表单
      dataFormSystem: {
        key: '',
        iv: '',
        text: '',
        algorithm: ''
      },
      // U 盾交互表单
      dataFormUkey: {
        key: '',
        iv: '',
        text: '',
        algorithm: ''
      },
      // 加解密后的结果
      resultTextFromSystem: '',
      resultTextFromUkey: ''
    };
  },
  created() {
    this.getAllDevice();
  },
  methods: {

    /** 初始化相关环境 */
    init() {
      this.token = new mToken("mTokenPlugin");
      // 加载相关控件
      let ret = this.token.SOF_LoadLibrary(this.token.GM3000);
      if (ret != 0) {
        Message.error("加载控件失败,错误码:" + this.token.SOF_GetLastError());
        return;
      }
    },

    /** 获取所有设备 */
    getAllDevice() {
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
      // 获取所有设备序列号和对称密钥
      for (let i = 0; i < id_list.length; i++) {
        // 绑定应用
        let ret = this.token.SOF_GetDeviceInstance(id_list[i], "");
        if (ret != 0) {
          Message.error("绑定应用失败，确定是否初始化Key,错误码:" + this.token.SOF_GetLastError());
          return;
        }
        // 获取设备号和设备名称
        this.dev_list.push({id: id_list[i], name: this.token.SOF_GetDeviceInfo(this.token.SGD_DEVICE_NAME)});
      }
    },

    /** 系统部分的加解密 */
    encryptOrAbstarctBySys() {
      encryptWithSM4CBCPadding(this.dataFormSystem).then(response => {
        console.log(response);
        this.resultTextFromSystem = response.data;
      })

    },
    decryptBySys() {
      decryptWithSM4CBCPadding(this.dataFormSystem).then(response => {
        console.log(response);
        this.resultTextFromSystem = response.data;
      })
    },
    /** Ukey部分的加解密 */
    encryptOrAbstarctByUkey() {
      if (this.dev_id === '') {
        Message.warning('请选择设备！');
        return;
      }
      // 绑定应用
      let ret = this.token.SOF_GetDeviceInstance(this.dev_id, "");
      if (ret != 0) {
        Message.error("绑定应用失败，确定是否初始化Key！");
        return;
      }
      if (this.dataFormUkey.algorithm === 'SM3') {
        // 待摘要的文本
        let text = this.dataFormUkey.text;
        // SGD_SM3 对应 1
        this.token.SOF_SetDigestMethod(1);
        // 需要选择一个容器，容器里面必须含有证书（存疑）
        let digest = this.token.SOF_DigestData('chen01', _Base64encode(text), text.length);
        console.log(digest);
        if (digest != null) {
          this.resultTextFromUkey = digest;
        } else {
          Message.error('数据摘要失败！');
        }
      } else {
        // SGD_SM4_CBC 对应 1026
        this.token.SOF_SetEncryptMethodAndIV(1026, _Base64encode(this.dataFormUkey.iv));
        // 使用 SM4_CBC 来进行加密
        let encryptData = this.token.SOF_SymEncryptData(_Base64encode(this.dataFormUkey.key), _Base64encode(this.dataFormUkey.text));
        if (encryptData != null || encryptData == "") {
          this.resultTextFromUkey = encryptData;
        } else {
          Message.error('加密失败！')
        }
      }
    },
    decryptByUkey() {
      // 绑定应用
      let ret = this.token.SOF_GetDeviceInstance(this.dev_id, "");
      if (ret != 0) {
        Message.error("绑定应用失败，确定是否初始化Key！");
        return;
      }
      this.token.SOF_SetEncryptMethodAndIV(1026, _Base64encode(this.dataFormUkey.iv));
      // 使用 SM4_CBC 来进行解密
      let decryptedData = this.token.SOF_SymDecryptData(_Base64encode(this.dataFormUkey.key), this.dataFormUkey.text);
      if (decryptedData != null && decryptedData != "") {
        this.resultTextFromUkey = _Base64decode(decryptedData);
      } else
        Message.error('解密失败！')
    }

  }
};
</script>
