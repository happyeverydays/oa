<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司编号"
          hasFeedback>
          <a-input placeholder="请输入公司编号" v-decorator="['companyid', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="风险单元编号"
          :hidden="hiding"
          hasFeedback>
          <a-input placeholder="请输入风险单元编号" v-decorator="['riskunitid', {'initialValue':this.mainId}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="检查项目"
          hasFeedback>
          <a-input placeholder="请输入检查项目" v-decorator="['inspectionproject', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="检查标准"
          hasFeedback>
          <a-input placeholder="请输入检查标准" v-decorator="['inspectionstandard', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="本次分析前事故次数"
          hasFeedback>
          <a-input placeholder="请输入本次分析前事故次数" v-decorator="['accidentcount', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="事故发生可能性L"
          hasFeedback>
          <a-input placeholder="请输入事故发生可能性L" v-decorator="['lecL', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="人员暴露频繁程度E"
          hasFeedback>
          <a-input placeholder="请输入人员暴露频繁程度E" v-decorator="['lecE', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="事故后果严重性C"
          hasFeedback>
          <a-input placeholder="请输入事故后果严重性C" v-decorator="['lecC', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="风险值D"
          hasFeedback>
          <a-input placeholder="请输入风险值D" v-decorator="['lecD', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="风险等级"
          hasFeedback>
          <a-input placeholder="请输入风险等级" v-decorator="['riskgrade', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="评价等级"
          hasFeedback>
          <a-input placeholder="请输入评价等级" v-decorator="['evaluationgrade', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="管控级别"
          hasFeedback>
          <a-input placeholder="请输入管控级别" v-decorator="['controllevel', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="管控措施"
          hasFeedback>
          <a-input placeholder="请输入管控措施" v-decorator="['proposestep', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否正常"
          hasFeedback>
          <a-input placeholder="请输入是否正常" v-decorator="['isok', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否检查"
          hasFeedback>
          <a-input placeholder="请输入是否检查" v-decorator="['ischeck', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="频次"
          hasFeedback>
          <a-input placeholder="请输入频次" v-decorator="['frequency', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="检查岗位"
          hasFeedback>
          <a-input placeholder="请输入检查岗位" v-decorator="['inspectionpost', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分析时间"
          hasFeedback>
          <a-input placeholder="请输入分析时间" v-decorator="['analysistime', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参与人"
          hasFeedback>
          <a-input placeholder="请输入参与人" v-decorator="['participant', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注"
          hasFeedback>
          <a-input placeholder="请输入备注" v-decorator="['remark', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="附件"
          hasFeedback>
          <a-input placeholder="请输入附件" v-decorator="['attachment', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from 'moment'
  import JDate from '@/components/jeecg/JDate'

  export default {
    components: {
      JDate
    },
    name: 'RiskAnalysisModal',
    data() {
      return {
        title: '操作',
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
        moment,
        format: 'YYYY-MM-DD HH:mm:ss',
        disableSubmit: false,
        mainId: '',
        hiding: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
        },
        url: {
          add: '/risk/riskUnit/addRiskAnalysis',
          edit: '/risk/riskUnit/editRiskAnalysis'
        }
      }
    },
    created() {
    },
    methods: {
      add(mainId) {
        if (mainId) {
          this.edit({mainId}, '')
        } else {
          this.$message.warning('请选择一条数据')
        }
      },
      detail(record) {
        this.edit(record, 'd')
      },
      edit(record, v) {
        if (v == 'e') {
          this.hiding = false;
          this.disableSubmit = false;
        } else if (v == 'd') {
          this.hiding = false;
          this.disableSubmit = true;
        } else {
          this.hiding = true;
          this.disableSubmit = false;
        }
        this.form.resetFields();
        this.mainId = record.mainId;
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'companyid', 'riskunitid', 'inspectionproject', 'inspectionstandard', 'accidentcount', 'lecL', 'lecE', 'lecC', 'lecD', 'riskgrade', 'evaluationgrade', 'controllevel', 'proposestep', 'isok', 'ischeck', 'frequency', 'inspectionpost', 'analysistime', 'participant', 'remark', 'attachment', ))
          // 时间格式化
        })
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel() {
        this.close();
      }
    }
  }
</script>

<style scoped>

</style>
