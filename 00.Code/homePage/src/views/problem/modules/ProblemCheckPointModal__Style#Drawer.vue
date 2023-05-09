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

        <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入编码"></a-input>
        </a-form-item>
        <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入名称"></a-input>
        </a-form-item>
        <a-form-item label="风险单元编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['riskUnitId']" :trigger-change="true" dictCode="" placeholder="请选择风险单元编号"/>
        </a-form-item>
        <a-form-item label="风险级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'level', validatorRules.level]" placeholder="请输入风险级别"></a-input>
        </a-form-item>
        <a-form-item label="公司编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入公司编号"></a-input>
        </a-form-item>
        <a-form-item label="大类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['category1']" :trigger-change="true" dictCode="" placeholder="请选择大类"/>
        </a-form-item>
        <a-form-item label="中类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['category2']" :trigger-change="true" dictCode="" placeholder="请选择中类"/>
        </a-form-item>
        <a-form-item label="小类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['category3']" :trigger-change="true" dictCode="" placeholder="请选择小类"/>
        </a-form-item>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'depart', validatorRules.depart]" placeholder="请输入部门"></a-input>
        </a-form-item>
        <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
        </a-form-item>
        <a-form-item label="占地面积" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'landArea', validatorRules.landArea]" placeholder="请输入占地面积"></a-input>
        </a-form-item>
        <a-form-item label="区域结构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'structure', validatorRules.structure]" placeholder="请输入区域结构"></a-input>
        </a-form-item>
        <a-form-item label="巡检点类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'spaceType', validatorRules.spaceType]" placeholder="请输入巡检点类型" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="火灾危险等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fireLevel', validatorRules.fireLevel]" placeholder="请输入火灾危险等级"></a-input>
        </a-form-item>
        <a-form-item label="建筑完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'buildingFinishTime', validatorRules.buildingFinishTime]" placeholder="请输入建筑完成时间"></a-input>
        </a-form-item>
        <a-form-item label="建筑投用时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'buildingUseTime', validatorRules.buildingUseTime]" placeholder="请输入建筑投用时间"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'responsible', validatorRules.responsible]" placeholder="请输入负责人"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'attachment', validatorRules.attachment]" placeholder="请输入附件"></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ProblemCheckPointModal",
    components: { 
      JDictSelectTag,
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
        code:{},
        name:{},
        riskUnitId:{},
        level:{},
        companyId:{},
        category1:{},
        category2:{},
        category3:{},
        depart:{},
        location:{},
        landArea:{},
        structure:{},
        spaceType:{},
        fireLevel:{},
        buildingFinishTime:{},
        buildingUseTime:{},
        remark:{},
        responsible:{},
        attachment:{},
        },
        url: {
          add: "/problem/problemCheckPoint/add",
          edit: "/problem/problemCheckPoint/edit",
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
          this.form.setFieldsValue(pick(this.model,'code','name','riskUnitId','level','companyId','category1','category2','category3','depart','location','landArea','structure','spaceType','fireLevel','buildingFinishTime','buildingUseTime','remark','responsible','attachment'))
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
        this.form.setFieldsValue(pick(row,'code','name','riskUnitId','level','companyId','category1','category2','category3','depart','location','landArea','structure','spaceType','fireLevel','buildingFinishTime','buildingUseTime','remark','responsible','attachment'))
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