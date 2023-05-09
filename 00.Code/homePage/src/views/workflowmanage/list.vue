<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
       
          <a-col :md="8" :sm="24">
             <a-form-item label="业务标题">
              <a-input placeholder="请输入业务标题"  v-model="queryParam.titleLike"></a-input>
            </a-form-item>
            <a-form-item label="流程ID">
              <a-input placeholder="请输入流程ID"  v-model="queryParam.processInstanceIds"></a-input>
            </a-form-item>
          </a-col>
             <a-col :md="8" :sm="24">
            <a-form-item label="流程发起人">
              <a-select
  
    style="width: 100%"
   v-model="queryParam.startUserId"
    placeholder="请选择流程发起人"
  >
    <a-select-option v-for="(item,index) in people" :key="index" :value="item.id"
      >{{item.realname}}</a-select-option>
  </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="2" :sm="12">
            <a-button type="primary" icon="search" @click="loadData">查询</a-button>
          </a-col>
          <a-col :md="2" :sm="12">
            <a-button type="primary" icon="reload">重置</a-button>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="goadd" type="primary" icon="plus">新建流程</a-button>
    </div> -->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
         :scroll="{ x: '120%' }"
      >
        <!-- <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>-->
            <template slot="status" slot-scope="msg">
            <a href="javascript:;">{{msg==1?'已启用':'未启用'}}</a>
          </template>
        <span slot="action" slot-scope="text, record">
         
                <a-popconfirm v-if="record.suspensionState==1" title="确定挂起吗?" @confirm="() => handleUp(record,2)">
                   <a>挂起</a>
                </a-popconfirm>
                <a-popconfirm v-else title="确定启动吗?" @confirm="() => handleUp(record,1)">
                   <a>启动</a>
                </a-popconfirm>
          <a-divider type="vertical" />
          <!-- <a @click="handleEdit(record)">发布</a>
          <a-divider type="vertical" /> -->
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定关闭吗?" @confirm="() => deleteWork(record.processInstanceId)">
                  <a>关闭</a>
                </a-popconfirm>
                
              </a-menu-item>
                <a-menu-item>
                <a @click="dueWork(record)">历史</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
<a-modal
      title="流程版本监控列表"
      :visible="showhistory"
      width="100%"
      :footer="null"
      style="top:0"
      :destroyOnClose="true"
      @cancel="showhistory = false"
    >
      
      <a-tabs defaultActiveKey="1"   @change="createModel" :tabPosition="tabPosition">
         <a-tab-pane  defaultActiveKey="1" tab="附加单据" key="1" style="min-height:90vh;" :tabPosition="tabPosition">
             
          <form-component v-if="formKey == null" :formobj="{formKey:formKey,businessKey:businessKey}" :businessKey="businessKey"  />
          <iframe v-else :src="formKey" frameborder="0"  :style="{height:height+'px',width:'100%'}"></iframe>
       </a-tab-pane>
        <!-- <a-tab-pane tab="我的任务" key="2"> -->
          <!-- <div style="margin:20px 0;">
            <a-steps progressDot :current="1">
              <a-step title="Finished" description="This is a description." />
              <a-step title="In Progress" description="This is a description." />
              <a-step title="Waiting" description="This is a description." />
            </a-steps>
          </div> -->

          <!-- <a-card title="意见信息"> -->
            <!-- <a-divider /> -->
            <!-- <p>处理意见：</p>
            <a-textarea placeholder="请输入处理意见" v-model="formobj.comment" :rows="4" />
            <a-divider />
            <a-upload
              name="file"
              :multiple="true"
              action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
              :headers="headers"
              @change="handleChange"
            >
              <a-button>
                <a-icon type="upload" />添加文件
              </a-button>
            </a-upload> -->
           
            <!-- <a-radio-group :options="plainOptions" @change="onChange1" :defaultValue="value1" /> -->
            <!-- <a-divider /> -->
            <!-- <a-checkbox v-model="key1">指定下一步操作人（指定下一步会签人员）</a-checkbox> -->
            <!-- <a-checkbox v-model="key2">是否抄送</a-checkbox> -->
            <!-- <a-divider v-if="key1" />
            <p v-if="key1">
              指定下一步操作人（指定下一步会签人员）：
              <a-select
                mode="multiple"
                :size="large"
                placeholder="请选择"
                style="width: 200px"
                v-model="formobj.nextOperaterIds"
              >
                <a-select-option
                  v-for="(item,index) in people"
                  :key="index"
                  :label="item.username"
                  :value="item.id"
                >{{item.username}}</a-select-option>
              </a-select>
            </p>
            <a-divider v-if="key2" /> -->
            <!-- <p v-if="key2">
              抄送给：
              <a-select
                mode="multiple"
                :size="large"
                v-model="formobj.nextOperaterNames"
                placeholder="请选择"
                style="width: 200px"
              >
                <a-select-option
                  v-for="(item,index) in people"
                  :key="index"
                  :value="item.id"
                >{{item.username}}</a-select-option>
              </a-select>
            </p> -->
             <!-- <a-divider />
         

              <p>审批结果：</p>
              <a-select  v-model="formobj.approvalResultCode" style="width: 200px">
           
          <a-select-option v-for="(item,index) in agreelist" :key="index" :value="item.value">{{item.text}}</a-select-option>
        </a-select>
            <a-divider />
            <p style="text-align:center;">
              <a-button type="primary" @click="submit">确认提交</a-button>
            </p>
          </a-card>
        </a-tab-pane> -->
        <a-tab-pane tab="流程图" key="3">
          <div>
               <div class="content-box" ref="content">
      <div class="canvas" id="canvas" ondragstart = "return false;"
 style="height:450px;" ref="canvas"></div>
      <div id="js-properties-panel"  class="panel"></div>
    </div>

          </div>
          <a-card title="流程历史跟踪">
            <a-table
              :columns="columns1"
              :dataSource="dataInfo"
            ></a-table>
          </a-card>
        </a-tab-pane>
      </a-tabs>
    </a-modal>
    <!-- <building-modal ref="modalForm" @ok="modalFormOk"></building-modal> -->
  </a-card>
