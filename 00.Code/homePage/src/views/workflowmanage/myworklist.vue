<template>
  <a-card :bordered="false">
           <!-- 查询区域 -->
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
            <a-button type="primary" icon="search" @click="setForm">查询</a-button>
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
          <a>办理</a>
          <a-divider type="vertical" />
          <!-- <a @click="handleEdit(record)">发布</a>
          <a-divider type="vertical" /> -->
         <a >委托</a>
        </span>
      </a-table>
    </div>

  </a-card>
</template>

<script>
import { axios } from '@/utils/request'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
// import BuildingModal from './modules/designModel'
export default {
  name: 'BuildingList',
  mixins: [JeecgListMixin],
  // components: {
  //   BuildingModal
  // },
  data() {
    return {
      tabPosition:'left',
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
          dataIndex: 'name'
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
           width:140,
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
          dataIndex: 'name'
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
           width:140,
          dataIndex: 'action',
          align: 'center',
           fixed: 'right',
          
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/workflow/engine/default/task',
        delete: '/workflow-rest/engine/default/process-definition/key/Process_14cpkuu/delete',
        hangUp:'/workflow-rest/engine/default/process-instance',
        deleteBatch: '/building/deleteBatch',
        exportXlsUrl: '/building/exportXls',
        importExcelUrl: '/building/importExcel'
      },
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    //

    deleteWork(key){
      
      let url='workflow-rest/engine/default/process-definition/key/'+key+'/delete';
      console.log(url)
      axios.delete(url).then(res=>{
        console.log(res)
      }).catch(err=>{
        console.log(err)      })
    },
    handleUp(record){
      let url=this.url.hangUp+'/'+record.processInstanceId+'/suspended';
        axios.put(url,{suspended:true}).then(res=>{
        console.log(res)
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