<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
		        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司编号">
          <a-input placeholder="请输入公司编号" v-decorator="['companyid', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="名称">
          <a-input placeholder="请输入名称" v-decorator="['name', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="辨识方法">
          <a-input placeholder="请输入辨识方法" v-decorator="['identificationtype', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单元类型">
          <a-input placeholder="请输入单元类型" v-decorator="['unittype', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单元级别">
          <a-input placeholder="请输入单元级别" v-decorator="['unitlevel', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="上级单元">
          <a-input placeholder="请输入上级单元" v-decorator="['parentunit', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="位置">
          <a-input placeholder="请输入位置" v-decorator="['place', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="位置">
          <a-input placeholder="请输入位置" v-decorator="['building', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="责任人">
          <a-input placeholder="请输入责任人" v-decorator="['personliable', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="管控部门">
          <a-input placeholder="请输入管控部门" v-decorator="['chargedepartment', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="可能导致事故类型">
          <a-input placeholder="请输入可能导致事故类型" v-decorator="['accidentype', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="事故后果">
          <a-input placeholder="请输入事故后果" v-decorator="['accidentconsequence', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="风险值D">
          <a-input-number v-decorator="[ 'lecD', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="风险级别">
          <a-input placeholder="请输入风险级别" v-decorator="['riskgrade', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="评估级别">
          <a-input placeholder="请输入评估级别" v-decorator="['evaluationgrade', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="管控水平">
          <a-input placeholder="请输入管控水平" v-decorator="['controllevel', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分析时间">
          <a-input placeholder="请输入分析时间" v-decorator="['analysistime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参与人">
          <a-input placeholder="请输入参与人" v-decorator="['participant', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <a-input placeholder="请输入状态" v-decorator="['status', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: 'RiskUnitModal',
    components: {
      JDate
    },
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
        },
        url: {
          add: "/risk/riskUnit/add",
          edit: "/risk/riskUnit/edit",
          riskAnalysisList: '/risk/riskUnit/queryRiskAnalysisByMainId',
          riskIdentificationList: '/risk/riskUnit/queryRiskIdentificationByMainId',
          riskMeasuresList: '/risk/riskUnit/queryRiskMeasuresByMainId',
          riskItemList: '/risk/riskUnit/queryRiskItemByMainId',
        }
      }
    },
    methods: {
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        //初始化明细表数据
        console.log(this.model.id)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'companyid', 'name', 'identificationtype', 'unittype', 'unitlevel', 'parentunit', 'place', 'building', 'personliable', 'chargedepartment', 'accidentype', 'accidentconsequence', 'lecD', 'riskgrade', 'evaluationgrade', 'controllevel', 'analysistime', 'participant', 'status', ))
          // 时间格式化
        });
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
        this.close()
      }
    }
  }
</script>

<style scoped>
  .ant-btn {
    padding: 0 10px;
    margin-left: 3px;
  }

  .ant-form-item-control {
    line-height: 0px;
  }

  /** 主表单行间距 */
  .ant-form .ant-form-item {
    margin-bottom: 10px;
  }

  /** Tab页面行间距 */
  .ant-tabs-content .ant-form-item {
    margin-bottom: 0px;
  }
</style>