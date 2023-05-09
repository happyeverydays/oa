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

        <a-form-item label="危险源名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入危险源名称"></a-input>
        </a-form-item>
          
        <a-form-item label="包含场所" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'places', validatorRules.places]" placeholder="请输入包含场所"></a-input>
        </a-form-item>
          
        <a-form-item label="投用时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'operationTime', validatorRules.operationTime]" placeholder="请输入投用时间"></a-input>
        </a-form-item>
          
        <a-form-item label="级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'clevel', validatorRules.clevel]" placeholder="请输入级别"></a-input>
        </a-form-item>
          
        <a-form-item label="设施及生产（存储）规模" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'scale', validatorRules.scale]" placeholder="请输入设施及生产（存储）规模"></a-input>
        </a-form-item>
          
        <a-form-item label="与周边重点防护目标最近距离情况（m）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'distance', validatorRules.distance]" placeholder="请输入与周边重点防护目标最近距离情况（m）"></a-input>
        </a-form-item>
          
        <a-form-item label="近三年内危险化学品事故情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'accidentInfo', validatorRules.accidentInfo]" placeholder="请输入近三年内危险化学品事故情况"></a-input>
        </a-form-item>
          
        <a-form-item label="主要装置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'devices', validatorRules.devices]" placeholder="请输入主要装置"></a-input>
        </a-form-item>
          
        <a-form-item label="监测设备" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'equipment', validatorRules.equipment]" placeholder="请输入监测设备"></a-input>
        </a-form-item>
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "MajorHazardsModal",
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
        places:{},
        operationTime:{},
        clevel:{},
        scale:{},
        distance:{},
        accidentInfo:{},
        devices:{},
        equipment:{},
        remark:{},
        },
        url: {
          add: "/iot.majorhazards/majorHazards/add",
          edit: "/iot.majorhazards/majorHazards/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','places','operationTime','clevel','scale','distance','accidentInfo','devices','equipment','remark'))
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
        this.form.setFieldsValue(pick(row,'name','places','operationTime','clevel','scale','distance','accidentInfo','devices','equipment','remark'))
      }
      
    }
  }
</script>