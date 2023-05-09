
<template>
    <div>
      <!-- {{formobj}}  -->
          <a-form :form="form" @submit="handleSubmit">
             <template v-for="(item,index) in formList">
    <a-form-item :label="item.label" :key="index" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-input 
      v-if="item.type=='text'"
      readonly
        v-decorator="[item.key, { rules: [{ required: true, message: item.placeholder }] }]"
          :placeholder="item.placeholder"
      />
       <a-select
       disabled
        v-if="item.type=='list'"
        v-decorator="[item.key, { rules: [{ required: true, message: item.placeholder }] }]"
        :placeholder="item.placeholder"
        @change="handleSelectChange"
      >
        <a-select-option v-for="(list,index) in item.optinons" :key="index" :value="list.value">
          {{list.text}}
        </a-select-option>
       
      </a-select>

       <a-select
        v-if="item.type=='list_multi'"
        disabled
        mode="multiple"
        v-decorator="[item.key, { rules: [{ required: true, message: item.placeholder }] }]"
        :placeholder="item.placeholder"
        @change="handleSelectChange"
      >
        <a-select-option v-for="(list,index) in item.optinons" :key="index" :value="list.value">
          {{list.text}}
        </a-select-option>
       
      </a-select>
       <a-date-picker 
       disabled
        v-if="item.type=='date'"
        :format='dateFormat'
        v-decorator="[item.key, { rules: [{ required: true, message: item.placeholder }] }]"
            :placeholder="item.placeholder"
       >
      <a-icon slot="suffixIcon" type="smile" />
    </a-date-picker>
        <a-textarea 
         readonly
         v-if="item.type=='textarea'"
        v-decorator="[item.key, { rules: [{ required: true, message: item.placeholder }] }]"
        
          :placeholder="item.placeholder" :rows="4" />
    </a-form-item>
   </template>
    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
      <a-button type="primary" html-type="submit">
        提交
      </a-button>
    </a-form-item>
  </a-form>
    </div>
</template>
<script>
import { axios } from '@/utils/request'
import moment from 'moment';
export default {
  props:['formobj'],
  data() {
    return {
       dateFormat:'YYYY-MM-DD HH:mm:ss',
      formLayout: 'horizontal',
      form: this.$form.createForm(this, { name: 'coordinated' }),
      formList:[
      
         
      ]
    };
  },
  mounted(){
   
       this.getformlist();

  },
  methods: {
    moment,
     getformlist(){
       let url='online/cgform/fieldExpand/listByHeadCode?formCode='+this.formobj.formKey+'&businessKey='+this.formobj.businessKey;
        axios.get(url).then(res => {
        console.log(res)
       this.formList=res.result.formList;
       let obj=res.result.formData;
       res.result.formList.map(item=>{
       
         if(item.type=='date'){
            obj[item.key]=moment(obj[item.key],this.dateFormat);
           
          
         }
       })
       console.log(obj)
       this.$nextTick(()=>{
           this.form.setFieldsValue({
       ...res.result.formData
      });
       })
      
      })
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
        }
      });
    },
    handleSelectChange(value) {
      console.log(value);
      this.form.setFieldsValue({
        note: `Hi, ${value === 'male' ? 'man' : 'lady'}!`,
      });
    },
  },
};
</script>