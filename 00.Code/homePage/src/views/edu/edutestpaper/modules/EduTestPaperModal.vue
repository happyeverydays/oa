<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="companyid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入companyid"></a-input>
        </a-form-item>
          
        <a-form-item label="title" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入title"></a-input>
        </a-form-item>
          
        <a-form-item label="mode" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'mode', validatorRules.mode]" placeholder="请输入mode" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="type" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'type', validatorRules.type]" placeholder="请输入type"></a-input>
        </a-form-item>
          
        <a-form-item label="departmentids" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'departmentIds', validatorRules.departmentIds]" placeholder="请输入departmentids"></a-input>
        </a-form-item>
          
        <a-form-item label="departmentnames" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'departmentNames', validatorRules.departmentNames]" placeholder="请输入departmentnames"></a-input>
        </a-form-item>
          
        <a-form-item label="passscore" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'passScore', validatorRules.passScore]" placeholder="请输入passscore" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="totalscore" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'totalScore', validatorRules.totalScore]" placeholder="请输入totalscore" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="accountid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'accountId', validatorRules.accountId]" placeholder="请输入accountid"></a-input>
        </a-form-item>
          
        <a-form-item label="status" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'status', validatorRules.status]" placeholder="请输入status" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="delflag" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'delFlag', validatorRules.delFlag]" placeholder="请输入delflag" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="createtime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择createtime" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="basecompanyid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'baseCompanyId', validatorRules.baseCompanyId]" placeholder="请输入basecompanyid"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "EduTestPaperModal",
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
        companyId:{rules: [{ required: true, message: '请输入companyid!' }]},
        title:{},
        mode:{},
        type:{},
        departmentIds:{},
        departmentNames:{},
        passScore:{},
        totalScore:{},
        accountId:{},
        status:{},
        delFlag:{},
        createTime:{rules: [{ required: true, message: '请输入createtime!' }]},
        baseCompanyId:{rules: [{ required: true, message: '请输入basecompanyid!' }]},
        },
        url: {
          add: "/edu/eduTestPaper/add",
          edit: "/edu/eduTestPaper/edit",
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
          this.form.setFieldsValue(pick(this.model,'companyId','title','mode','type','departmentIds','departmentNames','passScore','totalScore','accountId','status','delFlag','createTime','baseCompanyId'))
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
        this.form.setFieldsValue(pick(row,'companyId','title','mode','type','departmentIds','departmentNames','passScore','totalScore','accountId','status','delFlag','createTime','baseCompanyId'))
      }
      
    }
  }
</script>