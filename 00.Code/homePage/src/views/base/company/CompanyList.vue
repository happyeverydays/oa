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
      <a-button type="primary" icon="download" @click="handleExportXls('企业信息')">导出</a-button>
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

    <company-modal ref="modalForm" @ok="modalFormOk"></company-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CompanyModal from './modules/CompanyModal'
  export default {
    name: "CompanyList",
    mixins:[JeecgListMixin],
    components: {
      CompanyModal
    },
    data () {
      return {
        description: '企业信息管理页面',
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
            title:'上级企业',
            align:"center",
            dataIndex: 'parentId'
          },
          {
            title:'企业编号',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'企业名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'负责人',
            align:"center",
            dataIndex: 'principal'
          },
          {
            title:'注册地址',
            align:"center",
            dataIndex: 'regAddress'
          },
          {
            title:'经营地址',
            align:"center",
            dataIndex: 'proAddress'
          },
          {
            title:'占地面积',
            align:"center",
            dataIndex: 'landArea'
          },
          {
            title:'建筑面积',
            align:"center",
            dataIndex: 'buildingArea'
          },
          {
            title:'省',
            align:"center",
            dataIndex: 'province'
          },
          {
            title:'市',
            align:"center",
            dataIndex: 'city'
          },
          {
            title:'区',
            align:"center",
            dataIndex: 'area'
          },
          {
            title:'镇',
            align:"center",
            dataIndex: 'town'
          },
          {
            title:'园区',
            align:"center",
            dataIndex: 'park'
          },
          {
            title:'网格',
            align:"center",
            dataIndex: 'grid'
          },
          {
            title:'地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title:'公司介绍',
            align:"center",
            dataIndex: 'profile'
          },
          {
            title:'主营业务',
            align:"center",
            dataIndex: 'mainBusiness'
          },
          {
            title:'Logo',
            align:"center",
            dataIndex: 'logoUrl'
          },
          {
            title:'营业执照',
            align:"center",
            dataIndex: 'businessLicenseFile'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'status'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/company/list",
          delete: "/company/delete",
          deleteBatch: "/company/deleteBatch",
          exportXlsUrl: "/company/exportXls",
          importExcelUrl: "/company/importExcel",
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