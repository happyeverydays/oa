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
          label="风险单元编号"
          :hidden="hiding"
          hasFeedback>
          <a-input placeholder="请输入风险单元编号" v-decorator="['riskunitid', {'initialValue':this.mainId}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="记录编号"
          hasFeedback>
          <a-input placeholder="请输入记录编号" v-decorator="['recordid', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="序号"
          hasFeedback>
          <a-input placeholder="请输入序号" v-decorator="['seq', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="管控措施"
          hasFeedback>
          <a-input placeholder="请输入管控措施" v-decorator="['content', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="source"
          hasFeedback>
          <a-input placeholder="请输入source" v-decorator="['source', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="clause"
          hasFeedback>
          <a-input placeholder="请输入clause" v-decorator="['clause', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="troublecontent"
          hasFeedback>
          <a-input placeholder="请输入troublecontent" v-decorator="['troublecontent', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="normalcontent"
          hasFeedback>
          <a-input placeholder="请输入normalcontent" v-decorator="['normalcontent', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="inspectionitem"
          hasFeedback>
          <a-input placeholder="请输入inspectionitem" v-decorator="['inspectionitem', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="type"
          hasFeedback>
          <a-input placeholder="请输入type" v-decorator="['type', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="检查频次"
          hasFeedback>
          <a-input placeholder="请输入检查频次" v-decorator="['frequency', {}]" />
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
          label="是否隐患排查"
          hasFeedback>
          <a-input placeholder="请输入是否隐患排查" v-decorator="['ischeck', validatorRules.ischeck ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态"
          hasFeedback>
          <a-input placeholder="请输入状态" v-decorator="['status', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="old"
          hasFeedback>
          <a-input placeholder="请输入old" v-decorator="['old', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="remark"
          hasFeedback>
          <a-input placeholder="请输入remark" v-decorator="['remark', {}]" />
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
    name: 'RiskItemModal',
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
          ischeck: { rules: [{ required: true, message: '请输入是否隐患排查!' }] },
        },
        url: {
          add: '/risk/riskUnit/addRiskItem',
          edit: '/risk/riskUnit/editRiskItem'
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
          this.form.setFieldsValue(pick(this.model, 'riskunitid', 'recordid', 'seq', 'content', 'source', 'clause', 'troublecontent', 'normalcontent', 'inspectionitem', 'type', 'frequency', 'inspectionpost', 'ischeck', 'status', 'old', 'remark', ))
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
