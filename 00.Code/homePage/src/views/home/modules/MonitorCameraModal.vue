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
        <a-form-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            v-decorator="['companyId', {}]"
            :triggerChange="true"
            placeholder="请输入公司"
            :dictOptions="companyList"
          />
        </a-form-item>

        <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag
            v-model="data.areaId"
            placeholder="请选择所在区域"
            dictCode="base_area,location,id"
          />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="监控名称">
          <a-input placeholder="请输入监控名称" v-decorator="['name', {}]" />
        </a-form-item>

        <!-- <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="通道号">
          <a-input placeholder="请输入通道号" v-decorator="[ 'channelidname', {}]" />
        </a-form-item>-->
        <!-- <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="3d模型编号">
          <a-input placeholder="请输入3d模型编号" v-decorator="[ 'model3dno', {}]" />
        </a-form-item>-->

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="通道号">
          <a-input placeholder="通道号" v-decorator="[ 'distchannel', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="地址">
          <a-input placeholder="请输入地址" v-decorator="[ 'url', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="ip地址">
          <a-input placeholder="请输入ip地址" v-decorator="[ 'ipaddress', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="用户名">
          <a-input placeholder="请输入用户名" v-decorator="[ 'username', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="密码">
          <a-input placeholder="请输入密码" v-decorator="[ 'password', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="端口号">
          <a-input type="number" placeholder="请输入端口号" v-decorator="[ 'port', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import moment from 'moment'

export default {
  name: 'MonitorCameraModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {},
      data: { status: '', areaId: '', cameratype: '' },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      companyList: this.getcompanyList(),
      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {},
      url: {
        add: '/monitorCamera/add',
        edit: '/monitorCamera/edit'
      }
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)

      this.data = { status: '', areaid: '', cameratype: '' }

      if (record.status) {
        this.data.status = record.status + ''
      }
      if (record.cameratype) {
        this.data.cameratype = record.cameratype + ''
      }

      if (record.areaid) {
        this.data.areaid = record.areaid + ''
      }

      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'companyId',
            'channelid',
            'channelidname',
            'distchannel',
            'model3dno',
            'name',
            'areaId',
            'url',
            'ipaddress',
            'username',
            'password',
            'port',
            'cameratype'
          )
        )
        //时间格式化
        this.form.setFieldsValue({
          createtime: this.model.createtime ? moment(this.model.createtime) : null
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
          formData.createtime = formData.createtime ? formData.createtime.format('YYYY-MM-DD HH:mm:ss') : null

          formData.areaId = this.data.areaId
          formData.cameratype = this.data.cameratype

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