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

        <a-form-item label="企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入企业"></a-input>
        </a-form-item>
          
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'type', validatorRules.type]" placeholder="请输入类型" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入姓名"></a-input>
        </a-form-item>
          
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'gender', validatorRules.gender]" placeholder="请输入性别" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号"></a-input>
        </a-form-item>
          
        <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'idCard', validatorRules.idCard]" placeholder="请输入身份证号"></a-input>
        </a-form-item>
          
        <a-form-item label="部门选择" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <j-select-depart v-decorator="['departmentId']" :multi="true"/>
        </a-form-item>
          
        <a-form-item label="岗位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'positionId', validatorRules.positionId]" placeholder="请输入岗位"></a-input>
        </a-form-item>
          
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'education', validatorRules.education]" placeholder="请输入学历"></a-input>
        </a-form-item>
          
        <a-form-item label="在岗状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'status', validatorRules.status]" placeholder="请输入在岗状态"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  
  export default {
    name: "UserModal",
    components: { 
      JSelectDepart,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        departmentId:"",
        departmentIds:"",

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
        companyId:{},
        type:{},
        name:{},
        gender:{},
        phone:{},
        idCard:{},
        departmentId:{},
        positionId:{},
        education:{},
        status:{},
        },
        url: {
          add: "/user/add",
          edit: "/user/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      getFormFieldValue(field){
        return this.form.getFieldValue(field)
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'companyId','type','name','gender','phone','idCard','departmentId','positionId','education','status'))
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
        this.form.setFieldsValue(pick(row,'companyId','type','name','gender','phone','idCard','departmentId','positionId','education','status'))
      }
      
    }
  }
</script>