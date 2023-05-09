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

        <a-form-item label="companyid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyid', validatorRules.companyid]" placeholder="请输入companyid"></a-input>
        </a-form-item>
        <a-form-item label="parentid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parentid', validatorRules.parentid]" placeholder="请输入parentid"></a-input>
        </a-form-item>
        <a-form-item label="type" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'type', validatorRules.type]" placeholder="请输入type"></a-input>
        </a-form-item>
        <a-form-item label="createtime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择createtime" v-decorator="[ 'createtime', validatorRules.createtime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="projectid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'projectid', validatorRules.projectid]" placeholder="请输入projectid"></a-input>
        </a-form-item>
        <a-form-item label="basecompanyid" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basecompanyid', validatorRules.basecompanyid]" placeholder="请输入basecompanyid"></a-input>
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
    name: "EduExamTypeModal",
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
        companyid:{rules: [{ required: true, message: '请输入companyid!' }]},
        parentid:{},
        type:{},
        createtime:{rules: [{ required: true, message: '请输入createtime!' }]},
        projectid:{},
        basecompanyid:{rules: [{ required: true, message: '请输入basecompanyid!' }]},
        },
        url: {
          add: "/base/examType/eduExamType/add",
          edit: "/base/examType/eduExamType/edit",
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
          this.form.setFieldsValue(pick(this.model,'companyid','parentid','type','createtime','projectid','basecompanyid'))
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
        this.form.setFieldsValue(pick(row,'companyid','parentid','type','createtime','projectid','basecompanyid'))
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