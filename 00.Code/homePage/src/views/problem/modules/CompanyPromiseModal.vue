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

        <a-form-item label="日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择日期" v-decorator="[ 'promiseDate', validatorRules.promiseDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入所属企业"></a-input>
        </a-form-item>
          
        <a-form-item label="生成装置总套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'totalNum', validatorRules.totalNum]" placeholder="请输入生成装置总套"></a-input>
        </a-form-item>
          
        <a-form-item label="生成装置运行套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'runNum', validatorRules.runNum]" placeholder="请输入生成装置运行套"></a-input>
        </a-form-item>
          
        <a-form-item label="生成装置停产套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stopNum', validatorRules.stopNum]" placeholder="请输入生成装置停产套"></a-input>
        </a-form-item>
          
        <a-form-item label="生成装置检修套" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'repairNum', validatorRules.repairNum]" placeholder="请输入生成装置检修套"></a-input>
        </a-form-item>
          
        <a-form-item label="特殊动火" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workNum1', validatorRules.workNum1]" placeholder="请输入特殊动火"></a-input>
        </a-form-item>
          
        <a-form-item label="一级动火" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workNum2', validatorRules.workNum2]" placeholder="请输入一级动火"></a-input>
        </a-form-item>
          
        <a-form-item label="二级动火" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workNum3', validatorRules.workNum3]" placeholder="请输入二级动火"></a-input>
        </a-form-item>
          
        <a-form-item label="受限空间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workNum4', validatorRules.workNum4]" placeholder="请输入受限空间"></a-input>
        </a-form-item>
          
        <a-form-item label="是否处于试生产" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'preproFlag', validatorRules.preproFlag]" placeholder="请输入是否处于试生产" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="是否处于开停车状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'stopFlag', validatorRules.stopFlag]" placeholder="请输入是否处于开停车状态" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="是否处于安全状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'safeFlag', validatorRules.safeFlag]" placeholder="请输入是否处于安全状态" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="企业承诺内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'content', validatorRules.content]" placeholder="请输入企业承诺内容"></a-input>
        </a-form-item>
          
        <a-form-item label="主要负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'principal', validatorRules.principal]" placeholder="请输入主要负责人"></a-input>
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
    name: "CompanyPromiseModal",
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
        promiseDate:{},
        companyId:{},
        totalNum:{},
        runNum:{},
        stopNum:{},
        repairNum:{},
        workNum1:{},
        workNum2:{},
        workNum3:{},
        workNum4:{},
        preproFlag:{},
        stopFlag:{},
        safeFlag:{},
        content:{},
        principal:{},
        },
        url: {
          add: "/companyPromise/add",
          edit: "/companyPromise/edit",
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
          this.form.setFieldsValue(pick(this.model,'promiseDate','companyId','totalNum','runNum','stopNum','repairNum','workNum1','workNum2','workNum3','workNum4','preproFlag','stopFlag','safeFlag','content','principal'))
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
        this.form.setFieldsValue(pick(row,'promiseDate','companyId','totalNum','runNum','stopNum','repairNum','workNum1','workNum2','workNum3','workNum4','preproFlag','stopFlag','safeFlag','content','principal'))
      }
      
    }
  }
</script>