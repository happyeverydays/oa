<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('安全承诺公告')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
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
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <companyPromise-modal ref="modalForm" @ok="modalFormOk"></companyPromise-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CompanyPromiseModal from './modules/CompanyPromiseModal'
  export default {
    name: "CompanyPromiseList",
    mixins:[JeecgListMixin],
    components: {
      CompanyPromiseModal
    },
    data () {
      return {
        description: '安全承诺公告管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'日期',
            align:"center",
            dataIndex: 'promiseDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'所属企业',
            align:"center",
            dataIndex: 'companyId'
          },
          {
            title:'生成装置总套',
            align:"center",
            dataIndex: 'totalNum'
          },
          {
            title:'生成装置运行套',
            align:"center",
            dataIndex: 'runNum'
          },
          {
            title:'生成装置停产套',
            align:"center",
            dataIndex: 'stopNum'
          },
          {
            title:'生成装置检修套',
            align:"center",
            dataIndex: 'repairNum'
          },
          {
            title:'特殊动火',
            align:"center",
            dataIndex: 'workNum1'
          },
          {
            title:'一级动火',
            align:"center",
            dataIndex: 'workNum2'
          },
          {
            title:'二级动火',
            align:"center",
            dataIndex: 'workNum3'
          },
          {
            title:'受限空间',
            align:"center",
            dataIndex: 'workNum4'
          },
          {
            title:'是否处于试生产',
            align:"center",
            dataIndex: 'preproFlag'
          },
          {
            title:'是否处于开停车状态',
            align:"center",
            dataIndex: 'stopFlag'
          },
          {
            title:'是否处于安全状态',
            align:"center",
            dataIndex: 'safeFlag'
          },
          {
            title:'企业承诺内容',
            align:"center",
            dataIndex: 'content'
          },
          {
            title:'主要负责人',
            align:"center",
            dataIndex: 'principal'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/companyPromise/list",
          delete: "/companyPromise/delete",
          deleteBatch: "/companyPromise/deleteBatch",
          exportXlsUrl: "/companyPromise/exportXls",
          importExcelUrl: "/companyPromise/importExcel",
        },
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>