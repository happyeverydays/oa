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

        <a-form-item label="上级企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parentId', validatorRules.parentId]" placeholder="请输入上级企业"></a-input>
        </a-form-item>
          
        <a-form-item label="企业编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入企业编号"></a-input>
        </a-form-item>
          
        <a-form-item label="企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入企业名称"></a-input>
        </a-form-item>
          
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'principal', validatorRules.principal]" placeholder="请输入负责人"></a-input>
        </a-form-item>
          
        <a-form-item label="注册地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'regAddress', validatorRules.regAddress]" placeholder="请输入注册地址"></a-input>
        </a-form-item>
          
        <a-form-item label="经营地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'proAddress', validatorRules.proAddress]" placeholder="请输入经营地址"></a-input>
        </a-form-item>
          
        <a-form-item label="占地面积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'landArea', validatorRules.landArea]" placeholder="请输入占地面积"></a-input>
        </a-form-item>
          
        <a-form-item label="建筑面积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'buildingArea', validatorRules.buildingArea]" placeholder="请输入建筑面积"></a-input>
        </a-form-item>
          
        <a-form-item label="省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省"></a-input>
        </a-form-item>
          
        <a-form-item label="市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市"></a-input>
        </a-form-item>
          
        <a-form-item label="区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区"></a-input>
        </a-form-item>
          
        <a-form-item label="镇" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'town', validatorRules.town]" placeholder="请输入镇"></a-input>
        </a-form-item>
          
        <a-form-item label="园区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'park', validatorRules.park]" placeholder="请输入园区"></a-input>
        </a-form-item>
          
        <a-form-item label="网格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'grid', validatorRules.grid]" placeholder="请输入网格"></a-input>
        </a-form-item>
          
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
        </a-form-item>
          
        <a-form-item label="公司介绍" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'profile', validatorRules.profile]" placeholder="请输入公司介绍"></a-input>
        </a-form-item>
          
        <a-form-item label="主营业务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mainBusiness', validatorRules.mainBusiness]" placeholder="请输入主营业务"></a-input>
        </a-form-item>
          
        <a-form-item label="Logo" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'logoUrl', validatorRules.logoUrl]" placeholder="请输入Logo"></a-input>
        </a-form-item>
          
        <a-form-item label="营业执照" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'businessLicenseFile', validatorRules.businessLicenseFile]" placeholder="请输入营业执照"></a-input>
        </a-form-item>
          
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'status', validatorRules.status]" placeholder="请输入状态" style="width: 100%"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "CompanyModal",
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
        parentId:{},
        code:{},
        name:{rules: [{ required: true, message: '请输入企业名称!' }]},
        principal:{},
        regAddress:{},
        proAddress:{},
        landArea:{},
        buildingArea:{},
        province:{},
        city:{},
        area:{},
        town:{},
        park:{},
        grid:{},
        address:{},
        profile:{},
        mainBusiness:{},
        logoUrl:{},
        businessLicenseFile:{},
        status:{},
        },
        url: {
          add: "/base.company/company/add",
          edit: "/base.company/company/edit",
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
          this.form.setFieldsValue(pick(this.model,'parentId','code','name','principal','regAddress','proAddress','landArea','buildingArea','province','city','area','town','park','grid','address','profile','mainBusiness','logoUrl','businessLicenseFile','status'))
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
        this.form.setFieldsValue(pick(row,'parentId','code','name','principal','regAddress','proAddress','landArea','buildingArea','province','city','area','town','park','grid','address','profile','mainBusiness','logoUrl','businessLicenseFile','status'))
      }
      
    }
  }
</script>