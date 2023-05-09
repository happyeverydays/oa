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
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="员工">
          <a-input read-only placeholder="请输入员工" v-decorator="['staffName', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开始时间">
          <j-date
            v-model="model.startTime"
            @change="calcaddtime"
            :showTime="true"
            :dateFormat="'YYYY-MM-DD HH:mm:ss'"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结束时间">
          <j-date
            v-model="model.endTime"
            @change="calcaddtime"
            :showTime="true"
            :dateFormat="'YYYY-MM-DD HH:mm:ss'"
          />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="加班时间">
          <a-input placeholder="请输入加班时间" :disabled="true" v-model="model.addHours" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="加班原因">
          <a-textarea placeholder="请输入加班原因" v-decorator="['reason', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请时间">
          <a-date-picker
            style="width:100%;"
            :disabled="true"
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            v-decorator="[ 'applyDate', {}]"
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

export default {
  name: 'ProcessOverTimeModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {
        startTime: '',
        endTime: ''
      },
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
      validatorRules: {},
      url: {
        add: '/processOverTime/add',
        edit: '/processOverTime/edit'
      }
    }
  },
  created() {},
  methods: {
    calcaddtime(a) {
      var startTime = new Date(this.model.startTime).getTime()
      var endTime = new Date(this.model.endTime).getTime()
      var addHours = (((endTime - startTime) * 1.0) / 3600 / 1000).toFixed(2)
      this.model.addHours = addHours
    },
    add() {
      this.edit({})
    },
    edit(record) {
      var userInfo = JSON.parse(window.localStorage.getItem('pro__Login_Userinfo')).value
      if (!record.staffId) {
        record.staffId = userInfo.id
      }
      if (!record.staffName) {
        record.staffName = userInfo.realname
      }
      if (!record.jobNumber) {
        record.jobNumber = userInfo.jobNumber
      }
      if (!record.processState) {
        record.processState = '0'
      }
      if (!record.startTime) {
        record.startTime = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      }
      if (!record.endTime) {
        record.endTime = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      }
      if (!record.addHours) {
        record.addHours = '0'
      }
      this.form.resetFields()
      this.model = Object.assign({}, record)

      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(this.model, 'sysOrgCode', 'staffId', 'staffName', 'applyDate', 'reason', 'jobNumber', 'processState')
        )
        this.form.setFieldsValue({
          applyDate: this.model.applyDate ? moment(this.model.applyDate) : moment(new Date())
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
          // formData.startTime = formData.startTime ? formData.startTime.format('YYYY-MM-DD HH:mm:ss') : null
          // formData.endTime = formData.endTime ? formData.endTime.format('YYYY-MM-DD HH:mm:ss') : null
          formData.applyDate = formData.applyDate ? formData.applyDate.format('YYYY-MM-DD HH:mm:ss') : null
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