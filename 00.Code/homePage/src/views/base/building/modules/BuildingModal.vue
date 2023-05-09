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

        <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入所属企业"></a-input>
        </a-form-item>
          
        <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'areaId', validatorRules.areaId]" placeholder="请输入区域"></a-input>
        </a-form-item>
          
        <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
        </a-form-item>
          
        <a-form-item label="建筑类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'type', validatorRules.type]" placeholder="请输入建筑类型" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
        </a-form-item>
          
        <a-form-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gpsLati', validatorRules.gpsLati]" placeholder="请输入经度"></a-input>
        </a-form-item>
          
        <a-form-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gpsLongi', validatorRules.gpsLongi]" placeholder="请输入纬度"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "BuildingModal",
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
        companyId:{},
        areaId:{},
        name:{},
        type:{},
        location:{},
        gpsLati:{},
        gpsLongi:{},
        },
        url: {
          add: "/base.building/building/add",
          edit: "/base.building/building/edit",
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
          this.form.setFieldsValue(pick(this.model,'companyId','areaId','name','type','location','gpsLati','gpsLongi'))
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
        this.form.setFieldsValue(pick(row,'companyId','areaId','name','type','location','gpsLati','gpsLongi'))
      }
      
    }
  }
</script>