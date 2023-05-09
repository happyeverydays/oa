<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <!-- <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item label="流程类型">
              <a-input v-model.trim="queryParam.category" placeholder="请输入流程类型"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="流程ID">
              <a-input v-model.trim="queryParam.processDefinitionIdIn" placeholder="请输入流程ID"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="2" :sm="12">
            <a-button type="primary" icon="search" @click="searchQuery">查询</a-button>
          </a-col>
          <a-col :md="2" :sm="12">
            <a-button type="primary" icon="reload">重置</a-button>
          </a-col>
        </a-row>
      </a-form>
    </div> -->
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="goadd" type="primary" icon="plus">新建流程</a-button>
    </div> -->

    <!-- table区域-begin -->
    <!-- <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div> -->

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

        <span slot="action" slot-scope="text, record">
          <a @click="goExtActProcess(record)">流程图</a>
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
                <a-popconfirm title="确定删除吗?" @confirm="() => deleteWork(record.deploymentId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
               
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
   

  
<a-modal title="流程图" :width="800" v-model="showPhoto"  :footer="null">
    <div ref="content" class="content"  >
      <div class="canvas" v-if="showPhoto"  id="canvas" ref="canvas"></div>
    </div>
    </a-modal>

  </a-card>
</template>

<script>
import BpmnViewer from 'bpmn-js'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import { axios } from '@/utils/request'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BuildingModal from './modules/designModel'
import building from './building'

export default {
  name: 'BuildingList',
  mixins: [JeecgListMixin],
  components: {
    BuildingModal,building
  },
  props:['workKey'],
  data() {
    return {
       bpmnModeler: null,
      container: null,
      canvas: null,
      bpmnXmlStr: '',
      height:document.body.clientHeight,
      // 表头
      queryParam:{
        key:this.workKey
      },
      confirmLoading: false,
      visible: false,
      showPhoto:false,
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
          title: '名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '流程KEY',
          align: 'center',
          dataIndex: 'key'
        },
        {
          title: '版本',
          align: 'center',
          dataIndex: 'version'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/workflow/engine/default/process-definition',
        delete: '/workflow-rest/engine/default/process-definition/key/Process_14cpkuu/delete',
        getxml: '/workflow-rest/engine/default/process-definition',
        deleteBatch: '/building/deleteBatch',
        exportXlsUrl: '/building/exportXls',
        importExcelUrl: '/building/importExcel'
      },
      xmldom: '',
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  mounted(){

      console.log(this.queryParam)
  },
  methods: {


    goExtActProcess(record) {
    
      this.showPhoto=true;
      this.getXml(record.id);
      
    },
   createNewDiagram() {
            // console.log("xxxxxx")
            this.BpmnModeler=null;
            this.BpmnModeler = require('bpmn-js');
       let that=this;
       this.BpmnModeler = new BpmnModeler({ container:this.$refs.canvas });
			  //  web交流qq群873099947
			  // import a BPMN 2.0 diagram
			  this.BpmnModeler.importXML(that.xmldom, function(err) {
			    if (err) {
			      // import failed :-(
			    } else {
			      // we did well!
			      var canvas = that.BpmnModeler.get('canvas');
			      canvas.zoom('fit-viewport');
			    }
			  });


      },
    getXml(id) {
      let url = this.url.getxml + '/' + id + '/xml'
    
      axios
        .get(url)
        .then(res => {
          console.log(res)
          this.xmldom = res.bpmn20Xml
          this.visible = true;
          let that=this;
          setTimeout(function(){
              that.createNewDiagram();
          },1000)
       
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteWork(key) {
      let url = 'workflow/engine/default/deployment/' + key 
      console.log(url)
      axios
        .delete(url)
        .then(res => {
          // console.log(res)
          if(res.success){
               this.loadData();
          }else{
              this.$message.error(res.message);
          }
        })
        .catch(err => {
          console.log(err)
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

.canvas {
  width: 100%;
  height:100%;
}
.content{
  height: 500px;
}

</style>
<style>
#canvas .bjs-powered-by img{
  display: none;
}
</style>