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

        <a-form-item label="流程key" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'processKey', validatorRules.processKey]"  disabled placeholder="请输入流程key"></a-input>
        </a-form-item>
          
        <a-form-item label="唯一编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'relationCode', validatorRules.relationCode]" placeholder="请输入唯一编码"></a-input>
        </a-form-item>
          
        <a-form-item label="表名/表单code" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'formTableName', validatorRules.formTableName]" placeholder="请输入表名/表单code"></a-input>
        </a-form-item>

         <a-form-item label="表单地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'formKey', validatorRules.formKey]" placeholder="请输入表单地址"></a-input>
        </a-form-item>
          
        <!-- <a-form-item label="表单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['formType']" :trigger-change="true" dictCode="form_type" placeholder="请选择表单类型"/>
        </a-form-item>
          
        <a-form-item label="表单处理样式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'formDealStyle', validatorRules.formDealStyle]" placeholder="请输入表单处理样式"></a-input>
        </a-form-item> -->
          
        <a-form-item label="业务标题表达式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'titleExp', validatorRules.titleExp]" placeholder="请输入业务标题表达式"></a-input>
        </a-form-item>
          
        <a-form-item label="流程状态列名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'flowStatusCol', validatorRules.flowStatusCol]" placeholder="请输入流程状态列名"></a-input>
        </a-form-item>
        <a-form-item label="变量sql" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="[ 'varSql']" placeholder="请输入变量sql"  rows="10"></a-textarea>
        </a-form-item>
         <a-form-item label="变量接口" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="[ 'varInterface']" placeholder="请输入变量接口"  rows="10"></a-textarea>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ExtActProcessFormModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1300,
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
        processKey:{},
        relationCode:{},
        formTableName:{},
        formType:{},
        formDealStyle:{},
        titleExp:{},
        flowStatusCol:{},
        },
        url: {
          add: "/workflow/extActProcessForm/add",
          edit: "/workflow/extActProcessForm/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add (key) {
        // console.log(key)
        this.edit({
          processKey:key
        });
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'processKey','relationCode','formTableName','formType','formDealStyle','titleExp','flowStatusCol','formKey','varSql','varInterface'))
            console.log(this.form)
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
        this.form.setFieldsValue(pick(row,'processKey','relationCode','formTableName','formType','formDealStyle','titleExp','flowStatusCol','formKey'))
      
      }
      
    }
  }
</script>