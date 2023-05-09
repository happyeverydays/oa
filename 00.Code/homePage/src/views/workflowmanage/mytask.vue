<template>
  <a-card :bordered="false">
    <a-tabs defaultActiveKey="1" :tabPosition="tabPosition">
      <a-tab-pane tab="我的任务" key="1">
        <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :md="6" :sm="24">
                <a-form-item label="业务标题">
                  <a-input placeholder="请输入业务标题" v-model="queryParam.descriptionLike"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="流程编号">
                  <a-input placeholder="请输入流程编号" v-model="queryParam.processDefinitionId"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item label="流程发起人">
                  <a-select
                    v-model="queryParam.START_USER_ID_"
                    :size="large"
                    placeholder="请选择"
                    style="width: 200px"
                  >
                    <a-select-option
                      v-for="(item,index) in people"
                      :key="index"
                      :label="item.username"
                      :value="item.id"
                    >{{item.username}}</a-select-option>
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
        </div>-->

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
              <a href="javascript:;">{{msg?'已启用':'未启用'}}</a>
            </template>
            <span slot="action" slot-scope="text, record">
              <a @click="dueWork(record)">办理</a>
              <a-divider type="vertical" />
              <!-- <a @click="handleEdit(record)">发布</a>
              <a-divider type="vertical" />-->
              <a>委托</a>
            </span>
          </a-table>
        </div>
      </a-tab-pane>
      <a-tab-pane tab="历史任务" key="2">
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :md="8" :sm="24">
                <a-form-item label="流程类型">
                  <a-input placeholder="请输入流程类型"></a-input>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="流程ID">
                  <a-input placeholder="请输入流程ID"></a-input>
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
        </div>-->

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
            :columns="columnsone"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange"
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
              <a href="javascript:;">{{msg?'已启用':'未启用'}}</a>
            </template>
            <span slot="action" slot-scope="text, record">
              <a>历史</a>
            </span>
          </a-table>
        </div>
      </a-tab-pane>
    </a-tabs>

    <a-modal
      title="流程版本监控列表"
      :visible="showhistory"
      width="100%"
      :footer="null"
      style="top:0"
      :destroyOnClose="true"
      @cancel="showhistory = false"
    >
      <a-tabs defaultActiveKey="1" @change="createModel" :tabPosition="tabPosition">
        <a-tab-pane
          defaultActiveKey="1"
          tab="附加单据"
          key="1"
          style="min-height:90vh;"
          :tabPosition="tabPosition"
          :forceRender="true"
        >
          <form-component
            v-if="formKey == null"
            :formobj="{formKey:formKey,businessKey:businessKey}"
            :businessKey="businessKey"
          />
          <iframe
            v-show="formKey!=null"
            :src="formKey"
            id="commentIframe"
            frameborder="0"
            :style="{height:height+'px',width:'100%'}"
          ></iframe>
        </a-tab-pane>
        <a-tab-pane tab="我的任务" key="2" :forceRender="true">
          <!-- <div style="margin:20px 0;">
            <a-steps progressDot :current="1">
              <a-step title="Finished" description="This is a description." />
              <a-step title="In Progress" description="This is a description." />
              <a-step title="Waiting" description="This is a description." />
            </a-steps>
          </div>-->

          <a-card title="意见信息">
            <!-- <a-divider /> -->
            <p>处理意见：</p>
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
            </a-upload>

            <!-- <a-radio-group :options="plainOptions" @change="onChange1" :defaultValue="value1" /> -->
            <a-divider />
            <a-checkbox v-model="key1">指定下一步操作人（指定下一步会签人员）</a-checkbox>
            <!-- <a-checkbox v-model="key2">是否抄送</a-checkbox> -->
            <a-divider v-if="key1" />
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
            <a-divider v-if="key2" />
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
            </p>-->
            <a-divider />

            <p>审批结果：</p>
            <a-select v-model="formobj.approvalResultCode" style="width: 200px">
              <a-select-option
                v-for="(item,index) in agreelist"
                :key="index"
                :value="item.value"
              >{{item.text}}</a-select-option>
            </a-select>
            <!-- <a-form-item label="下一环节" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-model="formobj.approvalResultCode" style="width: 200px">
              <a-select-option
                v-for="(item,index) in agreelist"
                :key="index"
                :value="item.value"
              >{{item.text}}</a-select-option>
            </a-select>
        </a-form-item>
      -->

            <a-divider />
            <p style="text-align:center;">
              <a-button type="primary" @click="submit">确认提交</a-button>
            </p>
          </a-card>
        </a-tab-pane>
        <a-tab-pane tab="流程图" key="3">
          <div>
            <div class="content-box" ref="content">
              <!-- <div class="canvas" id="canvas" ondragstart = "return false;"
 style="height:450px;" ref="canvas"></div>
              <div id="js-properties-panel"  class="panel"></div>-->
              <div class="canvas" id="canvas" style="height:450px;" ref="canvas"></div>
            </div>
          </div>
          <a-card title="流程历史跟踪">
            <a-table :columns="columns1" :dataSource="dataInfo"></a-table>
          </a-card>
        </a-tab-pane>
      </a-tabs>
    </a-modal>
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
import formComponent from './formcomponent'
import { httpAction, getAction, postAction, putAction } from '@/api/manage'
const plainOptions = ['单分支模式', '多分支模式', '驳回']

