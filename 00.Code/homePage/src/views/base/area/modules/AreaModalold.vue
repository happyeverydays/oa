<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入所属企业"></a-input>
        </a-form-item>

        <a-form-item label="上级区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parentId', validatorRules.parentId]" placeholder="请输入上级区域"></a-input>
        </a-form-item>

        <a-form-item label="区域类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            v-decorator="[ 'type', validatorRules.type]"
            placeholder="请输入区域类型"
            style="width: 100%"
          />
        </a-form-item>

        <a-form-item label="危险等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            v-decorator="[ 'level', validatorRules.level]"
            placeholder="请输入危险等级"
            style="width: 100%"
          />
        </a-form-item>

        <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
        </a-form-item>
      </a-form>
    </a-spin>
    <!-- <baidu-map
      :center="center"
      map-type="BMAP_HYBRID_MAP"
      :zoom="zoom"
      @mousemove="syncPolyline"
      @click="paintPolyline"
      @rightclick="newPolyline"
      @ready="handler"
      class="map"
    >
      <bm-control>
        <button @click="toggle('polyline')">{{ polyline.editing ? '停止绘制' : '开始绘制' }}</button>
      </bm-control>
      <bm-polyline :path="polyline.paths"></bm-polyline>
    </baidu-map>
    {{polyline}} -->
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "AreaModal",
    components: { 
    },
    data () {
      return {
         center: {lng: 0, lat: 0},
        zoom: 3,
        polyline: {
        editing: false,
        paths: []
       },
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
        parentId:{},
        type:{},
        level:{},
        location:{},
        },
        url: {
          add: "/area/add",
          edit: "/area/edit",
        }
     
      }
    },
    created () {

    },
    mounted(){
        
    },
    methods: {
        toggle (name) {
      this[name].editing = !this[name].editing
    },
    syncPolyline (e) {
      if (!this.polyline.editing) {
        return
      }
      const {paths} = this.polyline
      // if (!paths.length) {
      //   return
      // }
      // const path = paths[paths.length - 1]
      // if (!path.length) {
      //   return
      // }
      if (path.length === 1) {
        path.push(e.point)
      }
      this.$set(path, path.length - 1, e.point)
    },
    newPolyline (e) {
      if (!this.polyline.editing) {
        return
      }
      const {paths} = this.polyline
      // if(!paths.length) {
      //   paths.push([])
      // }
      // const path = paths[paths.length - 1]
      path.pop()
      // if (path.length) {
      //   paths.push([])
      // }
    },
    paintPolyline (e) {
      if (!this.polyline.editing) {
        return
      }
      const {paths} = this.polyline
      // !paths.length && paths.push([])
      // paths[paths.length - 1].push(e.point)
      paths.push(e.point);
    },
       handler ({BMap, map}) {
      console.log(BMap, map)
      this.center.lng = 119.607511;
      this.center.lat = 32.185318;
      this.zoom = 16

    },
    openDistanceTool (e) {
      const {distanceTool} = this
      distanceTool && distanceTool.open()
    },
      add () {
        this.edit({});
      },
      edit (record) {
        console.log(record)
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'companyId','parentId','type','level','location'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.polyline.paths=[];
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
            let list=[];
          if(this.polyline.paths.length){
            this.polyline.paths.forEach((item,index)=>{
              console.log(item)
                   list.push(
                     {
                        gpsLong:item.lng,
                        gpsLati:item.lat,
                        seq:index+1
                     }
                   )
            })
          }

            let formData = Object.assign({
              ...this.model,gpsList:list
            }, values);
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
        this.form.setFieldsValue(pick(row,'companyId','parentId','type','level','location'))
      }
      
    }
  }
</script>
<style>
.map {
  width: 100%;
  height: 500px;
}
</style>