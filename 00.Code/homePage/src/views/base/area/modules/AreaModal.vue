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
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="所属企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'companyId', validatorRules.companyId]" placeholder="请输入所属企业"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="上级区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'parentId', validatorRules.parentId]" placeholder="请输入上级区域"></a-input>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="区域类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="[ 'type', validatorRules.type]"
                placeholder="请输入区域类型"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="危险等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="[ 'level', validatorRules.level]"
                placeholder="请输入危险等级"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <a-row>
          <a-col :span="12">
            <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select v-model="itemcolor" style="width: 120px">
                <a-select-option v-for="(item,index) in colorList" :key="index" :value="item">
                  <div :style="{background:item}" style="height:20px;width:20px;margin-top:4px;"></div>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
    <div id="mapbox" class="map"></div>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'

export default {
  name: 'AreaModal',
  components: {},
  data() {
    return {
      center: { lng: 0, lat: 0 },
      zoom: 3,
      points: [],
      itemcolor: '#00FFFF',
      colorList: ['#00FFFF', '#0000C6', '#FF0000', '#F00078', '#9393FF', '#F75000', '#FFD306', '#AE57A4', '#8600FF'],
      form: this.$form.createForm(this),
      title: '操作',
      width: 800,
      visible: false,
      drawingMode:'',
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },

      confirmLoading: false,
      validatorRules: {
        companyId: {},
        parentId: {},
        type: {},
        level: {},
        location: {},
        color: {}
      },
      url: {
        add: '/area/add',
        edit: '/area/edit'
      }
    }
  },
  watch: {
    model(val) {
      // 百度地图API功能
      let that = this
      setTimeout(function() {
        var map = new BMap.Map('mapbox', { mapType: BMAP_HYBRID_MAP }) // 创建Map实例
        map.centerAndZoom(new BMap.Point(119.607511, 32.185318), 17) // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件

        map.addControl(new BMap.NavigationControl())
        map.enableScrollWheelZoom()

        that.setControl(map, that)
        // var overlaycomplete = function(e){
        //       overlays.push(e.overlay);
        //   };
      }, 300)
    }
  },
  created() {},
  mounted() {},
  methods: {
    setControl(map, that) {
      var overlays = []
      var drawingManager = ''
      var styleOptions = {
        strokeColor: '#00FFFF', //边线颜色。
        fillColor: '#00FFFF', //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3, //边线的宽度，以像素为单位。
        strokeOpacity: 0.8, //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6, //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
      }
      //实例化鼠标绘制工具
      drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
          anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
          offset: new BMap.Size(5, 5), //偏离值
         drawingModes: [
            // BMAP_DRAWING_MARKER,
            // BMAP_DRAWING_CIRCLE,
            BMAP_DRAWING_POLYLINE,
            BMAP_DRAWING_POLYGON,
            BMAP_DRAWING_RECTANGLE
          ]
        },
        circleOptions: styleOptions, //圆的样式
        // polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
      })
      //添加鼠标绘制工具监听事件，用于获取绘制结果
      drawingManager.addEventListener('overlaycomplete', function(e) {
        console.log(e)
        overlays.push(e.overlay)
        drawingManager.close()
       that.points=[];
        switch (e.drawingMode) {
          // case 'circle':
          //   that.drawingMode = 'circle'
          //   that.points.push([{...e.overlay.point}]);
          //   break
          // case 'marker':
          //   that.drawingMode = 'marker'
          //   that.points.push([{...e.overlay.point}]);
          //   break
          case 'polyline':
            that.drawingMode = 'polyline'
            that.points.push(e.overlay.Pn)
            break
          case 'polygon':
            that.drawingMode = 'polygon'
            that.points.push(e.overlay.Pn)
            break
          case 'rectangle':
            that.drawingMode = 'rectangle'
            that.points.push(e.overlay.Pn)
            break
        }
        // alert(e.calculate);
        // alert(e.label);
      })

      let buttons = document.getElementsByClassName('BMapLib_Drawing_panel')[0].getElementsByTagName('a')
      for (let i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener('click', function() {
          clearAll()
        })
      }

      function clearAll() {
        for (var i = 0; i < overlays.length; i++) {
          map.removeOverlay(overlays[i])
        }
        overlays.length = 0
      }
    },
    toggle(name) {
      this[name].editing = !this[name].editing
    },
    openDistanceTool(e) {
      const { distanceTool } = this
      distanceTool && distanceTool.open()
    },
    add() {
      this.edit({})
    },
    edit(record) {
      console.log(record)
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'companyId', 'parentId', 'type', 'level', 'location'))
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
      // this.polyline.paths = []
    },
    handleOk() {
      const that = this
      let list = []
          if (this.points.length) {
            console.log(this.points)
            this.points[0].forEach((item, index) => {
              console.log(item)
              list.push({
                gpsLong: item.lng,
                gpsLati: item.lat,
                seq: index + 1
              })
            })
          }
          else{
            this.$message.error('请绘制区域！');
            return false;

          }
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          
          let formData = Object.assign(
            {
              ...this.model,
              gpsList: list,
              color:this.itemcolor
            },
            values
          )
          console.log('表单提交数据', formData)
          httpAction(httpurl, formData, method)
            .then(res => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(pick(row, 'companyId', 'parentId', 'type', 'level', 'location'))
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