export default {
  name: 'BuildingList',
  mixins: [JeecgListMixin],
  components: {
    formComponent
  },
  data() {
    return {
      plainOptions,
      value1: '单分支模式',
      key1: false,
      key2: false,
      formKey: '',
      businessKey: '',
      people: [],
      agreelist: [
        {
          value: 'OK',
          text: '同意'
        },
        {
          value: 'BK',
          text: '驳回'
        }
        // {
        //   value: 'end',
        //   text: '结束流程'
        // },
        // {
        //   value: 'ST',
        //   text: '提交'
        // }
      ],
      dataInfo: [],
      formobj: {
        approvalResultCode: 'OK',
        approvalResultDesc: '同意',
        comment: ''
      },
      showhistory: false,
      tabPosition: 'left',
      height: document.body.clientHeight,
      description: '流程设计',
      // 表头
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
        }
      ],
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
          dataIndex: 'description'
        },
        {
          title: '流程名称',
          align: 'center',
          dataIndex: 'processName'
        },
        {
          title: '环节名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '流程发起人',
          align: 'center',
          dataIndex: 'startUsername'
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime'
        },
        {
          title: '挂起状态',
          align: 'center',
          dataIndex: 'suspensionState'
        },
        {
          title: '操作',
          width: 140,
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',

          scopedSlots: { customRender: 'action' }
        }
      ],
      columnsone: [
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
          dataIndex: 'description'
        },
        {
          title: '流程编号',
          align: 'center',
          dataIndex: 'processDefinitionId'
        },
        {
          title: 'key',
          align: 'center',
          dataIndex: 'taskDefinitionKey'
        },

        {
          title: '操作',
          width: 140,
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',

          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/workflow/engine/default/task',
        delete: '/workflow-rest/engine/default/process-definition/key/Process_14cpkuu/delete',
        hangUp: '/workflow-rest/engine/default/process-instance',
        deleteBatch: '/building/deleteBatch',
        exportXlsUrl: '/building/exportXls',
        importExcelUrl: '/building/importExcel',
        peopleList: '/sys/user/list',
        getxml: '/workflow-rest/engine/default/process-definition'
      },
      task: '',
      taskid: '',
      processInstanceId: '',
      dictOptions: {}
    }
  },
  watch: {
    peopleone(val) {
      console.log(val)
    },

    'formobj.approvalResultCode': {
      handler(newValue, oldValue) {
        this.formobj.approvalResultDesc = this.agreelist.find(item => item.value == newValue).text
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  created() {
    this.getPeople()
    let that = this
    //默认地址
    // let origins = window.location.href.split("/#/")[0];

    //监听
    window.addEventListener('message', function(event) {
      //此处执行事件
      var origin = event.origin
      console.log(event)
      // if (origins !== origin) {
      let tag = event.data
      if (tag.hasOwnProperty('userName')) {
        if (!tag.userName) {
          that.$message.warning('请输入请假人')
          return
        }
        if (!tag.resons_1) {
          that.$message.warning('请输入请假事由')
          return
        }
        if (!tag.resons_2) {
          that.$message.warning('请输入请假原因')
          return
        }
      }
      // that.$message.success('');
    })
  },
  mounted() {
    this.height = document.body.clientHeight - 100

    // 获取到属性ref为“content”的dom节点
    this.container = this.$refs.content
    // 获取到属性ref为“canvas”的dom节点
    const canvas = this.$refs.canvas
    // 建模，官方文档这里讲的很详细
    var customTranslateModule = {
      translate: ['value', customTranslate]
    }

    this.bpmnModeler = new BpmnModeler({
      container: canvas,
      // 添加控制板
      propertiesPanel: {
        parent: '#js-properties-panel'
      },
      additionalModules: [
        // 左边工具栏以及节点
        propertiesProviderModule,
        // 右边的工具栏
        propertiesPanelModule,
        customTranslateModule
      ],
      moddleExtensions: {
        camunda: camundaModdleDescriptor
      }
    })
    console.log(this.bpmnModeler)

    this.createNewDiagram(this.buildId)
  },
  methods: {
    createModel(key) {
      let that = this
      setTimeout(function() {
        // 获取到属性ref为“content”的dom节点
        that.container = that.$refs.content
        // 获取到属性ref为“canvas”的dom节点
        const canvas = that.$refs.canvas
        // 建模，官方文档这里讲的很详细
        var customTranslateModule = {
          translate: ['value', customTranslate]
        }

alert(customTranslate.translate);
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

        that.createNewDiagram(that.task)
      }, 500)
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
              if (err) {
                console.error(err)
              } else {
                // 这里还没用到这个，先注释掉吧
                console.log(document.getElementsByClassName('djs-palette')[0])
                document.getElementsByClassName('djs-palette')[0].style.display = 'none'

                var wrap = document.querySelector('#canvas')

                wrap.onclick = function(e) {
                  // alert("不要跳转");
                  //IE:IE8及其以下：
                  //阻止默认事件写法：
                  // return false;
                  //现代浏览器：
                  //阻止默认事件写法：
                  //首先获取事件对象：
                  var ev = e || window.event
                  ev.preventDefault()
                }
                var overlays = that.bpmnModeler.get('overlays')
                let pointurl =
                  'workflow-rest/engine/default/process-instance/' + that.processInstanceId + '/activity-instances'
                axios.get(pointurl).then(res => {
                  let arr = res.childActivityInstances
                  arr.forEach(item => {
                    overlays.add(item.activityId, {
                      position: {
                        bottom: '-50%',
                        right: '-50%'
                      },
                      html: '<div style="width:100px;height:80px;background:red;opacity:0.1;"></div>'
                    })
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
    submit() {
      console.log(this.formobj)
      document.getElementById('commentIframe').contentWindow.myfun()
      // return;
      let arr = []
      // if (this.formobj.nextOperaterIds && this.formobj.nextOperaterIds.length) {
      //   this.people.forEach(list => {
      //     this.formobj.nextOperaterIds.forEach(item => {
      //       if (list.id === item) {
      //         arr.push(list.username)
      //       }
      //     })
      //   })
      // }

      let obj = {
        ...this.formobj,
        nextOperaterNames: arr,
        formData:{"性别":"男","name":"三"}
      }
      let url = 'workflow/engine/default/task/' + this.taskId + '/complete'
      postAction(url, obj).then(res => {
        this.loading = false;
        if (res.success == true) {
          this.$message.success('提交成功！')
          this.$emit('ok')
          this.modal = {};
          this.close();
        } else {
          this.$message.error(res.message)
        }
      })

  //  Object.keys(obj).forEach(function(key) {
  //       fd.append(key, obj[key])
  //     })
      // axios
      //   .post(url, fd, {
      //     headers: {
      //       'Content-Type': 'multipart/form-data'
      //     }
      //   })
      //   .then(res => {
      //     console.log(res)
      //     if (res.success == true) {
      //       this.$message.info('提交成功！')
      //       this.loadData()
      //     } else {
      //       this.$message.error(res.message)
      //     }
      //   })
    },

    getPeople() {
      axios.get(this.url.peopleList + '?pageNo=1&pageSize=10000').then(res => {
        console.log(res)
        this.people = res.result.records
      })
    },
    //
    onChange1(e) {
      console.log(e)
    },
    dueWork(item) {
      this.showhistory = true
      console.log(item)
      this.taskId = item.id
      this.task = item.processDefinitionId
      this.processInstanceId = item.processInstanceId
      console.log(item.formKey, 'item.formKey')
      this.formKey = item.formKey
      this.businessKey = item.businessKey
      this.getInfo()
      // this.getformlist();
    },

    getInfo() {
      console.log('xxxxxxx')
      let url = 'workflow/engine/default/process-instance/' + this.processInstanceId + '/comments/'
      console.log(url)
      axios
        .get(url)
        .then(res => {
          this.dataInfo = res
        })
        .catch(err => {
          console.log(err)
        })
    },

    deleteWork(key) {
      let url = 'workflow-rest/engine/default/process-definition/key/' + key + '/delete'
      console.log(url)
      axios
        .delete(url)
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    handleUp(record) {
      let url = this.url.hangUp + '/' + record.processInstanceId + '/suspended'
      axios
        .put(url, { suspended: true })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },
    setForm() {
      this.$refs.modalForm.loads()
      this.$refs.modalForm.title = '流程【process1569590197750】配置'
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

.panel {
  position: absolute;
  right: 0;
  top: 0;
  width: 300px;

  max-height: 80%;
  overflow-y: scroll;
}
</style>