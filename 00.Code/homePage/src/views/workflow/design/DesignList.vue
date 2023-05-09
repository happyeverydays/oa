<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
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
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="goadd" type="primary" icon="plus">新建流程</a-button>
    </div>

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

        <span slot="action" slot-scope="text, record">
          <a @click="goExtActProcess(record)">流程设计</a>
          <a-divider type="vertical" />
          <!-- <a @click="handleEdit(record)">发布</a>
          <a-divider type="vertical" />-->
          <a @click="duework(record)">流程配置</a>
          <a-divider type="vertical" />

          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => deleteWork(record.key)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a @click="manageVersion(record.key)">版本管理</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <a-drawer
      title="流程编辑"
      :placement="'top'"
      :visible="showWork"
      :closable="true"
      :height="height"
      @close="workflowedit()"
      class="drawer-height"
    >
      <building v-if="showWork" :buildId="buildId"></building>
    </a-drawer>

    <a-drawer
      title="流程版本监控列表"
      :placement="'top'"
      :visible="showhistory"
      :closable="true"
      :height="height"
      @close="showhistory = false"
      class="drawer-height"
    >
      <version-list v-if="showhistory" :workKey="workKey" />
    </a-drawer>

    <a-modal
      :title="title"
      :visible="visible"
      @ok="handleOk"
      width="100%"
      :footer="null"
      style="top:0;"
      bodyStyle1="height:calc(100% - 54px);"
      :confirmLoading="confirmLoading"
      @cancel="handleCancel"
    >
      <building-modal ref="BuildingModalone"></building-modal>
    </a-modal>
  </a-card>
</template>

<script>
// import BpmnViewer from 'bpmn-js'
// import BpmnModeler from 'bpmn-js/lib/Modeler'
// import propertiesPanelModule from 'bpmn-js-properties-panel'
// import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
// import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import { axios } from '@/utils/request'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BuildingModal from '@/views/ExtActProcess/ExtActProcessFormList'
import building from './building'
import versionList from './versionList'
export default {
  name: 'BuildingList',
  mixins: [JeecgListMixin],
  components: {
    building,
    versionList,
    BuildingModal
  },
  data() {
    return {
      bpmnModeler: null,
      container: null,
      canvas: this.$refs.canvas,
      description: '流程设计',
      title: '',
      height: document.body.clientHeight,
      // 表头
      confirmLoading: false,
      visible: false,
      showWork: false,
      showhistory: false,
      buildId: '',
      workKey: '',
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
          title: '流程名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '流程KEY',
          align: 'center',
          dataIndex: 'key'
        },
        {
          title: '流程类型',
          align: 'center',
          dataIndex: 'category'
        },
        {
          title: '流程id',
          align: 'center',
          dataIndex: 'id'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/workflow/engine/default/process-definition?latest=true',
        delete: '/workflow-rest/engine/default/process-definition/key/Process_14cpkuu/delete',
        getxml: '/workflow-rest/engine/default/process-definition',
        deleteBatch: '/building/deleteBatch',
        exportXlsUrl: '/building/exportXls',
        importExcelUrl: '/building/importExcel'
      },
      xmldom: '',
      dictOptions: {},
      keyselect: ''
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  mounted() {},
  methods: {
    workflowedit() {
      
      this.showWork = false
      this.loadData()
    },
    handleOk() {
      // this.confirmLoading = true;
      this.visible = false
    },
    handleCancel() {
      // this.confirmLoading = true;

      this.visible = false
    },
    duework(record) {
      console.log(record)

      this.title = '流程【' + record.key + '】配置'
      let that = this

      this.visible = true
      this.$nextTick(() => {
       
        that.$refs.BuildingModalone.filters = { processKey: record.key }
        that.$refs.BuildingModalone.processKey = record.key
        that.$refs.BuildingModalone.infos = record.key
      })
    },
    manageVersion(key) {
      this.workKey = key
      this.showhistory = true
    },
    goExtActProcess(record) {
      // this.$router.push({
      //   path: '/design/building',
      //   query: {
      //     taskid: record.id,
      //     key:record.key
      //   }
      // }) 
      this.buildId = record.id
      this.showWork = true
    },

    deleteWork(key) {
      let url = '/workflow/engine/default/process-definition/key/' + key + '/delete'
      console.log(url)
      axios
        .delete(url)
        .then(res => {
          if (res.success) {
            this.loadData()
          } else {
            this.$message.error(res.message)
          }
          // console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    },

    goadd() {
      this.buildId = ''
      this.showWork = true
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