</template>

<script>
import { axios } from '@/utils/request'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import qs from 'qs'
// import BuildingModal from './modules/designModel'
import customTranslate from '@/assets/customTranslate/customTranslate'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
// /*右边工具栏样式*/
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'
import BpmnViewer from 'bpmn-js'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import formComponent from './formcomponent'; 
const plainOptions = ['单分支模式', '多分支模式', '驳回']
// import BuildingModal from './modules/designModel'
export default {
  name: 'BuildingList',
  mixins: [JeecgListMixin],
  components: {
    formComponent
  },
  data() {
    return {
      description: '流程设计',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
         {
          title: '业务标题',
          align: 'center',
          dataIndex: 'title'
        },
       
         {
          title: '流程key',
          align: 'center',
          dataIndex: 'key'
        },
           {
          title: '流程名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '流程实例ID',
          align: 'center',
          dataIndex: 'processInstanceId'
        },
        {
          title: '业务id',
          align: 'center',
          dataIndex: 'businessKey'
        },
        {
          title: '发起人',
           width:120,
          align: 'center',
          dataIndex: 'startUserName'
        },
          {
          title: '开始时间',
          align: 'center',
          dataIndex: 'startTime'
        },
        ,
          {
          title: '耗时',
          width:120,
          align: 'center',
          dataIndex: 'duration'
        },
       {
          title: '状态',
           width:120,
          align: 'center',
          dataIndex: 'suspensionState',
           scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
           width:140,
          dataIndex: 'action',
          align: 'center',
           fixed: 'right',
          
          scopedSlots: { customRender: 'action' }
        }
      ],
         columns1: [
        {
          title: '环节名称',
          align: 'center',
          dataIndex: 'taskName'
        },
         {
          title: '审批时间',
          align: 'center',
          dataIndex: 'time'
        },
        {
          title: '审批意见',
          align: 'center',
          dataIndex: 'message'
        },
        {
          title: '审批结果',
          align: 'center',
          dataIndex: 'approvalResultDesc'
        },
        {
          title: '审批意见',
          align: 'center',
          dataIndex: 'message'
        },
        {
          title: '操作人姓名',
          align: 'center',
          dataIndex: 'userName'
        },
        ],
      url: {
        list: '/workflow/engine/default/process-instance',
        delete: '/workflow-rest/engine/default/process-definition/key/Process_14cpkuu/delete',
        hangUp:'/workflow/engine/default/process-instance',
        deleteBatch: '/building/deleteBatch',
        exportXlsUrl: '/building/exportXls',
        importExcelUrl: '/building/importExcel',
        peopleList:'/sys/user/list',
           getxml: '/workflow-rest/engine/default/process-definition',
      },
      dictOptions: {},
     people:[],
        task:'',
      taskid:'',
      processInstanceId:'',
      dictOptions: {},
        dataInfo:[],
      formobj: {
        approvalResultCode: 'OK',
        approvalResultDesc: '同意',
        comment:''
      },
      showhistory: false,
      tabPosition: 'left',
      height: document.body.clientHeight,
      description: '流程设计',
    }
  },
   watch: {
    peopleone(val) {
      console.log(val)
    },
   
    'formobj.approvalResultCode': {
　　　　handler(newValue, oldValue) {
　　　　　　
           this.formobj.approvalResultDesc= this.agreelist.find((item)=>item.value==newValue).text;
        
　　　　}
　　}

  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  created(){
    this.getPeople();
  },
  methods: {
      createModel(key){
   
    let that=this;
    setTimeout(function(){
               // 获取到属性ref为“content”的dom节点
    that.container = that.$refs.content
    // 获取到属性ref为“canvas”的dom节点
    const canvas = that.$refs.canvas
    // 建模，官方文档这里讲的很详细
      var customTranslateModule = {
        translate: [ 'value', customTranslate ]
      };

    that.bpmnModeler = new BpmnModeler({
      container: canvas,
      // 添加控制板
      propertiesPanel: {
        parent: '#js-properties-panel'
      },
      additionalModules: [
        // 左边工具栏以及节点
        // propertiesProviderModule,
        // 右边的工具栏
        // propertiesPanelModule,
        customTranslateModule
      ],
      moddleExtensions: {
        camunda: camundaModdleDescriptor
      }
    })
   
      that.createNewDiagram(that.task);
    },500)
    },
      createNewDiagram(taskid) {

      this.bpmnXmlStr = ''
     
      let that = this
      if (taskid) {
       
        let url = this.url.getxml + '/' + taskid + '/xml'
        axios
          .get(url)
          .then(res => {
             
            console.log(res)
            that.bpmnXmlStr = res.bpmn20Xml
            // 将字符串转换成图显示出来   
            
            this.bpmnModeler.importXML(that.bpmnXmlStr, function(err) {
                alert(that.bpmnXmlStr);
              if (err) {
                console.error(err)
            
              } else {
              
                // 这里还没用到这个，先注释掉吧
              console.log(  document.getElementsByClassName('djs-palette')[0])
                   document.getElementsByClassName('djs-palette')[0].style.display='none';
                  
                var wrap=document.querySelector('#canvas');

                wrap.onclick=function(e)
                {
                  // alert("不要跳转");
                  //IE:IE8及其以下：
                  //阻止默认事件写法：
                  // return false;
                  //现代浏览器：
                  //阻止默认事件写法：
                  //首先获取事件对象：
                  var ev=e||window.event;
                  ev.preventDefault();
                }
                 var overlays = that.bpmnModeler.get('overlays');
                let pointurl='workflow-rest/engine/default/process-instance/'+that.processInstanceId+'/activity-instances'
                axios.get(pointurl).then(res=>{
                   
                  let arr=res.childActivityInstances;
                  arr.forEach(item=>{
                           overlays.add(item.activityId, {
                          position: {
                            bottom: "-50%",
                            right: "-50%"
                          },
                          html: '<div style="width:100px;height:80px;background:red;opacity:0.1;"></div>'
                        });
                  })
                  
                })
                
        
              }
            })
          })
          .catch(err => {
            console.log(err)
          })
          
      } else {
        that.bpmnXmlStr =
          '<?xml version="1.0" encoding="UTF-8"?>' +
          '<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" id="Definitions_0fppxr8" targetNamespace="http://bpmn.io/schema/bpmn">' +
          '<bpmn:process id="Process_1" isExecutable="false" />' +
          '<bpmndi:BPMNDiagram id="BPMNDiagram_1">' +
          '<bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1" />' +
          '</bpmndi:BPMNDiagram>' +
          '</bpmn:definitions>'
        // 将字符串转换成图显示出来

        this.bpmnModeler.importXML(that.bpmnXmlStr, function(err) {
          if (err) {
            console.error(err)
          } else {
            // 这里还没用到这个，先注释掉吧
            // that.success()
          }
        })
      }

   
    },
      dueWork(item) {
      this.showhistory = true
      console.log(item)
      this.taskId = item.id;
      this.task=item.processDefinitionId;
   
      this.processInstanceId=item.processInstanceId;
      console.log(item.formKey,"item.formKey")
      this.formKey=item.formKey;
      this.businessKey=item.businessKey;
      this.getInfo()
      // this.getformlist();
    },
   getInfo() {
      console.log("xxxxxxx")
      let url = 'workflow/engine/default/process-instance/' + this.processInstanceId + '/comments/';
      console.log(url)
      axios
        .get(url)
        .then(res => {
         
        this.dataInfo=res;
        })
        .catch(err => {
           
          console.log(err)
        })
    },
    getPeople(){
      axios.get(this.url.peopleList+'?pageNo=1&pageSize=10000').then(res=>{
        console.log(res)
        this.people=res.result.records;
      })
    },
    deleteWork(key){
      
      let url='workflow/engine/default/process-instance/'+key;
      console.log(url)
      axios.delete(url).then(res=>{
        if(res.success){
               this.loadData();
          }else{
              this.$message.error(res.message);
          }
      }).catch(err=>{
        console.log(err)      })
    },
    handleUp(record,key){
      let url=this.url.hangUp+'/'+record.processInstanceId+'/suspended';
        axios.put(url,{suspensionState:key}).then(res=>{
        if(res.success){
               this.loadData();
          }else{
              this.$message.error(res.message);
          }
      }).catch(err=>{
        console.log(err)      })
    },
    setForm(){
      this.$refs.modalForm.loads();
      this.$refs.modalForm.title = "流程【process1569590197750】配置";
    },
    goadd() {
      this.$router.push({
        path: '/design/building'
      })
    },
    initDictConfig() {}
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
/deep/ .ant-form-item-label label {
  font-size: 12px;
}
</style>