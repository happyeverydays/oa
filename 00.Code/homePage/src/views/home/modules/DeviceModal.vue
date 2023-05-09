<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <!-- <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="companyid">
          <a-input-number v-decorator="[ 'companyid', {}]" />
        </a-form-item>-->
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类型">
          <a-select v-model="formdata.type" placeholder="请选择类型">
            <a-select-option value="1">员工</a-select-option>
            <a-select-option value="2">外协</a-select-option>
            <a-select-option value="3">临时</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="门禁卡卡号">
          <a-input placeholder="请输入门禁卡卡号" v-decorator="['cardno', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工号">
          <a-input placeholder="请输入工号" v-decorator="['jobnumber', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="姓名">
          <a-input placeholder="请输入姓名" v-decorator="['name', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="性别">
          <a-select v-model="formdata.gender" placeholder="请选择性别">
            <a-select-option value="1">男</a-select-option>
            <a-select-option value="2">女</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机号">
          <a-input placeholder="请输入手机号" v-decorator="['phone', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="身份证">
          <a-input placeholder="请输入身份证" v-decorator="['idcard', {}]" />
        </a-form-item>
        
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="教育程度">
          <a-input placeholder="请输入教育" v-decorator="['education', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="照片">
          <a-input placeholder="请输入照片" v-decorator="['portrait', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="状态">
          <a-select v-model="formdata.status" placeholder="请选择状态">
            <a-select-option value="1">在岗</a-select-option>
            <a-select-option value="2">离职</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from "@/api/manage";
import pick from "lodash.pick";
import moment from "moment";

export default {
  name: "UserModal",
  data() {
    return {
      title: "操作",
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },

      confirmLoading: false,
      form: this.$form.createForm(this),
      formdata: { type: "1",gender:"1",status:"1" },
      validatorRules: {},
      url: {
        add: "/integrated/user/add",
        edit: "/integrated/user/edit"
      }
    };
  },
  created() {},
  methods: {
    add() {
      this.edit({});
    },
    edit(record) {
      this.form.resetFields();
      this.model = Object.assign({}, record);
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            "companyid",
            "type",
            "cardno",
            "jobnumber",
            "name",
            "gender",
            "phone",
            "idcard",
            "departmentid",
            "positionid",
            "education",
            "portrait",
            "status",
            "delflag"
          )
        );
        //时间格式化
        this.form.setFieldsValue({
          createtime: this.model.createtime
            ? moment(this.model.createtime)
            : null
        });
      });
    },
    close() {
      this.$emit("close");
      this.visible = false;
    },
    handleOk() {
      const that = this;
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true;
          let httpurl = "";
          let method = "";
          if (!this.model.id) {
            httpurl += this.url.add;
            method = "post";
          } else {
            httpurl += this.url.edit;
            method = "put";
          }
          let formData = Object.assign(this.model, values);
          formData = Object.assign(formData, this.formdata);
          //时间格式化
          formData.createtime = formData.createtime
            ? formData.createtime.format("YYYY-MM-DD HH:mm:ss")
            : null;

          console.log(formData);
          httpAction(httpurl, formData, method)
            .then(res => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit("ok");
              } else {
                that.$message.warning(res.message);
              }
            })
            .finally(() => {
              that.confirmLoading = false;
              that.close();
            });
        }
      });
    },
    handleCancel() {
      this.close();
    }
  }
};
</script>

<style lang="less" scoped>
</style>