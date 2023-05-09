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

        <a-form-item label="危化品" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入危化品"></a-input>
        </a-form-item>
        <a-form-item label="储存方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'storageMode', validatorRules.storageMode]" placeholder="请输入储存方式"></a-input>
        </a-form-item>
        <a-form-item label="储存地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'storageAddress', validatorRules.storageAddress]" placeholder="请输入储存地址"></a-input>
        </a-form-item>
        <a-form-item label="规模" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'storageNums', validatorRules.storageNums]" placeholder="请输入规模"></a-input>
        </a-form-item>
        <a-form-item label="处置文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'disposalFile', validatorRules.disposalFile]" placeholder="请输入处置文件"></a-input>
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
  
  export default {
    name: "HazardousMaterialModal",
    components: { 
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
        name:{},
        storageMode:{},
        storageAddress:{},
        storageNums:{},
        disposalFile:{},
        },
        url: {
          add: "/iot.hazardousmaterial/hazardousMaterial/add",
          edit: "/iot.hazardousmaterial/hazardousMaterial/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','storageMode','storageAddress','storageNums','disposalFile'))
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
        this.form.setFieldsValue(pick(row,'name','storageMode','storageAddress','storageNums','disposalFile'))
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