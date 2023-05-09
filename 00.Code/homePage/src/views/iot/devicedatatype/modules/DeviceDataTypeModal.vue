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
          
        <a-form-item label="设备" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'deviceId', validatorRules.deviceId]" placeholder="请输入设备"></a-input>
        </a-form-item>
          
        <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'seq', validatorRules.seq]" placeholder="请输入序号"></a-input>
        </a-form-item>
          
        <a-form-item label="数据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dataType', validatorRules.dataType]" placeholder="请输入数据类型"></a-input>
        </a-form-item>
          
        <a-form-item label="监测参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dataName', validatorRules.dataName]" placeholder="请输入监测参数"></a-input>
        </a-form-item>
          
        <a-form-item label="应用类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'applicationType', validatorRules.applicationType]" placeholder="请输入应用类型"></a-input>
        </a-form-item>
          
        <a-form-item label="参数类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parameterType', validatorRules.parameterType]" placeholder="请输入参数类型"></a-input>
        </a-form-item>
          
        <a-form-item label="最新值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lastValue', validatorRules.lastValue]" placeholder="请输入最新值"></a-input>
        </a-form-item>
          
        <a-form-item label="上报时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lastTime', validatorRules.lastTime]" placeholder="请输入上报时间"></a-input>
        </a-form-item>
          
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入单位"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "DeviceDataTypeModal",
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
        deviceId:{},
        seq:{},
        dataType:{},
        dataName:{},
        applicationType:{},
        parameterType:{},
        lastValue:{},
        lastTime:{},
        unit:{},
        },
        url: {
          add: "/iot.devicedatatype/deviceDataType/add",
          edit: "/iot.devicedatatype/deviceDataType/edit",
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
          this.form.setFieldsValue(pick(this.model,'companyId','deviceId','seq','dataType','dataName','applicationType','parameterType','lastValue','lastTime','unit'))
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
        this.form.setFieldsValue(pick(row,'companyId','deviceId','seq','dataType','dataName','applicationType','parameterType','lastValue','lastTime','unit'))
      }
      
    }
  }
</script>