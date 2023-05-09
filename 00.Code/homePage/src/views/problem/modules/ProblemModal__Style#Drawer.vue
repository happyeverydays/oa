<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入编码"></a-input>
        </a-form-item>
        <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'description', validatorRules.description]" placeholder="请输入描述"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'status', validatorRules.status]" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="隐患所在区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入隐患所在区域"></a-input>
        </a-form-item>
        <a-form-item label="隐患照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'problemPic', validatorRules.problemPic]" placeholder="请输入隐患照片"></a-input>
        </a-form-item>
        <a-form-item label="公司编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入公司编号"></a-input>
        </a-form-item>
        <a-form-item label="检查方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'checkMethod', validatorRules.checkMethod]" placeholder="请输入检查方式" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="检查类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'checkType', validatorRules.checkType]" placeholder="请输入检查类型"></a-input>
        </a-form-item>
        <a-form-item label="检查人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'checkUsers', validatorRules.checkUsers]" placeholder="请输入检查人"></a-input>
        </a-form-item>
        <a-form-item label="检查时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择检查时间" v-decorator="[ 'checkTime', validatorRules.checkTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'submitTime', validatorRules.submitTime]" placeholder="请输入提交时间"></a-input>
        </a-form-item>
        <a-form-item label="隐患级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'problemLevel', validatorRules.problemLevel]" placeholder="请输入隐患级别"></a-input>
        </a-form-item>
        <a-form-item label="隐患类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'problemCategory', validatorRules.problemCategory]" placeholder="请输入隐患类别"></a-input>
        </a-form-item>
        <a-form-item label="专业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'professionalCategory', validatorRules.professionalCategory]" placeholder="请输入专业类别"></a-input>
        </a-form-item>
        <a-form-item label="风险级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'riskLevel', validatorRules.riskLevel]" placeholder="请输入风险级别"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="整改部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformDepart', validatorRules.reformDepart]" placeholder="请输入整改部门"></a-input>
        </a-form-item>
        <a-form-item label="整改责任人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformResponsible', validatorRules.reformResponsible]" placeholder="请输入整改责任人"></a-input>
        </a-form-item>
        <a-form-item label="整改期限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformDeadline', validatorRules.reformDeadline]" placeholder="请输入整改期限"></a-input>
        </a-form-item>
        <a-form-item label="验收类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewType', validatorRules.reviewType]" placeholder="请输入验收类型"></a-input>
        </a-form-item>
        <a-form-item label="处理时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dealTime', validatorRules.dealTime]" placeholder="请输入处理时间"></a-input>
        </a-form-item>
        <a-form-item label="整改措施" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformAction', validatorRules.reformAction]" placeholder="请输入整改措施"></a-input>
        </a-form-item>
        <a-form-item label="整改投入资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformMoney', validatorRules.reformMoney]" placeholder="请输入整改投入资金"></a-input>
        </a-form-item>
        <a-form-item label="预防措施" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'precautionAction', validatorRules.precautionAction]" placeholder="请输入预防措施"></a-input>
        </a-form-item>
        <a-form-item label="整改完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformCompleteTime', validatorRules.reformCompleteTime]" placeholder="请输入整改完成时间"></a-input>
        </a-form-item>
        <a-form-item label="整改照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformPic', validatorRules.reformPic]" placeholder="请输入整改照片"></a-input>
        </a-form-item>
        <a-form-item label="整改提交人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformUsers', validatorRules.reformUsers]" placeholder="请输入整改提交人"></a-input>
        </a-form-item>
        <a-form-item label="整改提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reformTime', validatorRules.reformTime]" placeholder="请输入整改提交时间"></a-input>
        </a-form-item>
        <a-form-item label="验收结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewResult', validatorRules.reviewResult]" placeholder="请输入验收结果"></a-input>
        </a-form-item>
        <a-form-item label="验收意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewOpinion', validatorRules.reviewOpinion]" placeholder="请输入验收意见"></a-input>
        </a-form-item>
        <a-form-item label="验收照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewPic', validatorRules.reviewPic]" placeholder="请输入验收照片"></a-input>
        </a-form-item>
        <a-form-item label="验收人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewUsers', validatorRules.reviewUsers]" placeholder="请输入验收人"></a-input>
        </a-form-item>
        <a-form-item label="验收提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'reviewTime', validatorRules.reviewTime]" placeholder="请输入验收提交时间"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "ProblemModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        code:{},
        description:{},
        status:{},
        area:{},
        problemPic:{},
        companyId:{},
        checkMethod:{},
        checkType:{},
        checkUsers:{},
        checkTime:{},
        submitTime:{},
        problemLevel:{},
        problemCategory:{},
        professionalCategory:{},
        riskLevel:{},
        remark:{},
        reformDepart:{},
        reformResponsible:{},
        reformDeadline:{},
        reviewType:{},
        dealTime:{},
        reformAction:{},
        reformMoney:{},
        precautionAction:{},
        reformCompleteTime:{},
        reformPic:{},
        reformUsers:{},
        reformTime:{},
        reviewResult:{},
        reviewOpinion:{},
        reviewPic:{},
        reviewUsers:{},
        reviewTime:{},
        },
        url: {
          add: "/problem/problem/add",
          edit: "/problem/problem/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','description','status','area','problemPic','companyId','checkMethod','checkType','checkUsers','checkTime','submitTime','problemLevel','problemCategory','professionalCategory','riskLevel','remark','reformDepart','reformResponsible','reformDeadline','reviewType','dealTime','reformAction','reformMoney','precautionAction','reformCompleteTime','reformPic','reformUsers','reformTime','reviewResult','reviewOpinion','reviewPic','reviewUsers','reviewTime'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','description','status','area','problemPic','companyId','checkMethod','checkType','checkUsers','checkTime','submitTime','problemLevel','problemCategory','professionalCategory','riskLevel','remark','reformDepart','reformResponsible','reformDeadline','reviewType','dealTime','reformAction','reformMoney','precautionAction','reformCompleteTime','reformPic','reformUsers','reformTime','reviewResult','reviewOpinion','reviewPic','reviewUsers','reviewTime'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>