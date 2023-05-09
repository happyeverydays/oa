<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <template v-if="flag==1">
          <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入编码"></a-input>
          </a-form-item>

          <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'description', validatorRules.description]" placeholder="请输入描述"></a-input>
          </a-form-item>

          <a-form-item label="隐患所在区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['area']" :trigger-change="true" dictCode="base_area,name,id" placeholder="请选择隐患所在区域"/>
          </a-form-item>

          <a-form-item label="隐患照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'problemPic', validatorRules.problemPic]" placeholder="请输入隐患照片"></a-input>
          </a-form-item>

          <a-form-item label="公司编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入公司编号"></a-input>
          </a-form-item>

          <a-form-item label="检查方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['checkMethod']" :trigger-change="true" dictCode="problem_checkmethod" placeholder="请选择检查方式"/>
          </a-form-item>

          <a-form-item label="检查类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['checkType']" :trigger-change="true" dictCode="problem_checktype" placeholder="请选择检查类型"/>
          </a-form-item>

          <a-form-item label="检查人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'checkUsers', validatorRules.checkUsers]" placeholder="请输入检查人"></a-input>
          </a-form-item>

          <a-form-item label="检查时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-date
              placeholder="请选择检查时间"
              v-decorator="[ 'checkTime', validatorRules.checkTime]"
              :trigger-change="true"
              style="width: 100%"
            />
          </a-form-item>

          <a-form-item label="提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-date
              placeholder="请选择提交时间"
              v-decorator="[ 'submitTime', validatorRules.submitTime]"
              :trigger-change="true"
              style="width: 100%"
            />
          </a-form-item>
          </template>

          <template v-else-if="flag==2">
          <a-form-item label="隐患级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'problemLevel', validatorRules.problemLevel]"
              placeholder="请输入隐患级别"
            ></a-input>
          </a-form-item>

          <a-form-item label="隐患类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'problemCategory', validatorRules.problemCategory]"
              placeholder="请输入隐患类别"
            ></a-input>
          </a-form-item>

          <a-form-item label="专业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'professionalCategory', validatorRules.professionalCategory]"
              placeholder="请输入专业类别"
            ></a-input>
          </a-form-item>

          <a-form-item label="风险级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'riskLevel', validatorRules.riskLevel]" placeholder="请输入风险级别"></a-input>
          </a-form-item>

          <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
          </a-form-item>
        
          
          <a-form-item label="整改部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformDepart', validatorRules.reformDepart]"
              placeholder="请输入整改部门"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改责任人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformResponsible', validatorRules.reformResponsible]"
              placeholder="请输入整改责任人"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改期限" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformDeadline', validatorRules.reformDeadline]"
              placeholder="请输入整改期限"
            ></a-input>
          </a-form-item>

          <a-form-item label="验收类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'reviewType', validatorRules.reviewType]" placeholder="请输入验收类型"></a-input>
          </a-form-item>

          <a-form-item label="处理时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'dealTime', validatorRules.dealTime]" placeholder="请输入处理时间"></a-input>
          </a-form-item>
          </template>


          <template v-else-if="flag==3">
          <a-form-item label="整改措施" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformAction', validatorRules.reformAction]"
              placeholder="请输入整改措施"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改投入资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformMoney', validatorRules.reformMoney]"
              placeholder="请输入整改投入资金"
            ></a-input>
          </a-form-item>

          <a-form-item label="预防措施" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'precautionAction', validatorRules.precautionAction]"
              placeholder="请输入预防措施"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformCompleteTime', validatorRules.reformCompleteTime]"
              placeholder="请输入整改完成时间"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'reformPic', validatorRules.reformPic]" placeholder="请输入整改照片"></a-input>
          </a-form-item>

          <a-form-item label="整改提交人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformUsers', validatorRules.reformUsers]"
              placeholder="请输入整改提交人"
            ></a-input>
          </a-form-item>

          <a-form-item label="整改提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reformTime', validatorRules.reformTime]"
              placeholder="请输入整改提交时间"
            ></a-input>
          </a-form-item>
        </template>
        <template v-else-if="flag==4">
          <a-form-item label="验收结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reviewResult', validatorRules.reviewResult]"
              placeholder="请输入验收结果"
            ></a-input>
          </a-form-item>

          <a-form-item label="验收意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reviewOpinion', validatorRules.reviewOpinion]"
              placeholder="请输入验收意见"
            ></a-input>
          </a-form-item>

          <a-form-item label="验收照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'reviewPic', validatorRules.reviewPic]" placeholder="请输入验收照片"></a-input>
          </a-form-item>

          <a-form-item label="验收人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reviewUsers', validatorRules.reviewUsers]"
              placeholder="请输入验收人"
            ></a-input>
          </a-form-item>

          <a-form-item label="验收提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="[ 'reviewTime', validatorRules.reviewTime]"
              placeholder="请输入验收提交时间"
            ></a-input>
          </a-form-item>
        </template>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import JDate from '@/components/jeecg/JDate'
