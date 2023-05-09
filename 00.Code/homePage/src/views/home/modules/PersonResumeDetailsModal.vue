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
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="面试官id">
          <j-search-select-tag
            v-decorator="['interviewerId', {}]"
            :triggerChange="true"
            placeholder="请输入面试官"
            :dict="'sys_user,realname,id'"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="面试评价">
          <a-textarea placeholder="请输入面试评价" v-decorator="['memo', {}]" rows="5" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="附件">
          <a-upload
            name="file"
            :showUploadList="false"
            :multiple="false"
            :headers="tokenHeader"
            :action="url.fileUpload"
            @change="handleChange"
          >
            <a-button type="primary" icon="import">上传附件</a-button>
          </a-upload>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="面试时间">
          <a-date-picker
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            style="width:100%;"
            v-decorator="[ 'interviewerDate', {}]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import moment from 'moment'
import { ACCESS_TOKEN } from "@/store/mutation-types"
import Vue from 'vue'

export default {
  name: 'PersonResumeDetailsModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {},
      data: { attachment: '' },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {},
      url: {
        add: '/integrated/personResumeDetails/add',
        edit: '/integrated/personResumeDetails/edit',
        fileUpload: window._CONFIG['domianURL'] + 'upload3DMachine/uploadFile'
      }
    }
  },
  created() {},
  methods: {
    handleChange(info) {
      this.picUrl = ''
      if (info.file.status === 'uploading') {
        this.uploadLoading = true
        return
      }
      if (info.file.status === 'done') {
        var response = info.file.response
        this.uploadLoading = false
        console.log(response)
        if (response.success) {
          this.data.attachment = response.shorturl
          this.picUrl = 'Has no pic url yet'
        } else {
          this.$message.warning(response.message)
        }
      }
    },
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(this.model, 'sysOrgCode', 'memo', 'interviewerId', 'interviewerJobNumber', 'interviewerName')
        )
        //时间格式化
        this.form.setFieldsValue({
          interviewerDate: this.model.interviewerDate ? moment(this.model.interviewerDate) : null
        })
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          let formData = Object.assign(this.model, values)
          //时间格式化
          formData.interviewerDate = formData.interviewerDate
            ? formData.interviewerDate.format('YYYY-MM-DD HH:mm:ss')
            : null
          if (this.data.attachment) {
            formData.attachment = this.data.attachment
          }
          console.log(formData)
          httpAction(httpurl, formData, method)
            .then(res => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        }
      })
    },
    handleCancel() {
      this.close()
    }
  }
}
</script>

<style lang="less" scoped>
</style>