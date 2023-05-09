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
          <a-input placeholder="请输入员工" read-only v-decorator="['staffName', validatorRules.staffName ]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="请假类型">
          <j-dict-select-tag
            v-decorator="['leaveType', {}]"
            :triggerChange="true"
            placeholder="请输入面试流程"
            dictCode="leave_type"
          />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开始时间">
          <a-date-picker
            style="width:100%;"
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            v-decorator="[ 'startTime', {}]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结束时间">
          <a-date-picker
            style="width:100%;"
            showTime
            format="YYYY-MM-DD HH:mm:ss"
            v-decorator="[ 'endTime', {}]"
          />
        </a-form-item>
        <!-- <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="请假天数">
          <a-input-number readonly style="width:100%;" v-decorator="[ 'days', {}]" />
        </a-form-item> -->
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="原因">
          <a-textarea rows="4" placeholder="请输入原因" v-decorator="['reason', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请时间">
          <a-date-picker
            style="width:100%;"
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
  name: 'OverTimeModal',
  data() {
    return {
      title: '操作',
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
      validatorRules: {
        staffName: { rules: [{ required: true, message: '请输入员工!' }] }
      },
      url: {
        add: '/integrated/processLeaveBill/add',
        edit: '/integrated/processLeaveBill/edit'
      }
    }
  },
  created() {},
  methods: {
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
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'staffId', 'staffName', 'days', 'reason','leaveType'))
        //时间格式化
        this.form.setFieldsValue({ startTime: this.model.startTime ? moment(this.model.startTime) : null })
        this.form.setFieldsValue({ endTime: this.model.endTime ? moment(this.model.endTime) : null })
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

          formData.startTime = formData.startTime ? formData.startTime.format('YYYY-MM-DD HH:mm:ss') : null
          formData.endTime = formData.endTime ? formData.endTime.format('YYYY-MM-DD HH:mm:ss') : null
          formData.applyDate = formData.applyDate ? formData.applyDate.format('YYYY-MM-DD HH:mm:ss') : null

          formData.days =
            (this.convertDateFromString(formData.endTime).getTime() -
              this.convertDateFromString(formData.startTime).getTime()) /
            3600 /
            24000;
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
    },
    convertDateFromString(dateString) {
      if (dateString) {
        var arr1 = dateString.split(' ')
        var sdate = arr1[0].split('-')
        var date = new Date(sdate[0], sdate[1] - 1, sdate[2])
        return date
      }
    }
  }
}
</script>

<style lang="less" scoped>
</style>