import JDictSelectTag from "@/components/dict/JDictSelectTag"

export default {
  name: 'ProblemModal',
  components: {
    JDate,
    JDictSelectTag,
  },
  data() {
    return {
      flag:'1',
      form: this.$form.createForm(this),
      title: '操作',
      width: 800,
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
      validatorRules: {
        code: {},
        description: {},
        status: {},
        area: {},
        problemPic: {},
        companyId: {},
        checkMethod: {},
        checkType: {},
        checkUsers: {},
        checkTime: {},
        submitTime: {},
        problemLevel: {},
        problemCategory: {},
        professionalCategory: {},
        riskLevel: {},
        remark: {},
        reformDepart: {},
        reformResponsible: {},
        reformDeadline: {},
        reviewType: {},
        dealTime: {},
        reformAction: {},
        reformMoney: {},
        precautionAction: {},
        reformCompleteTime: {},
        reformPic: {},
        reformUsers: {},
        reformTime: {},
        reviewResult: {},
        reviewOpinion: {},
        reviewPic: {},
        reviewUsers: {},
        reviewTime: {}
      },
      url: {
        add: '/problem/problem/add',
        edit: '/problem/problem/edit'
      }
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    getFlag(index){
      this.flag=index;
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'code',
            'description',
            'status',
            'area',
            'problemPic',
            'companyId',
            'checkMethod',
            'checkType',
            'checkUsers',
            'checkTime',
            'submitTime',
            'problemLevel',
            'problemCategory',
            'professionalCategory',
            'riskLevel',
            'remark',
            'reformDepart',
            'reformResponsible',
            'reformDeadline',
            'reviewType',
            'dealTime',
            'reformAction',
            'reformMoney',
            'precautionAction',
            'reformCompleteTime',
            'reformPic',
            'reformUsers',
            'reformTime',
            'reviewResult',
            'reviewOpinion',
            'reviewPic',
            'reviewUsers',
            'reviewTime'
          )
        )
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
          if(this.flag == 1){
            this.model.status = 1;
          }else if(this.flag == 2){
            this.model.status = 2;
          }else if(this.flag == 3){
            this.model.status =3;
          }else if(this.flag == 4){
            this.model.status =4;
          }
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
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
    popupCallback(row) {
      this.form.setFieldsValue(
        pick(
          row,
          'code',
          'description',
          'status',
          'area',
          'problemPic',
          'companyId',
          'checkMethod',
          'checkType',
          'checkUsers',
          'checkTime',
          'submitTime',
          'problemLevel',
          'problemCategory',
          'professionalCategory',
          'riskLevel',
          'remark',
          'reformDepart',
          'reformResponsible',
          'reformDeadline',
          'reviewType',
          'dealTime',
          'reformAction',
          'reformMoney',
          'precautionAction',
          'reformCompleteTime',
          'reformPic',
          'reformUsers',
          'reformTime',
          'reviewResult',
          'reviewOpinion',
          'reviewPic',
          'reviewUsers',
          'reviewTime'
        )
      )
    }
  }
}
</script>